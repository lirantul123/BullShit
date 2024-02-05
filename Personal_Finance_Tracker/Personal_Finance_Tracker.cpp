#include <iostream>
#include <vector>
#include <string>
#include <thread>
#include <chrono>
#include <iomanip> // For setw
#include <algorithm> // For transform
#include <cctype> // For tolower
#include <limits> // For numeric_limits
#include <cstdlib> // For system("cls") (clear screen on Windows)
using namespace std;

#define SLEEP(x) this_thread::sleep_for(chrono::seconds(x))

class User
{
public:
    User() : balance(0), phone(""), password(""), totalTries(0) {}

    // Method to add income to the user's account
    void addIncome(float amount, const string &category)
    {
        balance += amount;
        comments.push_back("Income: +" + to_string(amount) + " (" + category + ")");
    }

    // Method to add an expense to the user's account
    void addExpense(float amount, const string &category)
    {
        if (!checkBalance(amount))
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY to withdraw\n";
            return;
        }

        balance -= amount;
        comments.push_back("Expense: -" + to_string(amount) + " (" + category + ")");
    }

    // Method to make a transaction (send money to another user)
    void addTransaction(float amount, const string &phoneGetter, vector<User> &users, User &user, const string &pass)
    {
        bool dontExecute = false; string passs="";
        int tries = 0;
        const int MaxTries = 3;

        if (!checkBalance(amount))
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY; you cannot send this amount\n";
            return;
        }

        while (pass != this->password)
        {
            tries++;

            cout << "------------\n";
            cout << "You have " << MaxTries - tries << " tries left\n";
            cout << "------------\n";

            if (tries % 3 == 0)
            {
                totalTries++;
                if (totalTries == 10)
                    totalTries = 1;

                (totalTries > 1) ? cout << " You have to wait " << totalTries << " minutes now\n" : cout << " You have to wait " << totalTries << " minute now\n";

                SLEEP(totalTries * 60);
                cout << "------------\n";

                dontExecute = true;
                break;
            }

            cout << "Enter your password again for confirmation: ";
            cin.ignore();
            getline(cin, passs);
        }

        if (!dontExecute)
        {
            bool receiverExists = false;
            for (auto &perUser : users)
            {
                if (perUser.phone == phoneGetter && perUser.phone != user.phone)
                {
                    receiverExists = true;
                    break;
                }
            }

            if (receiverExists)
            {
                balance -= amount;
                comments.push_back("Transaction: -" + to_string(amount) + " to '" + phoneGetter + "'");
            }
            else
                cout << "\nRECEIVER NOT FOUND; transaction canceled\n";
        }
    }

    // Method to get the current balance of the user
    float getBalance() const
    {
        return balance;
    }

    // Method to display the transaction history
    void displayTransactions() const
    {
        cout << "\nTransaction History:\n";
        for (const string &comment : comments)
        {
            cout << "- " << comment << endl;
        }
    }

public:
    // Method to display an exit message
    void exitMessage()
    {
        cout << "\n";
        for (int i = 0; i < 3; i++)
        {
            if (i == 0)
                cout << " ----------------------\n";
            if (i == 2)
                cout << " ----------------------\n";
            if (i != 0 && i != 2)
                cout << "| THANKS, ReTuRn BaCk! |\n";
        }
    }

public:
    float balance;
    string phone;
    string password;
    vector<string> comments;
    int totalTries;

    // Method to check if there's enough balance for a transaction
    bool checkBalance(float amount) const
    {
        if (balance < amount)
        {
            cout << "\nINSUFFICIENT BALANCE\n";
            return false;
        }
        return true;
    }
};

// Function to clear the console screen
void clearScreen()
{
#ifdef _WIN32
    system("cls");
#else
    system("clear");
#endif
}

// Function to print the header of the application
void printHeader()
{
    cout << setw(30) << "Personal Finance Tracker\n"
         << setw(30) << "------------------------\n\n";
}

// Function to print the menu options
void printMenu()
{
    cout << "1. Add Income\n"
         << "2. Add Expense\n"
         << "3. Make Transaction\n"
         << "4. Display Balance\n"
         << "5. Display Transaction History\n"
         << "0. Exit\n\n";
}

// Function to print the current balance of the user
void printBalance(const User &user)
{
    cout << "\nCurrent Balance: $" << fixed << setprecision(2) << user.getBalance() << endl;
}

// Function to print the transaction history of the user
void printTransactionHistory(const User &user)
{
    user.displayTransactions();
}

// Function to get the user's choice from the menu
int getUserChoice()
{
    int choice;
    cout << "Enter your choice: ";
    while (!(cin >> choice) || (choice < 0 || choice > 5))
    {
        cout << "Invalid input. Please enter a valid option (0, 1, 2, 3, 4, or 5): ";
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
    }
    return choice;
}

// Main function of the application
int main()
{
    vector<User> users;
    User user1, user2, user3;
    user1.balance = 1000;
    user1.phone = "0";
    user1.password = "0";
    users.push_back(user1);
    user2.balance = 2000;
    user2.phone = "00";
    user2.password = "00";
    users.push_back(user2);
    user3.balance = 3000;
    user3.phone = "000";
    user3.password = "000";
    users.push_back(user3);

    int currentUserIndex = -1;

    string ph;
    string pss;

    // User authentication loop
    while (currentUserIndex == -1)
    {
        clearScreen();
        printHeader();
        cout << "Who are you (phone): ";
        getline(cin, ph);
        cout << "And password? ";
        getline(cin, pss);

        // Check if the entered credentials match a user's information
        for (int i = 0; i < users.size(); ++i)
        {
            if (users[i].phone == ph && users[i].password == pss)
            {
                currentUserIndex = i;
                break;
            }
        }
    }

    User currentUser = users[currentUserIndex];
    cout << "\n";

    // Main application loop
    while (true)
    {
        clearScreen();
        printHeader();
        printMenu();

        int choice = getUserChoice();

        switch (choice)
        {
        case 1:
        {
            float amount;
            string category;
            cout << "Enter income amount: $";
            cin >> amount;
            cout << "Enter income category: ";
            cin.ignore();
            getline(cin, category);
            currentUser.addIncome(amount, category);
            SLEEP(2);
            break;
        }
        case 2:
        {
            float amount;
            string category;
            cout << "Enter expense amount: $";
            cin >> amount;
            cout << "Enter expense category: ";
            cin.ignore();
            getline(cin, category);
            currentUser.addExpense(amount, category);
            SLEEP(2);
            break;
        }
        case 3:
        {
            float amount;
            string phoneGetter;
            string pass; // Change 'const string' to 'string'
            cout << "Enter transaction amount: $";
            cin >> amount;
            cout << "Enter the name (phone_later) whose money you want to send: ";
            cin.ignore();
            getline(cin, phoneGetter);
            cout << "Enter your password for confirmation: ";
            getline(cin, pass); // No longer const
            currentUser.addTransaction(amount, phoneGetter, users, currentUser, pass);
            SLEEP(3);
            break;
        }
        case 4:
            printBalance(currentUser);
            SLEEP(2);
            break;
        case 5:
            printTransactionHistory(currentUser);
            SLEEP(5);
            break;
        case 0:
            clearScreen();
            printHeader();
            cout << "Exiting the Personal Finance Tracker. Goodbye!\n";
            currentUser.exitMessage();
            return 0;
        default:
            cout << "Invalid choice. Please enter a valid option (1, 2, 3, 4, 5, or 0).\n";
        }

        cout << "--------------------------------------\n\n";
        SLEEP(1);
    }

    return 0;
}


// Target- reach 800 lines of code