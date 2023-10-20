#include "Player.h"
#include "SDL2/SDL.h"

Player::Player(float x, float y) {
    mPosition.x = x;
    mPosition.y = y;
    mRect.w = 50;
    mRect.h = 50;
}

void Player::Move(float x, float y) {
    mPreviousDirection.x = x;
    mPreviousDirection.y = y;
    mPosition.x += x;
    mPosition.y += y;
    mRect.x = static_cast<int>(mPosition.x);
    mRect.y = static_cast<int>(mPosition.y);
}

void Player::Power(float powerFactor) {
    mPosition.x += mPreviousDirection.x * powerFactor;
    mPosition.y += mPreviousDirection.y * powerFactor;
    mRect.x = static_cast<int>(mPosition.x);
    mRect.y = static_cast<int>(mPosition.y);
}

void Player::Render(SDL_Renderer* renderer) {
    SDL_SetRenderDrawColor(renderer, 255, 0, 0, 255);
    SDL_RenderFillRect(renderer, &mRect);
}
