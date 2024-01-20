#include <iostream>
#include <vector>


template<typename T>
class String
{
public:
    String(const T* str) {
        length = strlen(str);
        elements.reserve(length);
        for (int i = 0; i < length; ++i) {
            elements.push_back(str[i]);
        }
    }

    ~String() {
    }

    const std::vector<T>& getElements() const {
        return elements;
    }

    String& operator+=(const T* str) {
        int strLength = strlen(str);
        elements.reserve(length + strLength);

        for (int i = 0; i < strLength; ++i) {
            elements.push_back(str[i]);
        }

        length += strLength;
        return *this;
    }
    String& operator-=(const T* str) {
        int strLength = strlen(str);
        if (length >= strLength) {
            elements.erase(elements.end() - strLength, elements.end());
            length -= strLength;
        }
        else {
            elements.clear();
            length = 0;
        }
        return *this;
    }

private:
    int length;
    std::vector<T> elements;
};


int main()
{
    String<char> name("yogin");
    name += "moshi";
    name += "choen";
    name -= "moshi";// deletes by number and not by characters

    for (auto& let : name.getElements())
    {
        std::cout << let << "";
    }
    std::cout << "\n";

}

