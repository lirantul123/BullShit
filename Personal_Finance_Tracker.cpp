#include <iostream>
#include <vector>
#include <string>
#include <thread>
#include <chrono>
// The program Personal ensures user authentication, allowing individuals to interact with their personal finances securely. 
// While it offers essential functionalities for tracking finances, several areas, such as input validation, data persistence, 
// enhanced error handling, user interface improvements, and additional features, could be further developed for
// a more comprehensive and user-friendly experience.
#define SLEEP(x)    this_thread::sleep_for(chrono::seconds(x))// for easy understanding
using namespace std;

class User
{
public:
    User() : balance(0), phone(""), password(""), totalTries(0) {} // Resets what needed
    ~User()
    { 
        cout << "\n";
        
        for (int i = 0; i < 3; i++)
        {
            if (i == 0)
                cout << "-----------------------\n";
            if (i == 2)
                cout << "-----------------------\n";
            if (i != 0 && i != 2)    
            cout << "| THANKS, ReTuRn BaCk! |\n";
        }

    }
    void addIncome(float amount, const string& category) // Add money method
    {
        balance += amount;
        comments.push_back("Income: +" + to_string(amount) + " (" + category + ")");
    }

    void addExpense(float amount, const string& category)// Subtract money method
    {
        if (!checkBalance(amount))// Check if balance under zero
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY to withdraw\n";
            return;
        }
        
        balance -= amount;
        comments.push_back("Expense: -" + to_string(amount) + " (" + category + ")");
    }

    void addTransaction(float amount, const string& phoneGetter, vector<User*> users, User *user, string& pass)// Making transaction method
    {
        bool dontExecute = false;
        int tries = 0; const int MaxTries = 3;
        if (!checkBalance(amount))// Check if balance under zero
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY; you cannot send this amount\n";
            return;
        }

        while (pass != this->password)// The user has max 3 tries, or he waits minute
        {
            tries++;
            
            cout << "------------\n";
            cout << "You left more " << MaxTries - tries << " tries\n";
            cout << "------------\n";
            if (tries % 3 == 0)
            {
                totalTries++;
                if (totalTries == 10)// if reaches ten will reset
                    totalTries = 1;

                (totalTries > 1) ? cout << " You have to wait a "<<  totalTries << " minute now\n" : cout << " You have to wait a "<<  totalTries << " minutes now\n";
                
                SLEEP(totalTries*60);// sleep for minutes per each three tries
                cout << "------------\n";

                dontExecute = true; // won't continue execute the purpose of this method
                break;
            }
            cout << "Enter your passord agian for confirmation: ";
            cin.ignore(); 
            getline(cin, pass);
        }
    
        if (!dontExecute){
            bool receiverExists = false;
            for (auto& perUser : users) // Search the user to send the money to, and make sure it is not himself
            {
                if (perUser->phone == phoneGetter &&  perUser->phone != user->phone)
                {
                    receiverExists = true;
                    break;
                }
            }
        
            if (receiverExists)// Subtracts the money form the sender's account
            {
                balance -= amount;
                comments.push_back("Transaction: -" + to_string(amount) + " to '" + phoneGetter + "'");
                
            }
            else
                cout << "\nRECEIVER NOT FOUND; transaction canceled\n";
        }
    }

    float getBalance() const // Return the balance amount
    {
        return balance;
    }

    void displayTransactions() const // Display the whole transactions which have been executed(by the other methods)
    {
        cout << "\nTransaction History:\n";
        for (const string& comment : comments)
        {
            cout << "- " << comment << endl;
        }
    }

public:// TODO: Should be private later on
    // Define used Variables 
    float balance;
    string phone;
    string password;

    bool checkBalance(float amount) const // Check if insufficient balance
    {
        if (balance < amount)
        {
            cout << "\nINSUFFICIENT BALANCE\n";
            return false;
        }
        return true;
    }

    vector<string> comments; // because it's vector I put it here( idk-> ;) )
    int totalTries;
};

int main()
{
    vector<User*> users;
    User user1, user2, user3;
    user1.balance = 1000;
    user1.phone = "0";
    user1.password = "0";
    users.push_back(&user1);
    user2.balance = 2000;
    user2.phone = "00";
    user2.password = "00";
    users.push_back(&user2);
    user3.balance = 3000;
    user3.phone = "000";
    user3.password = "000";
    users.push_back(&user3);

    int currentUserIndex = -1;

    string ph;
    string pss;

    while (currentUserIndex == -1)
    {
        cout << "Who are you (phone): ";
        getline(cin, ph);
        cout << "And password? ";
        getline(cin, pss);

        for (int i = 0; i < users.size(); ++i)
        {
            if (users[i]->phone == ph && users[i]->password == pss)
            {
                currentUserIndex = i;
                break;
            }
        }
    }

    User* currentUser = users[currentUserIndex];
    cout << "\n";

    while (true)
    {
        cout << "Personal Finance Tracker\n";
        cout << "1. Add Income\n";
        cout << "2. Add Expense\n";
        cout << "3. Make Transaction\n";
        cout << "4. Display Balance\n";
        cout << "5. Display Transaction History\n";
        cout << "0. Exit\n";

        int choice;
        cout << "Enter your choice: ";
        cin >> choice;

        switch (choice)
        {
        case 1:
        {
            float amount;
            string category;
            cout << "Enter income amount: ";
            cin >> amount;
            cout << "Enter income category: ";
            cin.ignore();
            getline(cin, category);
            currentUser->addIncome(amount, category);
            break;
        }
        case 2:
        {
            float amount;
            string category;
            cout << "Enter expense amount: ";
            cin >> amount;
            cout << "Enter expense category: ";
            cin.ignore();
            getline(cin, category);
            currentUser->addExpense(amount, category);
            break;
        }
        case 3:
        {
            float amount;
            string phoneGetter;
            string pass;
            cout << "Enter transaction amount: ";
            cin >> amount;
            cout << "Enter the name (phone_later) whose money you want to send: ";
            cin.ignore();
            getline(cin, phoneGetter);
            cout << "Enter your password for confirmation: ";
            cin.ignore();
            getline(cin, pass);
            currentUser->addTransaction(amount, phoneGetter, users, currentUser, pass);
            break;
        }
        case 4:
            cout << "\nCurrent Balance: " << currentUser->getBalance() << endl;
            break;
        case 5:
            currentUser->displayTransactions();
            break;
        case 0:
            cout << "\nExiting the Personal Finance Tracker. Goodbye!\n";
            return 0;
        default:
            cout << "Invalid choice. Please enter a valid option (1, 2, 3, 4, 5, or 0).\n";
        }

        cout << "--------------------------------------\n\n";
    }

    return 0;
}
