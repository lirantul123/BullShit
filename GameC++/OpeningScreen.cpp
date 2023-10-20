#include "OpeningScreen.h"
#include <iostream>
#include <memory>
#include "holder.h"
#include "SDL.h" // Include SDL header

OpeningScreen::OpeningScreen()
        : mWindow(nullptr),
          mRenderer(nullptr),
          eio(false) {
    std::srand(static_cast<unsigned int>(std::time(nullptr)));
}

OpeningScreen::~OpeningScreen() {
    ShutDownO();
    free(toStartGame);
}

void OpeningScreen::ShutDownO() {
    SDL_DestroyRenderer(mRenderer);
    SDL_DestroyWindow(mWindow);
    SDL_Quit();
}

bool OpeningScreen::InitializeStartScreen() {
    if (SDL_Init(SDL_INIT_VIDEO) != 0) {
        std::cerr << "SDL_Init Error: " << SDL_GetError() << std::endl;
        return false;
    }

    mWindow = SDL_CreateWindow("Entity Devourer: Race Against Time- Details Page", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED,
                               SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN);
    if (!mWindow) {
        std::cerr << "SDL_CreateWindow Error: " << SDL_GetError() << std::endl;
        SDL_Quit();
        return false;
    }

    mRenderer = SDL_CreateRenderer(mWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    if (!mRenderer) {
        std::cerr << "SDL_CreateRenderer Error: " << SDL_GetError() << std::endl;
        SDL_DestroyWindow(mWindow);
        SDL_Quit();
        return false;
    }

    eio = true;

    return true;
}

//// Function to render text using a bitmap font
//void OpeningScreen::RenderText(const std::string& text, int x, int y, SDL_Color textColor) {
//
//    // Load the bitmap font texture (replace "font.png" with your font file)
//
//    SDL_Surface* fontSurface = SDL_LoadBMP("myFont.ttf");
//    if (!fontSurface) {
//        std::cerr << "SDL_LoadBMP Error: " << SDL_GetError() << std::endl;
//        return;
//    }
//
//    SDL_Texture* fontTexture = SDL_CreateTextureFromSurface(mRenderer, fontSurface);
//    SDL_FreeSurface(fontSurface);
//
//    if (!fontTexture) {
//        std::cerr << "SDL_CreateTextureFromSurface Error: " << SDL_GetError() << std::endl;
//        return;
//    }
//
//    int charWidth = 16; // Adjust according to your font's character size
//    int charHeight = 16;
//
//    // Map characters to their positions in the bitmap font
//    std::string characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//    int charIndex;
//
//    for (char c : text) {
//        if (c == ' ') {
//            // Handle spaces
//            x += charWidth;
//            continue;
//        }
//
//        charIndex = characters.find(c);
//        if (charIndex != std::string::npos) {
//            SDL_Rect srcRect = { charIndex * charWidth, 0, charWidth, charHeight };
//            SDL_Rect destRect = { x, y, charWidth, charHeight };
//
//            SDL_RenderCopy(mRenderer, fontTexture, &srcRect, &destRect);
//
//            x += charWidth; // Move to the next character
//        }
//    }
//
//    SDL_DestroyTexture(fontTexture);
//}

void OpeningScreen::GenerateOutputO() {
    // Clear the screen to black
    SDL_SetRenderDrawColor(mRenderer, 0, 0, 0, 255);
    SDL_RenderClear(mRenderer);

//    // Render text, input fields, and button
//    SDL_Color textColor = { 255, 255, 255, 255 }; // White color
//
//    // Render text for time limit input
//    RenderText("Enter time limit (seconds):", 50, 100, textColor);
//
//    // Render text for player speed input
//    RenderText("Enter player speed (5-25):", 50, 150, textColor);
//
//    // Render a button
//    RenderText("Start Game", 50, 200, textColor);

    // Update the screen
    SDL_RenderPresent(mRenderer);
}

void OpeningScreen::RunLoopO() {
    while (eio) {
        ProcessInputO();  // Process input events
        UpdateGameO();    // Update game logic
        GenerateOutputO(); // Render the screen
        SDL_Delay(16);   // Cap frame rate (approximately 60 FPS)
    }
}

void OpeningScreen::ProcessInputO() {
    SDL_Event event;
    while (SDL_PollEvent(&event)) {
        if (event.type == SDL_QUIT) {
            eio = false;
            std::cout << "Closed by 'X' button." << std::endl;
        }
        else if (event.type == SDL_KEYDOWN) {
            switch (event.key.keysym.sym) {
                case SDLK_ESCAPE:
                    eio = false;
                    std::cout << "Closed by 'Escape' button." << std::endl;
                    break;

                case SDLK_RETURN:
                    StartGame();
                    break;

                default:
                    break;
            }
        }
    }
}

void OpeningScreen::UpdateGameO() {
    // Once the user clicks on the button after filling the two inputs - sent to the game
    // if (!eio)
    // {
    // }
}

bool OpeningScreen::StartGame() {
    if (toStartGame->Initialize()) {
        toStartGame->RunLoop();
    }
    return true;
}
