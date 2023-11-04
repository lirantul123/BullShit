#include <iostream>
#include <string>

class BankAccount {
public:
    BankAccount(std::string accountNumber, float balance) {
        this->accountNumber = accountNumber;
        this->balance = balance;
    }

    std::string getAccountNumber() const {
        return accountNumber;
    }

    float getBalance() const {
        return balance;
    }

    void deposit(float amount) {
        balance += amount;
    }

    void withdraw(float amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            std::cout << "Insufficient balance." << std::endl;
        }
    }

private:
    std::string accountNumber;
    float balance;
};

int main() {
    // Create an array of BankAccount objects
    const int numAccounts = 5;
    BankAccount accounts[numAccounts] = {
        BankAccount("AC001", 1000.0),
        BankAccount("AC002", 1500.0),
        BankAccount("AC003", 800.0),
        BankAccount("AC004", 2000.0),
        BankAccount("AC005", 1200.0)
    };

    std::string accountNumber;
    std::string act;
    float money;
    bool found = false;
    
    std::cout << "What is your account number?" << std::endl;
    std::getline(std::cin, accountNumber);

    for (int i = 0; i < numAccounts; i++)
    {
        if (accounts[i].getAccountNumber() ==  accountNumber)
        {
            found = true;
            do
            {
                std::cout << "Do you want to add or subtract from your account? (deposit-de/withdrow-wi/stop): ";
                std::cin >> act;

                if (act == "deposit" || act == "de")
                {
                    std::cout << "How much do you want to deposit to your account? ";
                    std::cin >> money;
                    accounts[i].deposit(money);
                }
                else if (act == "withdrow" || act == "wi")
                {
                    std::cout << "How much do you want to withdrow from your account? ";
                    std::cin >> money;
                    accounts[i].withdraw(money);
                }
                else if (act == "stop")
                {
                    break; 
                }
                else
                {
                    std::cout << "Invalid action. Please enter 'add', 'subtract', or 'stop'." << std::endl;
                }

            } while (true);

            std::cout << "Your account status: " << accounts[i].getBalance() << std::endl;
        }
        if (found)
            break;
    }
    if (!found) 
        std::cout << "\nYour account has not been found in our system,\nAre you sure this is your account?" << accountNumber << std::endl;

    return 0;
}
