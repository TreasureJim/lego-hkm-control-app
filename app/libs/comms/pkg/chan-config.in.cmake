set(CHAN_VERSION @PROJECT_VERSION@)

@PACKAGE_INIT@

set_and_check(CHAN_INCLUDE_DIR "@PACKAGE_INCLUDE_INSTALL_DIR@")
set_and_check(CHAN_LIBRARY_DIR "@PACKAGE_LIBRARY_INSTALL_DIR@")

INCLUDE("${CMAKE_CURRENT_LIST_DIR}/libchanTargets.cmake")

message(STATUS "chan version: ${CHAN_VERSION}")
message(STATUS "chan include location: ${CHAN_INCLUDE_DIR}")
message(STATUS "chan library location: ${CHAN_LIBRARY_DIR}")