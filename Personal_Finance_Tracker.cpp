#include <iostream>
#include <vector>
#include <string>
using namespace std;

class User
{
public:
    User() : balance(0), phone("") {}

    void addIncome(float amount, const string& category)
    {
        balance += amount;
        comments.push_back("Income: +" + to_string(amount) + " (" + category + ")");
    }

    void addExpense(float amount, const string& category)
    {
        if (!checkBalance(amount))
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY to withdraw\n";
            return;
        }
        
        balance -= amount;
        comments.push_back("Expense: -" + to_string(amount) + " (" + category + ")");
    }

    void addTransaction(float amount, const string& phoneGetter, vector<User>& users, User user)
    {
        if (!checkBalance(amount))
        {
            cout << "\nYOU DON'T HAVE ENOUGH MONEY; you cannot send this amount\n";
            return;
        }

        bool receiverExists = false;
        for (auto& perUser : users)
        {
            if (perUser.phone == phoneGetter &&  perUser.phone != user.phone)
            {
                receiverExists = true;
                perUser.balance += amount;
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

    float getBalance() const
    {
        return balance;
    }

    void displayTransactions() const
    {
        cout << "\nTransaction History:\n";
        for (const string& comment : comments)
        {
            cout << "- " << comment << endl;
        }
    }

public:
    float balance;
    string phone;

    bool checkBalance(float amount) const
    {
        if (balance < amount)
        {
            cout << "\nINSUFFICIENT BALANCE\n";
            return false;
        }
        return true;
    }

    vector<string> comments;
};

int main()
{
    User user1;
    User user2;
    User user3;
    user1.balance = 1000;
    user1.phone = "0";
    user2.balance = 500;
    user2.phone = "00";
    user3.balance = 2000;
    user3.phone = "000";

    vector<User> users;
    users.push_back(user1);
    users.push_back(user2);
    users.push_back(user3);

    User user; 
    bool found = false;
    string ph;

    while (!found){
        cout << "Who are you: ";
        getline(cin, ph);

        for (auto& perUser : users)
        {
            if (perUser.phone == ph)
            {
                found = true;
                user = perUser;
            }
        }
    }

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
                user.addIncome(amount, category);
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
                user.addExpense(amount, category);
                break;
            }
            case 3:
            {
                float amount;
                string phoneGetter;
                cout << "Enter transaction amount: ";
                cin >> amount;
                cout << "Enter the name(phone_later) whose money you want to send: ";
                cin.ignore(); 
                getline(cin, phoneGetter);
                user.addTransaction(amount, phoneGetter, users, user);
                break;

            }
            case 4:
                cout << "\nCurrent Balance: " << user.getBalance() << endl;
                break;
            case 5:
                user.displayTransactions();
                break;
            case 0:
                cout << "\nExiting the Personal Finance Tracker. Goodbye!\n";
                return 0;
            default:
                cout << "Invalid choice. Please enter a valid option.\n";
        }

        cout << "--------------------------------------\n\n";
    }

    return 0;
}
