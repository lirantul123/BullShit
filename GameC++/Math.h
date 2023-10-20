//
// Created by Liran Tulchinski on 22/09/2023.
//

#ifndef GAME_MATH_H
#define GAME_MATH_H

struct Vector2 {
    float x;
    float y;
    float z;

    float distance(const Vector2& other) const;

};

#endif //GAME_MATH_H
