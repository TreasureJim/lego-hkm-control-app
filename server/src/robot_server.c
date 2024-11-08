#include <stdio.h>
#include <unistd.h>

#include "chan/decoder.h"
#include "chan/encoder.h"
#include "chan/fd_reader.h"
#include "chan/fd_writer.h"
#include "global.h"
#include "motion_types.h"

void unknown_type_error_func(uint8_t* sig, uint32_t sig_len, void* ctx) {
  fprintf(stderr, "[ERROR] parsing type from juliet queue.\n");
}

struct chan_encoder* encoder;
struct chan_decoder* decoder;
struct chan_writer* robot_writer;
struct chan_reader* robot_reader;
char decoder_ctx[] = "decoder context";

int socket_fd;

void cleanup_juliet_comms() {
  chan_encoder_free(encoder);
  chan_decoder_free(decoder);

  close(socket_fd);
}

void motionid_callbck_func(struct motionid* m, void* ctx) {
  app_send_motionid(m);
}

void robotstatus_callbck_func(struct robotstatus* m, void* ctx) {
  app_send_robotstatus(m);
}

void robot_decode(int robot_socket) {
  socket_fd = robot_socket;

  robot_writer = fd_writer_new(robot_socket);
  robot_reader = fd_reader_new(robot_socket);
  encoder = chan_encoder_new(robot_writer);
  decoder = chan_decoder_new(robot_reader, unknown_type_error_func, decoder_ctx);

  // register encoder types
  chan_enc_register_movejog(encoder);
  chan_enc_register_movelinear(encoder);
  chan_enc_register_movepos(encoder);
  chan_enc_register_robotrequeststatus(encoder);

  // register decoder types
  chan_dec_register_motionid(decoder, motionid_callbck_func, NULL);
  chan_dec_register_robotstatus(decoder, robotstatus_callbck_func, NULL);

  // decode
  int status;
  while ((status = chan_decode(decoder)) == 0)
	;

  cleanup_juliet_comms();
  printf("Received non-zero status from chan: %d.\n", status);
}

void robot_send_movejog(struct movejog* m) { encode_movejog(encoder, m); }
void robot_send_robotrequeststatus(struct robotrequeststatus* m) { encode_robotrequeststatus(encoder, m); }
void robot_send_movelinear(struct movelinear* m) { encode_movelinear(encoder, m); }
void robot_send_movepos(struct movepos* m) { encode_movepos(encoder, m); }
