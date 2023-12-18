cmake_minimum_required(VERSION 3.12)
project(pg_service CXX)

include(third_party/userver/cmake/SetupEnvironment.cmake)
include(GNUInstallDirs)

add_subdirectory(third_party/userver)


# The Service
add_executable(${PROJECT_NAME}
        src/db/queries.hpp
        src/db/queries.cpp
        src/db/repository.hpp
        src/db/repository.cpp
        src/models/game.hpp
        src/views/scores/get/view.hpp
        src/views/scores/get/view.cpp
    src/main.cpp
)
target_link_libraries(${PROJECT_NAME} PUBLIC userver-postgresql)
target_include_directories(${PROJECT_NAME} PUBLIC src)


# Functional Tests
include(UserverTestsuite)

userver_testsuite_add_simple(
    REQUIREMENTS "${CMAKE_CURRENT_SOURCE_DIR}/tests/requirements.txt"
)


# Install
if(DEFINED ENV{PREFIX})
  message(STATUS "Set install prefix: $ENV{PREFIX}")
  file(TO_CMAKE_PATH "$ENV{PREFIX}" PREFIX_PATH)
  set(CMAKE_INSTALL_PREFIX ${PREFIX_PATH})
endif()

file(GLOB CONFIGS_FILES ${CMAKE_CURRENT_SOURCE_DIR}/configs/*.yaml ${CMAKE_CURRENT_SOURCE_DIR}/configs/*.json)

install(TARGETS ${PROJECT_NAME} DESTINATION ${CMAKE_INSTALL_BINDIR} COMPONENT ${PROJECT_NAME})
install(FILES ${CONFIGS_FILES} DESTINATION ${CMAKE_INSTALL_SYSCONFDIR}/${PROJECT_NAME} COMPONENT ${PROJECT_NAME})
