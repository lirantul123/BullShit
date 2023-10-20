#pragma once

#include "SDL_render.h"
#include "../Math.h"

class Player {
public:
    Player(float x, float y);
    void Move(float x, float y);
    void Power(float powerFactor);
    void Render(SDL_Renderer* renderer);

private:
    Vector2 mPosition;
    SDL_Rect mRect;
    Vector2 mPreviousDirection;
};
