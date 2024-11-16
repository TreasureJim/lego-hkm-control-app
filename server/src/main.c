#include <arpa/inet.h>
#include <pthread.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "global.h"

#define ROBOT_PORT 3001
#define APP_PORT 3002

typedef struct {
  int app_fd;
} ThreadArgs;

void* app_decode_thread(void* args) {
  ThreadArgs* thread_args = (ThreadArgs*)args;

  int result = app_decode(thread_args->app_fd);
  if (app_client_fd >= 0) {
	close(app_server_fd);
  }

  while(result <= 0) {
    if ((app_client_fd = listen_server(app_server_fd)) < 0) {
	  fprintf(stderr, "Failed to reconnect to app\n");
	  break;
    }
    printf("Reconnected to app.\n");

    result = app_decode(thread_args->app_fd);
  }

  // Thread cleanup
  free(thread_args);
  return NULL;
}

// Function to start the app_decode thread
pthread_t start_decode_thread(int app_fd) {
  pthread_t thread_id;
  ThreadArgs* args = malloc(sizeof(ThreadArgs));

  if (args == NULL) {
	perror("Failed to allocate memory for thread arguments");
	exit(EXIT_FAILURE);
  }

  args->app_fd = app_fd;

  // Create the thread
  if (pthread_create(&thread_id, NULL, app_decode_thread, args) != 0) {
	perror("Failed to create thread");
	free(args);
	exit(EXIT_FAILURE);
  }

  return thread_id;
}

int robot_server_fd, robot_client_fd;
int app_server_fd, app_client_fd;

// Signal handler to catch CTRL+C and trigger shutdown
void handle_sigint(int sig) {
  printf("\nCaught signal %d, shutting down...\n", sig);
  shutdown_server();
  exit(0);
}

int main() {
  signal(SIGINT, handle_sigint);

  if ((robot_server_fd = start_server(ROBOT_PORT)) < 0) {
	fprintf(stderr, "Failed to start the robot server\n");
	return 1;
  }
  printf("Robot server listening on: %d\n", ROBOT_PORT);
  if ((robot_client_fd = listen_server(robot_server_fd)) < 0) {
	fprintf(stderr, "Failed to connect to robot\n");
	return 1;
  }
  printf("Connected to robot.\n");

  if ((app_server_fd = start_server(APP_PORT)) < 0) {
	fprintf(stderr, "Failed to start the app server\n");
	return 1;
  }
  printf("app server listening on: %d\n", APP_PORT);
  if ((app_client_fd = listen_server(app_server_fd)) < 0) {
	fprintf(stderr, "Failed to connect to app\n");
	return 1;
  }
  printf("Connected to app.\n");

  pthread_t thread_id = start_decode_thread(app_client_fd);
  robot_decode(robot_client_fd);

  pthread_join(thread_id, NULL);

  // Shutdown the server if it exits the loop
  shutdown_server();
  return 0;
}
