add_subdirectory(chan-gen-types)

set(SOURCE_FILES
    main.c
    robot_server.c
    app_server.c
)

set(LOCAL_LIBS_DIR ${PROJECT_SOURCE_DIR}/libs)

# LegoHKM
add_executable(AppServer ${SOURCE_FILES})

include_directories(
    ${LOCAL_LIBS_DIR}/chan/include
    ${CMAKE_BINARY_DIR}/gen_c
    ${PROJECT_SOURCE_DIR}/include
)

target_link_libraries(AppServer PUBLIC chan-static chan-comms)
