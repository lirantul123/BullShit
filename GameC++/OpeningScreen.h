#ifndef GAME_OPENINGSCREEN_H
#define GAME_OPENINGSCREEN_H

#include "SDL2/SDL.h"
#include <map>
#include <string>
#include "Game.h"

class OpeningScreen {
public:
    OpeningScreen();
    ~OpeningScreen();

    bool InitializeStartScreen();
    void RunLoopO();
    bool eio = false;


    //void loadFont(const std::string& fontImagePath, int charWidth, int charHeight);
    //void renderText(const std::string& text, int x, int y);

private:
    SDL_Window* mWindow;
    SDL_Renderer* mRenderer;

    bool StartGame();
    void GenerateOutputO();
    void ProcessInputO();
    void UpdateGameO();
    void ShutDownO();

    //void RenderText(const std::string& text, int x, int y, SDL_Color textColor);


    Game* toStartGame = (Game*)malloc(sizeof(Game));

    //SDL_Texture* fontTexture;
    //int charWidth;
    //int charHeight;
    //std::map<char, SDL_Rect> charMap;

    //void loadCharacterMap();
};

#endif // GAME_OPENINGSCREEN_H
