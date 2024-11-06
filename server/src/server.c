#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "global.h"

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
