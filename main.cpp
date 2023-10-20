#include <memory>
#include <iostream>
#include "OpeningScreen.h"


int main()
{
    std::unique_ptr<OpeningScreen> myGame = std::make_unique<OpeningScreen>();

    if (myGame->InitializeStartScreen()) {
        myGame->RunLoopO();
    }

    return 0;
}
