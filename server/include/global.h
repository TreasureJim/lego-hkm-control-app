#pragma once

#include "motion_types.h"

extern int robot_server_fd, robot_client_fd;
extern int app_server_fd, app_client_fd;

void robot_decode(int robot_socket);
void robot_send_movejog(struct movejog* m);
void robot_send_robotrequeststatus(struct robotrequeststatus* m);

int app_decode(int app_fd);
void app_send_motionid(struct motionid* m);
void app_send_robotstatus(struct robotstatus* m);

int start_server(int port);
int listen_server(int server_socket);
void shutdown_server();
