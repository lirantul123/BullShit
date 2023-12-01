#include <memory>
#include <iostream>
#include "OpeningScreen.h"


int main()
{
    std::unique_ptr<OpeningScreen> myGame(new OpeningScreen());

    if (myGame->InitializeStartScreen()) {
        myGame->RunLoopO();
    }

    return 0;
}