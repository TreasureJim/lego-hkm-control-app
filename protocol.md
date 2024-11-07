# Protocol

This document will describe the communication protocol between the app and the juliet server.

Each struct will have a signature for decoding. This will be in the format `<variable_name: how many bytes>`. Eg. `<s_id: 1><status: 4>` meaning 1 byte for `s_id` and 4 bytes for `status`.

Each message will begin with a `1 byte` struct identifier. This is called the `s_id` and will tell the protocol which struct signature to decode with. 

## Structs

`s_id`: 0
```
struct MotionId
    id::SVector{16, UInt8}
    status::Int8
end
```
Sig: `<s_id: 1><id: 16><status: 1>`

`s_id`: 1
```
struct MoveJog
    motion_id::SVector{16, UInt8}
    x::Float64
    y::Float64
    z::Float64
    j1::Float64
    j2::Float64
    j3::Float64
    j4::Float64
end
```
Sig: `<s_id: 1><motion_id: 16><x: 8><y: 8><z: 8><j1: 8><j2: 8><j3: 8><j4: 8>`

`s_id`: 2
```
struct RobotRequestStatus
end
```
Sig: `<s_id: 1>`

`s_id`: 3
```
struct RobotStatus
    x::Float64
    y::Float64
    z::Float64
    j1::Float64
    j2::Float64
    j3::Float64
    j4::Float64
end
```
Sig: `<s_id: 1><motion_id: 16><x: 8><y: 8><z: 8><j1: 8><j2: 8><j3: 8><j4: 8>`