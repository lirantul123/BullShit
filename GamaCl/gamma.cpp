#include <iostream>
#include <vector>

class Animal
{
public:
    Animal(std::vector<char>& gVec) : name(gVec) {}

    virtual void setSize(float size) = 0;
    std::vector<char> getName() const { return name; }

private:
    std::vector<char> name;
};

class AnimalInstance : public Animal
{
public:
    AnimalInstance(std::vector<char>& gVec, float initialSize) : Animal(gVec), size(initialSize) {}

    void setSize(float newSize) override
    {
        size = newSize;
    }

    void printSize() const
    {
        std::cout << "Size: " << size << std::endl;
    }

private:
    float size;
};

template<typename T>
// T = Animla
void Print(T* a)
{
    std::cout << "Name: ";
    for (char c : a->getName())
    {
        std::cout << c;
    }
    std::cout << std::endl;


    a->setSize(10.0f); 
}

int main()
{
    std::vector<char> vec;
    vec.push_back('e');
    vec.push_back('l');
    vec.push_back('i');

    AnimalInstance* animal = new AnimalInstance(vec, 5.0f);

    Print(animal);

    animal->printSize();

    delete animal;

    return 0;
}
