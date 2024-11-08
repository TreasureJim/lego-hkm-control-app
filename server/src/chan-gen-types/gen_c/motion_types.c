#include <chan/decoder_private.h>
#include <chan/encoder_private.h>
#include <chan/protocol.h>
#include <chan/signature.h>
#include "motion_types.h"

/**
 * Juliet type:
 *
 *  struct MotionId
 *    id::StaticArraysCore.SVector{16, UInt8}
 *    status::Int8
 *  end
 */

static const uint8_t motionid_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 2 struct fields */
    0x02,
        0x02,'i', 'd', /* 2 byte long field name, id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x06,'s', 't', 'a', 't', 'u', 's', /* 6 byte long field name, status */
        /* int8_t */
        CHAN_ID_INT8,
};

size_t
encoded_motionid_size(const struct motionid *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->id[0]));
    s += sizeof(int8_t);

    return s;
}

static struct chan_type _motionid_type = {
    .sig = motionid_signature_bytes,
    .sig_len = sizeof(motionid_signature_bytes),
    .name = "MotionId",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_motionid_size,
};
const struct chan_type *motionid_type = &_motionid_type;

int
encode_motionid(struct chan_encoder *e, const struct motionid *val)
{
    int res;

    res = chan_encode_begin(e, motionid_type, val);
    if (res != 0) { return res; }

    res = chan_encode_motionid(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_motionid(struct chan_encoder *e, const struct motionid *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_int8(e, val->status);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_motionid(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_motionid_type);
}

static int
decode_motionid(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct motionid val;
    int res = 0;

    res = chan_decode_motionid(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_motionid(struct chan_decoder *d, struct motionid *val)
{
    int res = 0;
    int err = 0;

    size_t dims_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_id != 1)) {
        return -1;
    }
    size_t dim_1_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_id; i_1++) {
        val->id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    val->status = chan_decode_int8(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_motionid(struct chan_decoder *d, motionid_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_motionid_type,
            (decoding_f) decode_motionid,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct RobotRequestStatus
 *  end
 */

static const uint8_t robotrequeststatus_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 0 struct fields */
    0x00,
};

size_t
encoded_robotrequeststatus_size(const struct robotrequeststatus *val)
{
    size_t s = 0;

    (void) val;

    return s;
}

static struct chan_type _robotrequeststatus_type = {
    .sig = robotrequeststatus_signature_bytes,
    .sig_len = sizeof(robotrequeststatus_signature_bytes),
    .name = "RobotRequestStatus",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_robotrequeststatus_size,
};
const struct chan_type *robotrequeststatus_type = &_robotrequeststatus_type;

int
encode_robotrequeststatus(struct chan_encoder *e, const struct robotrequeststatus *val)
{
    int res;

    res = chan_encode_begin(e, robotrequeststatus_type, val);
    if (res != 0) { return res; }

    res = chan_encode_robotrequeststatus(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_robotrequeststatus(struct chan_encoder *e, const struct robotrequeststatus *val)
{
    int res = 0;

    (void) e;
    (void) val;

    return res;
}

int
chan_enc_register_robotrequeststatus(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_robotrequeststatus_type);
}

static int
decode_robotrequeststatus(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct robotrequeststatus val;
    int res = 0;

    res = chan_decode_robotrequeststatus(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_robotrequeststatus(struct chan_decoder *d, struct robotrequeststatus *val)
{
    int res = 0;
    int err = 0;

   (void) d;
   (void) val;
   (void) res;

    return err;
}

int
chan_dec_register_robotrequeststatus(struct chan_decoder *d, robotrequeststatus_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_robotrequeststatus_type,
            (decoding_f) decode_robotrequeststatus,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct RobotStatus
 *    x::Float64
 *    y::Float64
 *    z::Float64
 *    j1::Float64
 *    j2::Float64
 *    j3::Float64
 *    j4::Float64
 *  end
 */

static const uint8_t robotstatus_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 7 struct fields */
    0x07,
        0x01,'x', /* 1 byte long field name, x */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'y', /* 1 byte long field name, y */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'z', /* 1 byte long field name, z */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '1', /* 2 byte long field name, j1 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '2', /* 2 byte long field name, j2 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '3', /* 2 byte long field name, j3 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '4', /* 2 byte long field name, j4 */
        /* double */
        CHAN_ID_FLOAT64,
};

size_t
encoded_robotstatus_size(const struct robotstatus *val)
{
    size_t s = 0;

    (void) val;
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);

    return s;
}

static struct chan_type _robotstatus_type = {
    .sig = robotstatus_signature_bytes,
    .sig_len = sizeof(robotstatus_signature_bytes),
    .name = "RobotStatus",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_robotstatus_size,
};
const struct chan_type *robotstatus_type = &_robotstatus_type;

int
encode_robotstatus(struct chan_encoder *e, const struct robotstatus *val)
{
    int res;

    res = chan_encode_begin(e, robotstatus_type, val);
    if (res != 0) { return res; }

    res = chan_encode_robotstatus(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_robotstatus(struct chan_encoder *e, const struct robotstatus *val)
{
    int res = 0;

    res = chan_encode_float64(e, val->x);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->y);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->z);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j1);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j2);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j3);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j4);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_robotstatus(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_robotstatus_type);
}

static int
decode_robotstatus(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct robotstatus val;
    int res = 0;

    res = chan_decode_robotstatus(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_robotstatus(struct chan_decoder *d, struct robotstatus *val)
{
    int res = 0;
    int err = 0;

    val->x = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->y = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->z = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j1 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j2 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j3 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j4 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_robotstatus(struct chan_decoder *d, robotstatus_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_robotstatus_type,
            (decoding_f) decode_robotstatus,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct RobTarget
 *    x::Float64
 *    y::Float64
 *    z::Float64
 *    j4::Float64
 *    a::Float64
 *    b::Float64
 *    c::Float64
 *  end
 */

static const uint8_t robtarget_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 7 struct fields */
    0x07,
        0x01,'x', /* 1 byte long field name, x */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'y', /* 1 byte long field name, y */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'z', /* 1 byte long field name, z */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '4', /* 2 byte long field name, j4 */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'a', /* 1 byte long field name, a */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'b', /* 1 byte long field name, b */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'c', /* 1 byte long field name, c */
        /* double */
        CHAN_ID_FLOAT64,
};

size_t
encoded_robtarget_size(const struct robtarget *val)
{
    size_t s = 0;

    (void) val;
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);

    return s;
}

static struct chan_type _robtarget_type = {
    .sig = robtarget_signature_bytes,
    .sig_len = sizeof(robtarget_signature_bytes),
    .name = "RobTarget",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_robtarget_size,
};
const struct chan_type *robtarget_type = &_robtarget_type;

int
encode_robtarget(struct chan_encoder *e, const struct robtarget *val)
{
    int res;

    res = chan_encode_begin(e, robtarget_type, val);
    if (res != 0) { return res; }

    res = chan_encode_robtarget(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_robtarget(struct chan_encoder *e, const struct robtarget *val)
{
    int res = 0;

    res = chan_encode_float64(e, val->x);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->y);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->z);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j4);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->a);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->b);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->c);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_robtarget(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_robtarget_type);
}

static int
decode_robtarget(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct robtarget val;
    int res = 0;

    res = chan_decode_robtarget(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_robtarget(struct chan_decoder *d, struct robtarget *val)
{
    int res = 0;
    int err = 0;

    val->x = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->y = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->z = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j4 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->a = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->b = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->c = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_robtarget(struct chan_decoder *d, robtarget_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_robtarget_type,
            (decoding_f) decode_robtarget,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct HKMPos
 *    j1::Float64
 *    j2::Float64
 *    j3::Float64
 *    j4::Float64
 *  end
 */

static const uint8_t hkmpos_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 4 struct fields */
    0x04,
        0x02,'j', '1', /* 2 byte long field name, j1 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '2', /* 2 byte long field name, j2 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '3', /* 2 byte long field name, j3 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '4', /* 2 byte long field name, j4 */
        /* double */
        CHAN_ID_FLOAT64,
};

size_t
encoded_hkmpos_size(const struct hkmpos *val)
{
    size_t s = 0;

    (void) val;
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);

    return s;
}

static struct chan_type _hkmpos_type = {
    .sig = hkmpos_signature_bytes,
    .sig_len = sizeof(hkmpos_signature_bytes),
    .name = "HKMPos",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_hkmpos_size,
};
const struct chan_type *hkmpos_type = &_hkmpos_type;

int
encode_hkmpos(struct chan_encoder *e, const struct hkmpos *val)
{
    int res;

    res = chan_encode_begin(e, hkmpos_type, val);
    if (res != 0) { return res; }

    res = chan_encode_hkmpos(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_hkmpos(struct chan_encoder *e, const struct hkmpos *val)
{
    int res = 0;

    res = chan_encode_float64(e, val->j1);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j2);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j3);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j4);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_hkmpos(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_hkmpos_type);
}

static int
decode_hkmpos(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct hkmpos val;
    int res = 0;

    res = chan_decode_hkmpos(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_hkmpos(struct chan_decoder *d, struct hkmpos *val)
{
    int res = 0;
    int err = 0;

    val->j1 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j2 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j3 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j4 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_hkmpos(struct chan_decoder *d, hkmpos_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_hkmpos_type,
            (decoding_f) decode_hkmpos,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MoveLinear
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    target::JuliaCompiler.RobTarget
 *  end
 */

static const uint8_t movelinear_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 2 struct fields */
    0x02,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x06,'t', 'a', 'r', 'g', 'e', 't', /* 6 byte long field name, target */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
};

size_t
encoded_movelinear_size(const struct movelinear *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += encoded_robtarget_size(&val->target);

    return s;
}

static struct chan_type _movelinear_type = {
    .sig = movelinear_signature_bytes,
    .sig_len = sizeof(movelinear_signature_bytes),
    .name = "MoveLinear",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movelinear_size,
};
const struct chan_type *movelinear_type = &_movelinear_type;

int
encode_movelinear(struct chan_encoder *e, const struct movelinear *val)
{
    int res;

    res = chan_encode_begin(e, movelinear_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movelinear(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movelinear(struct chan_encoder *e, const struct movelinear *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_robtarget(e, &val->target);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movelinear(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movelinear_type);
}

static int
decode_movelinear(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movelinear val;
    int res = 0;

    res = chan_decode_movelinear(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movelinear(struct chan_decoder *d, struct movelinear *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    res = chan_decode_robtarget(d, &val->target);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movelinear(struct chan_decoder *d, movelinear_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movelinear_type,
            (decoding_f) decode_movelinear,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MoveArc
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    apos::JuliaCompiler.RobTarget
 *    target::JuliaCompiler.RobTarget
 *  end
 */

static const uint8_t movearc_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 3 struct fields */
    0x03,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x04,'a', 'p', 'o', 's', /* 4 byte long field name, apos */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
        0x06,'t', 'a', 'r', 'g', 'e', 't', /* 6 byte long field name, target */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
};

size_t
encoded_movearc_size(const struct movearc *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += encoded_robtarget_size(&val->apos);
    s += encoded_robtarget_size(&val->target);

    return s;
}

static struct chan_type _movearc_type = {
    .sig = movearc_signature_bytes,
    .sig_len = sizeof(movearc_signature_bytes),
    .name = "MoveArc",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movearc_size,
};
const struct chan_type *movearc_type = &_movearc_type;

int
encode_movearc(struct chan_encoder *e, const struct movearc *val)
{
    int res;

    res = chan_encode_begin(e, movearc_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movearc(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movearc(struct chan_encoder *e, const struct movearc *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_robtarget(e, &val->apos);
    if (res != 0) { return res; }
    res = chan_encode_robtarget(e, &val->target);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movearc(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movearc_type);
}

static int
decode_movearc(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movearc val;
    int res = 0;

    res = chan_decode_movearc(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movearc(struct chan_decoder *d, struct movearc *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    res = chan_decode_robtarget(d, &val->apos);
    if (res != 0) {
        return -1;
    }
    res = chan_decode_robtarget(d, &val->target);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movearc(struct chan_decoder *d, movearc_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movearc_type,
            (decoding_f) decode_movearc,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MoveCircular
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    apos::JuliaCompiler.RobTarget
 *    target::JuliaCompiler.RobTarget
 *  end
 */

static const uint8_t movecircular_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 3 struct fields */
    0x03,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x04,'a', 'p', 'o', 's', /* 4 byte long field name, apos */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
        0x06,'t', 'a', 'r', 'g', 'e', 't', /* 6 byte long field name, target */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
};

size_t
encoded_movecircular_size(const struct movecircular *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += encoded_robtarget_size(&val->apos);
    s += encoded_robtarget_size(&val->target);

    return s;
}

static struct chan_type _movecircular_type = {
    .sig = movecircular_signature_bytes,
    .sig_len = sizeof(movecircular_signature_bytes),
    .name = "MoveCircular",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movecircular_size,
};
const struct chan_type *movecircular_type = &_movecircular_type;

int
encode_movecircular(struct chan_encoder *e, const struct movecircular *val)
{
    int res;

    res = chan_encode_begin(e, movecircular_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movecircular(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movecircular(struct chan_encoder *e, const struct movecircular *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_robtarget(e, &val->apos);
    if (res != 0) { return res; }
    res = chan_encode_robtarget(e, &val->target);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movecircular(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movecircular_type);
}

static int
decode_movecircular(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movecircular val;
    int res = 0;

    res = chan_decode_movecircular(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movecircular(struct chan_decoder *d, struct movecircular *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    res = chan_decode_robtarget(d, &val->apos);
    if (res != 0) {
        return -1;
    }
    res = chan_decode_robtarget(d, &val->target);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movecircular(struct chan_decoder *d, movecircular_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movecircular_type,
            (decoding_f) decode_movecircular,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MoveJoint
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    target::JuliaCompiler.HKMPos
 *  end
 */

static const uint8_t movejoint_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 2 struct fields */
    0x02,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x06,'t', 'a', 'r', 'g', 'e', 't', /* 6 byte long field name, target */
        /* struct hkmpos */
        CHAN_ID_STRUCT,
        /* 4 struct fields */
        0x04,
            0x02,'j', '1', /* 2 byte long field name, j1 */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '2', /* 2 byte long field name, j2 */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '3', /* 2 byte long field name, j3 */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
};

size_t
encoded_movejoint_size(const struct movejoint *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += encoded_hkmpos_size(&val->target);

    return s;
}

static struct chan_type _movejoint_type = {
    .sig = movejoint_signature_bytes,
    .sig_len = sizeof(movejoint_signature_bytes),
    .name = "MoveJoint",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movejoint_size,
};
const struct chan_type *movejoint_type = &_movejoint_type;

int
encode_movejoint(struct chan_encoder *e, const struct movejoint *val)
{
    int res;

    res = chan_encode_begin(e, movejoint_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movejoint(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movejoint(struct chan_encoder *e, const struct movejoint *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_hkmpos(e, &val->target);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movejoint(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movejoint_type);
}

static int
decode_movejoint(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movejoint val;
    int res = 0;

    res = chan_decode_movejoint(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movejoint(struct chan_decoder *d, struct movejoint *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    res = chan_decode_hkmpos(d, &val->target);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movejoint(struct chan_decoder *d, movejoint_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movejoint_type,
            (decoding_f) decode_movejoint,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MovePos
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    target::JuliaCompiler.RobTarget
 *  end
 */

static const uint8_t movepos_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 2 struct fields */
    0x02,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x06,'t', 'a', 'r', 'g', 'e', 't', /* 6 byte long field name, target */
        /* struct robtarget */
        CHAN_ID_STRUCT,
        /* 7 struct fields */
        0x07,
            0x01,'x', /* 1 byte long field name, x */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'y', /* 1 byte long field name, y */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'z', /* 1 byte long field name, z */
            /* double */
            CHAN_ID_FLOAT64,
            0x02,'j', '4', /* 2 byte long field name, j4 */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'a', /* 1 byte long field name, a */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'b', /* 1 byte long field name, b */
            /* double */
            CHAN_ID_FLOAT64,
            0x01,'c', /* 1 byte long field name, c */
            /* double */
            CHAN_ID_FLOAT64,
};

size_t
encoded_movepos_size(const struct movepos *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += encoded_robtarget_size(&val->target);

    return s;
}

static struct chan_type _movepos_type = {
    .sig = movepos_signature_bytes,
    .sig_len = sizeof(movepos_signature_bytes),
    .name = "MovePos",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movepos_size,
};
const struct chan_type *movepos_type = &_movepos_type;

int
encode_movepos(struct chan_encoder *e, const struct movepos *val)
{
    int res;

    res = chan_encode_begin(e, movepos_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movepos(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movepos(struct chan_encoder *e, const struct movepos *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_robtarget(e, &val->target);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movepos(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movepos_type);
}

static int
decode_movepos(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movepos val;
    int res = 0;

    res = chan_decode_movepos(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movepos(struct chan_decoder *d, struct movepos *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    res = chan_decode_robtarget(d, &val->target);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movepos(struct chan_decoder *d, movepos_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movepos_type,
            (decoding_f) decode_movepos,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct MoveJog
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *    x::Float64
 *    y::Float64
 *    z::Float64
 *    j1::Float64
 *    j2::Float64
 *    j3::Float64
 *    j4::Float64
 *  end
 */

static const uint8_t movejog_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 8 struct fields */
    0x08,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
        0x01,'x', /* 1 byte long field name, x */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'y', /* 1 byte long field name, y */
        /* double */
        CHAN_ID_FLOAT64,
        0x01,'z', /* 1 byte long field name, z */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '1', /* 2 byte long field name, j1 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '2', /* 2 byte long field name, j2 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '3', /* 2 byte long field name, j3 */
        /* double */
        CHAN_ID_FLOAT64,
        0x02,'j', '4', /* 2 byte long field name, j4 */
        /* double */
        CHAN_ID_FLOAT64,
};

size_t
encoded_movejog_size(const struct movejog *val)
{
    size_t s = 0;

    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);

    return s;
}

static struct chan_type _movejog_type = {
    .sig = movejog_signature_bytes,
    .sig_len = sizeof(movejog_signature_bytes),
    .name = "MoveJog",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_movejog_size,
};
const struct chan_type *movejog_type = &_movejog_type;

int
encode_movejog(struct chan_encoder *e, const struct movejog *val)
{
    int res;

    res = chan_encode_begin(e, movejog_type, val);
    if (res != 0) { return res; }

    res = chan_encode_movejog(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_movejog(struct chan_encoder *e, const struct movejog *val)
{
    int res = 0;

    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }
    res = chan_encode_float64(e, val->x);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->y);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->z);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j1);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j2);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j3);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->j4);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_movejog(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_movejog_type);
}

static int
decode_movejog(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct movejog val;
    int res = 0;

    res = chan_decode_movejog(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_movejog(struct chan_decoder *d, struct movejog *val)
{
    int res = 0;
    int err = 0;

    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }
    val->x = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->y = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->z = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j1 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j2 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j3 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->j4 = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_movejog(struct chan_decoder *d, movejog_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_movejog_type,
            (decoding_f) decode_movejog,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct BlendValue
 *    percentage::Float64
 *    distance::Float64
 *    vel_const::Float64
 *  end
 */

static const uint8_t blendvalue_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 3 struct fields */
    0x03,
        0x0a,'p', 'e', 'r', 'c', 'e', 'n', 't', 'a', 'g', 'e', /* 10 byte long field name, percentage */
        /* double */
        CHAN_ID_FLOAT64,
        0x08,'d', 'i', 's', 't', 'a', 'n', 'c', 'e', /* 8 byte long field name, distance */
        /* double */
        CHAN_ID_FLOAT64,
        0x09,'v', 'e', 'l', '_', 'c', 'o', 'n', 's', 't', /* 9 byte long field name, vel_const */
        /* double */
        CHAN_ID_FLOAT64,
};

size_t
encoded_blendvalue_size(const struct blendvalue *val)
{
    size_t s = 0;

    (void) val;
    s += sizeof(double);
    s += sizeof(double);
    s += sizeof(double);

    return s;
}

static struct chan_type _blendvalue_type = {
    .sig = blendvalue_signature_bytes,
    .sig_len = sizeof(blendvalue_signature_bytes),
    .name = "BlendValue",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_blendvalue_size,
};
const struct chan_type *blendvalue_type = &_blendvalue_type;

int
encode_blendvalue(struct chan_encoder *e, const struct blendvalue *val)
{
    int res;

    res = chan_encode_begin(e, blendvalue_type, val);
    if (res != 0) { return res; }

    res = chan_encode_blendvalue(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_blendvalue(struct chan_encoder *e, const struct blendvalue *val)
{
    int res = 0;

    res = chan_encode_float64(e, val->percentage);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->distance);
    if (res != 0) { return res; }
    res = chan_encode_float64(e, val->vel_const);
    if (res != 0) { return res; }

    return res;
}

int
chan_enc_register_blendvalue(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_blendvalue_type);
}

static int
decode_blendvalue(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct blendvalue val;
    int res = 0;

    res = chan_decode_blendvalue(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_blendvalue(struct chan_decoder *d, struct blendvalue *val)
{
    int res = 0;
    int err = 0;

    val->percentage = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->distance = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }
    val->vel_const = chan_decode_float64(d, &res);
    if (res != 0) {
        return -1;
    }

    return err;
}

int
chan_dec_register_blendvalue(struct chan_decoder *d, blendvalue_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_blendvalue_type,
            (decoding_f) decode_blendvalue,
            (app_callback_f) f,
            ctx);
}


/**
 * Juliet type:
 *
 *  struct Blend
 *    blend_type::UInt16
 *    blend_value::JuliaCompiler.BlendValue
 *    motion_id::StaticArraysCore.SVector{16, UInt8}
 *  end
 */

static const uint8_t blend_signature_bytes[] = {
    /* struct */
    CHAN_ID_STRUCT,
    /* 3 struct fields */
    0x03,
        0x0a,'b', 'l', 'e', 'n', 'd', '_', 't', 'y', 'p', 'e', /* 10 byte long field name, blend_type */
        /* uint16_t */
        CHAN_ID_UINT16,
        0x0b,'b', 'l', 'e', 'n', 'd', '_', 'v', 'a', 'l', 'u', 'e', /* 11 byte long field name, blend_value */
        /* struct blendvalue */
        CHAN_ID_STRUCT,
        /* 3 struct fields */
        0x03,
            0x0a,'p', 'e', 'r', 'c', 'e', 'n', 't', 'a', 'g', 'e', /* 10 byte long field name, percentage */
            /* double */
            CHAN_ID_FLOAT64,
            0x08,'d', 'i', 's', 't', 'a', 'n', 'c', 'e', /* 8 byte long field name, distance */
            /* double */
            CHAN_ID_FLOAT64,
            0x09,'v', 'e', 'l', '_', 'c', 'o', 'n', 's', 't', /* 9 byte long field name, vel_const */
            /* double */
            CHAN_ID_FLOAT64,
        0x09,'m', 'o', 't', 'i', 'o', 'n', '_', 'i', 'd', /* 9 byte long field name, motion_id */
        /* array of uint8_t */
        CHAN_ID_ARRAY, 0x01, 0x10, CHAN_ID_UINT8,
};

size_t
encoded_blend_size(const struct blend *val)
{
    size_t s = 0;

    s += sizeof(uint16_t);
    s += encoded_blendvalue_size(&val->blend_value);
    s += (chan_sizeof_varint((uint32_t) 1) + chan_sizeof_varint((uint32_t) 16) + 16 * sizeof(val->motion_id[0]));

    return s;
}

static struct chan_type _blend_type = {
    .sig = blend_signature_bytes,
    .sig_len = sizeof(blend_signature_bytes),
    .name = "Blend",
    .encoder_id = 0,
    .encoded_size = (size_t (*)(const void *val)) encoded_blend_size,
};
const struct chan_type *blend_type = &_blend_type;

int
encode_blend(struct chan_encoder *e, const struct blend *val)
{
    int res;

    res = chan_encode_begin(e, blend_type, val);
    if (res != 0) { return res; }

    res = chan_encode_blend(e, val);
    if (res != 0) { return res; }

    res = chan_encode_end(e);

    return res;
}

int
chan_encode_blend(struct chan_encoder *e, const struct blend *val)
{
    int res = 0;

    res = chan_encode_uint16(e, val->blend_type);
    if (res != 0) { return res; }
    res = chan_encode_blendvalue(e, &val->blend_value);
    if (res != 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)1);
    if (res <= 0) { return res; }
    res = chan_encode_varint(e, (uint32_t)16);
    if (res <= 0) { return res; }
    for (size_t i_1 = 0; i_1 < 16; i_1++) {
        res = chan_encode_uint8(e, val->motion_id[i_1]);
        if (res != 0) { return res; }
    }

    return res;
}

int
chan_enc_register_blend(struct chan_encoder *e)
{
    return chan_enc_register_type(e, &_blend_type);
}

static int
decode_blend(struct chan_decoder *d, app_callback_f f, void *ctx)
{
    struct blend val;
    int res = 0;

    res = chan_decode_blend(d, &val);
    if (res != 0) {
        if (res == -2 || res == -3) {
            return res;
        } else {
            return -1;
        }
    }

    f(&val, ctx);

    return 0;
}

int
chan_decode_blend(struct chan_decoder *d, struct blend *val)
{
    int res = 0;
    int err = 0;

    val->blend_type = chan_decode_uint16(d, &res);
    if (res != 0) {
        return -1;
    }
    res = chan_decode_blendvalue(d, &val->blend_value);
    if (res != 0) {
        return -1;
    }
    size_t dims_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dims_motion_id != 1)) {
        return -1;
    }
    size_t dim_1_motion_id = chan_decode_varint(d, &res);
    if ((res != 0) || (dim_1_motion_id) != 16) {
        return -1;
    }
    for (size_t i_1 = 0; i_1 < dim_1_motion_id; i_1++) {
        val->motion_id[i_1] = chan_decode_uint8(d, &res);
        if (res != 0) {
            return -1;
        }
    }

    return err;
}

int
chan_dec_register_blend(struct chan_decoder *d, blend_cb_f f, void *ctx)
{
    return chan_dec_register_type(d,
            &_blend_type,
            (decoding_f) decode_blend,
            (app_callback_f) f,
            ctx);
}
