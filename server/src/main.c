#include "motion_types.h"
#include <arpa/inet.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define ROBOT_PORT 3001
#define APP_PORT 3002

int robot_server_fd, robot_client_fd;
int app_server_fd, app_client_fd;

int start_server(int port) {
  struct sockaddr_in server_addr;

  // Create a socket
  int server_socket = socket(AF_INET, SOCK_STREAM, 0);
  if (server_socket < 0) {
	perror("Socket creation failed");
	return -1;
  }

  // Set socket options to allow reuse of address
  int opt = 1;
  if (setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt)) < 0) {
	perror("Failed to set socket options");
	close(server_socket);
	return -1;
  }

  // Bind the socket to the specified port
  memset(&server_addr, 0, sizeof(server_addr));
  server_addr.sin_family = AF_INET;
  server_addr.sin_addr.s_addr = INADDR_ANY;
  server_addr.sin_port = htons(port);

  if (bind(server_socket, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
	perror("Bind failed");
	close(server_socket);
	return -1;
  }

  // Start listening for incoming connections
  if (listen(server_socket, 1) < 0) {
	perror("Listen failed");
	close(server_socket);
	return -1;
  }

  return server_socket;
}

int listen_server(int server_socket) {
  // Accept incoming connections (for demonstration purposes)
  struct sockaddr_in client_addr;
  socklen_t client_addr_len = sizeof(client_addr);
  int client_socket = accept(server_socket, (struct sockaddr*)&client_addr, &client_addr_len);

  if (client_socket < 0) {
	perror("Failed to accept connection");
	return -1;
  }

  printf("Accepted connection from %s:%d\n", inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));

  return client_socket;
}

// Function to gracefully shut down the server
void shutdown_server() {
  if (robot_client_fd >= 0) {
	close(robot_server_fd);
	printf("Robot client shut down gracefully\n");
  }
  if (robot_server_fd >= 0) {
	close(robot_server_fd);
	printf("Robot server shut down gracefully\n");
  }

  if (app_client_fd >= 0) {
	close(app_server_fd);
	printf("app client shut down gracefully\n");
  }
  if (app_server_fd >= 0) {
	close(app_server_fd);
	printf("app server shut down gracefully\n");
  }
}

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

 	// TODO: Create thread and run both decode functions

  // Shutdown the server if it exits the loop
  shutdown_server();
  return 0;
}
