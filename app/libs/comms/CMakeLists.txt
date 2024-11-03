cmake_minimum_required(VERSION 3.22)

project(CustomChanLib VERSION 1.0)

set(CMAKE_C_FLAGS_INIT "-fsanitize=undefined")
set(CMAKE_CXX_FLAGS_INIT "-fsanitize=undefined")

include_directories(chan/include)

include(chan/cmake/GlobalSettings.cmake)
include(chan/cmake/ChanConfigureTarget.cmake)
add_subdirectory(chan/src)

set(CHAN_GEN_TYPES_DIR ${CMAKE_CURRENT_SOURCE_DIR})

set(CHAN_DEF_FILES
    ${CHAN_GEN_TYPES_DIR}/defs/motion_types.jl
)

set(CHAN_GEN_FILES_CXX
    ${CMAKE_BINARY_DIR}/gen_c/motion_types.c
)
set(CHAN_GEN_FILES_OTHER
    # ${CMAKE_BINARY_DIR}/juliet-server/src/gen/motion_types.jlt
)

set(COMPILED_OUTPUT_DIR ${CMAKE_BINARY_DIR})
file(MAKE_DIRECTORY ${CMAKE_BINARY_DIR}/gen_c)
file(MAKE_DIRECTORY ${CMAKE_BINARY_DIR}/juliet-server/src/gen)

add_custom_command(
    OUTPUT ${CHAN_GEN_FILES_CXX} ${CHAN_GEN_FILES_OTHER}
    COMMAND ${CMAKE_CURRENT_SOURCE_DIR}/regen.sh ${COMPILED_OUTPUT_DIR}
    WORKING_DIRECTORY ${CHAN_GEN_TYPES_DIR}
    DEPENDS ${CHAN_DEF_FILES} ${CMAKE_CURRENT_SOURCE_DIR}/regen.sh
    COMMENT "Regenerating files using regen.sh because defs/ files changed"
    VERBATIM
)

add_library(custom_chan SHARED ${CHAN_GEN_FILES_CXX})
target_link_libraries(custom_chan PUBLIC chan)
target_link_options(custom_chan PUBLIC -fsanitize=undefined -fsanitize=address)
