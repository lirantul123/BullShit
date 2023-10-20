cmake_minimum_required(VERSION 3.17)
project(Game)

set(CMAKE_CXX_STANDARD 14)

find_package(SDL2 REQUIRED)

add_executable(Game
        main.cpp
        Math.cpp
        Player.cpp
        Game.cpp
        holder.h
        Player.h
        Math.h
        Game.h
        OpeningScreen.cpp
        OpeningScreen.h
)

target_link_libraries(Game SDL2::SDL2)
