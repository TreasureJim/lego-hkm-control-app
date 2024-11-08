cmake_minimum_required(VERSION 3.3)

project(AppServer VERSION 0.1)
set(CMAKE_CXX_STANDARD 23)

add_compile_options(-g -O0)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/libs)

add_subdirectory(libs/chan)

add_subdirectory(src)
