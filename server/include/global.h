#pragma once

#include "motion_types.h"

extern int robot_server_fd, robot_client_fd;
extern int app_server_fd, app_client_fd;

void robot_send_movejog(struct movejog* m);
void robot_send_robotrequeststatus(struct robotrequeststatus* m);

void app_send_motionid(struct motionid* m);
void app_send_robotstatus(struct robotstatus* m);
