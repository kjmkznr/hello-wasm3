cmake_minimum_required(VERSION 3.13)
set(CMAKE_CXX_STANDARD 17)

set(PROJECT_NAME hello_wasm3_cpp)
set(SOURCE_DIR src)
set(SOURCE ${SOURCE_DIR}/main.cpp)

project(${PROJECT_NAME})
add_executable(${PROJECT_NAME} ${SOURCE})

get_filename_component(PARENT_DIR ../ ABSOLUTE)
add_subdirectory(${PARENT_DIR}/libs/wasm3/source ${CMAKE_BINARY_DIR}/m3)
target_include_directories(${PROJECT_NAME} PUBLIC ../libs/wasm3/platforms/cpp/wasm3_cpp/include)
target_include_directories(${PROJECT_NAME} PUBLIC ../libs/wasm3/source)
target_link_libraries(${PROJECT_NAME} PUBLIC m3)

