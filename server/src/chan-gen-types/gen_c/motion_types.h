#ifndef MOTION_TYPES_H
#define MOTION_TYPES_H

#include <stdint.h>
#include <chan/decoder.h>
#include <chan/encoder.h>

/* Generated type */
struct motionid {
    uint8_t id[16];
    int8_t status;
};

/* Basic usage. */
typedef void (*motionid_cb_f)(struct motionid *m, void *ctx);
int chan_enc_register_motionid(struct chan_encoder *e);
int chan_dec_register_motionid(struct chan_decoder *d, motionid_cb_f f, void *ctx);
int encode_motionid(struct chan_encoder *e, const struct motionid *val);

/* Extended usage. */
size_t encoded_motionid_size(const struct motionid *val);
int chan_encode_motionid(struct chan_encoder *e, const struct motionid *val);
int chan_decode_motionid(struct chan_decoder *d, struct motionid *val);
extern const struct chan_type *motionid_type;


/* Generated type */
struct robotrequeststatus {
    int _dummy;
};

/* Basic usage. */
typedef void (*robotrequeststatus_cb_f)(struct robotrequeststatus *m, void *ctx);
int chan_enc_register_robotrequeststatus(struct chan_encoder *e);
int chan_dec_register_robotrequeststatus(struct chan_decoder *d, robotrequeststatus_cb_f f, void *ctx);
int encode_robotrequeststatus(struct chan_encoder *e, const struct robotrequeststatus *val);

/* Extended usage. */
size_t encoded_robotrequeststatus_size(const struct robotrequeststatus *val);
int chan_encode_robotrequeststatus(struct chan_encoder *e, const struct robotrequeststatus *val);
int chan_decode_robotrequeststatus(struct chan_decoder *d, struct robotrequeststatus *val);
extern const struct chan_type *robotrequeststatus_type;


/* Generated type */
struct robotstatus {
    double x;
    double y;
    double z;
    double j1;
    double j2;
    double j3;
    double j4;
};

/* Basic usage. */
typedef void (*robotstatus_cb_f)(struct robotstatus *m, void *ctx);
int chan_enc_register_robotstatus(struct chan_encoder *e);
int chan_dec_register_robotstatus(struct chan_decoder *d, robotstatus_cb_f f, void *ctx);
int encode_robotstatus(struct chan_encoder *e, const struct robotstatus *val);

/* Extended usage. */
size_t encoded_robotstatus_size(const struct robotstatus *val);
int chan_encode_robotstatus(struct chan_encoder *e, const struct robotstatus *val);
int chan_decode_robotstatus(struct chan_decoder *d, struct robotstatus *val);
extern const struct chan_type *robotstatus_type;


/* Generated type */
struct robtarget {
    double x;
    double y;
    double z;
    double j4;
    double a;
    double b;
    double c;
};

/* Basic usage. */
typedef void (*robtarget_cb_f)(struct robtarget *m, void *ctx);
int chan_enc_register_robtarget(struct chan_encoder *e);
int chan_dec_register_robtarget(struct chan_decoder *d, robtarget_cb_f f, void *ctx);
int encode_robtarget(struct chan_encoder *e, const struct robtarget *val);

/* Extended usage. */
size_t encoded_robtarget_size(const struct robtarget *val);
int chan_encode_robtarget(struct chan_encoder *e, const struct robtarget *val);
int chan_decode_robtarget(struct chan_decoder *d, struct robtarget *val);
extern const struct chan_type *robtarget_type;


/* Generated type */
struct hkmpos {
    double j1;
    double j2;
    double j3;
    double j4;
};

/* Basic usage. */
typedef void (*hkmpos_cb_f)(struct hkmpos *m, void *ctx);
int chan_enc_register_hkmpos(struct chan_encoder *e);
int chan_dec_register_hkmpos(struct chan_decoder *d, hkmpos_cb_f f, void *ctx);
int encode_hkmpos(struct chan_encoder *e, const struct hkmpos *val);

/* Extended usage. */
size_t encoded_hkmpos_size(const struct hkmpos *val);
int chan_encode_hkmpos(struct chan_encoder *e, const struct hkmpos *val);
int chan_decode_hkmpos(struct chan_decoder *d, struct hkmpos *val);
extern const struct chan_type *hkmpos_type;


/* Generated type */
struct movelinear {
    uint8_t motion_id[16];
    struct robtarget target;
};

/* Basic usage. */
typedef void (*movelinear_cb_f)(struct movelinear *m, void *ctx);
int chan_enc_register_movelinear(struct chan_encoder *e);
int chan_dec_register_movelinear(struct chan_decoder *d, movelinear_cb_f f, void *ctx);
int encode_movelinear(struct chan_encoder *e, const struct movelinear *val);

/* Extended usage. */
size_t encoded_movelinear_size(const struct movelinear *val);
int chan_encode_movelinear(struct chan_encoder *e, const struct movelinear *val);
int chan_decode_movelinear(struct chan_decoder *d, struct movelinear *val);
extern const struct chan_type *movelinear_type;


/* Generated type */
struct movearc {
    uint8_t motion_id[16];
    struct robtarget apos;
    struct robtarget target;
};

/* Basic usage. */
typedef void (*movearc_cb_f)(struct movearc *m, void *ctx);
int chan_enc_register_movearc(struct chan_encoder *e);
int chan_dec_register_movearc(struct chan_decoder *d, movearc_cb_f f, void *ctx);
int encode_movearc(struct chan_encoder *e, const struct movearc *val);

/* Extended usage. */
size_t encoded_movearc_size(const struct movearc *val);
int chan_encode_movearc(struct chan_encoder *e, const struct movearc *val);
int chan_decode_movearc(struct chan_decoder *d, struct movearc *val);
extern const struct chan_type *movearc_type;


/* Generated type */
struct movecircular {
    uint8_t motion_id[16];
    struct robtarget apos;
    struct robtarget target;
};

/* Basic usage. */
typedef void (*movecircular_cb_f)(struct movecircular *m, void *ctx);
int chan_enc_register_movecircular(struct chan_encoder *e);
int chan_dec_register_movecircular(struct chan_decoder *d, movecircular_cb_f f, void *ctx);
int encode_movecircular(struct chan_encoder *e, const struct movecircular *val);

/* Extended usage. */
size_t encoded_movecircular_size(const struct movecircular *val);
int chan_encode_movecircular(struct chan_encoder *e, const struct movecircular *val);
int chan_decode_movecircular(struct chan_decoder *d, struct movecircular *val);
extern const struct chan_type *movecircular_type;


/* Generated type */
struct movejoint {
    uint8_t motion_id[16];
    struct hkmpos target;
};

/* Basic usage. */
typedef void (*movejoint_cb_f)(struct movejoint *m, void *ctx);
int chan_enc_register_movejoint(struct chan_encoder *e);
int chan_dec_register_movejoint(struct chan_decoder *d, movejoint_cb_f f, void *ctx);
int encode_movejoint(struct chan_encoder *e, const struct movejoint *val);

/* Extended usage. */
size_t encoded_movejoint_size(const struct movejoint *val);
int chan_encode_movejoint(struct chan_encoder *e, const struct movejoint *val);
int chan_decode_movejoint(struct chan_decoder *d, struct movejoint *val);
extern const struct chan_type *movejoint_type;


/* Generated type */
struct movepos {
    uint8_t motion_id[16];
    struct robtarget target;
};

/* Basic usage. */
typedef void (*movepos_cb_f)(struct movepos *m, void *ctx);
int chan_enc_register_movepos(struct chan_encoder *e);
int chan_dec_register_movepos(struct chan_decoder *d, movepos_cb_f f, void *ctx);
int encode_movepos(struct chan_encoder *e, const struct movepos *val);

/* Extended usage. */
size_t encoded_movepos_size(const struct movepos *val);
int chan_encode_movepos(struct chan_encoder *e, const struct movepos *val);
int chan_decode_movepos(struct chan_decoder *d, struct movepos *val);
extern const struct chan_type *movepos_type;


/* Generated type */
struct movejog {
    uint8_t motion_id[16];
    double x;
    double y;
    double z;
    double j1;
    double j2;
    double j3;
    double j4;
};

/* Basic usage. */
typedef void (*movejog_cb_f)(struct movejog *m, void *ctx);
int chan_enc_register_movejog(struct chan_encoder *e);
int chan_dec_register_movejog(struct chan_decoder *d, movejog_cb_f f, void *ctx);
int encode_movejog(struct chan_encoder *e, const struct movejog *val);

/* Extended usage. */
size_t encoded_movejog_size(const struct movejog *val);
int chan_encode_movejog(struct chan_encoder *e, const struct movejog *val);
int chan_decode_movejog(struct chan_decoder *d, struct movejog *val);
extern const struct chan_type *movejog_type;


/* Generated type */
struct blendvalue {
    double percentage;
    double distance;
    double vel_const;
};

/* Basic usage. */
typedef void (*blendvalue_cb_f)(struct blendvalue *m, void *ctx);
int chan_enc_register_blendvalue(struct chan_encoder *e);
int chan_dec_register_blendvalue(struct chan_decoder *d, blendvalue_cb_f f, void *ctx);
int encode_blendvalue(struct chan_encoder *e, const struct blendvalue *val);

/* Extended usage. */
size_t encoded_blendvalue_size(const struct blendvalue *val);
int chan_encode_blendvalue(struct chan_encoder *e, const struct blendvalue *val);
int chan_decode_blendvalue(struct chan_decoder *d, struct blendvalue *val);
extern const struct chan_type *blendvalue_type;


/* Generated type */
struct blend {
    uint16_t blend_type;
    struct blendvalue blend_value;
    uint8_t motion_id[16];
};

/* Basic usage. */
typedef void (*blend_cb_f)(struct blend *m, void *ctx);
int chan_enc_register_blend(struct chan_encoder *e);
int chan_dec_register_blend(struct chan_decoder *d, blend_cb_f f, void *ctx);
int encode_blend(struct chan_encoder *e, const struct blend *val);

/* Extended usage. */
size_t encoded_blend_size(const struct blend *val);
int chan_encode_blend(struct chan_encoder *e, const struct blend *val);
int chan_decode_blend(struct chan_decoder *d, struct blend *val);
extern const struct chan_type *blend_type;

#endif
