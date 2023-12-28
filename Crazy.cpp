#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>

class PetRock {
public:
    PetRock(const std::string& name) : name(name), happiness(50), hunger(50) {}

    void play() {
        std::cout << name << " is happy! ";
        increaseHappiness(10);
        printStatus();
    }

    void feed() {
        std::cout << name << " is full! ";
        decreaseHunger(10);
        printStatus();
    }

    void talk() {
        std::string phrases[] = {"Hi there!", "Rock on!", "I'm a rolling stone!", "Let's rock and roll!"};
        int index = rand() % (sizeof(phrases) / sizeof(phrases[0]));
        std::cout << name << ": " << phrases[index] << std::endl;
    }

    void printStatus() {
        std::cout << "Happiness: " << happiness << ", Hunger: " << hunger << std::endl;
    }

    std::string getName() const { return name; }

private:
    std::string name;
    int happiness;
    int hunger;

    void increaseHappiness(int amount) {
        happiness += amount;
        if (happiness > 100) happiness = 100;
    }

    void decreaseHunger(int amount) {
        hunger -= amount;
        if (hunger < 0) hunger = 0;
    }
};

int main() {
    std::srand(std::time(0));

    std::cout << "Welcome to the Virtual Pet Rock Simulator!" << std::endl;
    std::cout << "Enter a name for your pet rock: ";
    
    std::string petName;
    std::cin >> petName;

    PetRock myRock(petName);

    char choice;
    do {
        std::cout << "\nOptions:\n";
        std::cout << "1. Play\n";
        std::cout << "2. Feed\n";
        std::cout << "3. Talk\n";
        std::cout << "4. Quit\n";
        std::cout << "Choose an option: ";

        std::cin >> choice;

        switch (choice) {
            case '1':
                myRock.play();
                break;
            case '2':
                myRock.feed();
                break;
            case '3':
                myRock.talk();
                break;
            case '4':
                std::cout << "Goodbye! Thanks for playing with " << myRock.getName() << ".\n";
                break;
            default:
                std::cout << "Invalid choice. Try again.\n";
        }
    } while (choice != '4');

    return 0;
}
