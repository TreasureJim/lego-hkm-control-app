#include "global.h"
#include "motion_types.h"
#include <arpa/inet.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

enum S_ID { S_ID_MOTIONID = 0, S_ID_MOVEJOG, S_ID_ROBOTREQUESTSTATUS, S_ID_ROBOTSTATUS, S_ID_MOVELINEAR, S_ID_MOVEPOS };

void send_message(int sockfd, uint8_t* buffer, size_t length) {
  if (send(sockfd, buffer, length, 0) != length) {
	perror("Send failed");
  }
}

ssize_t receive_message(int sockfd, uint8_t* buffer, size_t length) {
  int received = recv(sockfd, buffer, length, 0);
  if (received < length) {
	fprintf(stderr, "[ERROR] reached error or EOF for app\n");
	return -1;
  }
  return received;
}

int app_decode(int app_fd) {
  while (true) {
	char sig_id;
	recv(app_fd, &sig_id, 1, 0);

	char* buf;

	switch (sig_id) {
	case S_ID_MOVEJOG:
	  buf = malloc(sizeof(struct movejog));
	  if (receive_message(app_fd, buf, sizeof(struct movejog)) < 0) {
		return -1;
	  }

	  robot_send_movejog((struct movejog*)buf);

	  free(buf);
	  break;

	case S_ID_ROBOTREQUESTSTATUS:
	  struct robotrequeststatus rqs;
	  robot_send_robotrequeststatus((struct robotrequeststatus*)&rqs);
	  break;

	case S_ID_MOVELINEAR:
	  buf = malloc(sizeof(struct movelinear));
	  if (receive_message(app_fd, buf, sizeof(struct movelinear)) < 0) {
		return -1;
	  }

	  robot_send_movelinear((struct movelinear*)buf);

	  free(buf);
	  break;

	case S_ID_MOVEPOS:
	  buf = malloc(sizeof(struct movepos));
	  if (receive_message(app_fd, buf, sizeof(struct movepos)) < 0) {
		return -1;
	  }

	  robot_send_movepos((struct movepos*)buf);

	  free(buf);
	  break;

	default:
	  fprintf(stderr, "[APP ERROR] Decoded an unknown sig_id: %d\n", sig_id);
	  break;
	}
  }
}

void app_send_motionid(struct motionid* m) {
  uint8_t sig_id = (uint8_t)S_ID_MOTIONID;
  send_message(app_client_fd, &sig_id, 1);
  send_message(app_client_fd, (uint8_t*)m, sizeof(struct motionid));
}

void app_send_robotstatus(struct robotstatus* m) {
  uint8_t sig_id = (uint8_t)S_ID_ROBOTSTATUS;
  send_message(app_client_fd, &sig_id, 1);
  send_message(app_client_fd, (uint8_t*)m, sizeof(struct robotstatus));
}
