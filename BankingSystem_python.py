class Bank:
    def __init__(self, accountNumber, balance):
        self.accountNumber = accountNumber
        self.balance = balance

    def getAccountNumber(self):
        return self.accountNumber

    def getBalance(self):
        return self.balance

    def deposit(self, amount):
        self.balance += amount

    def withdraw(self, amount):
        if self.balance >= amount:
            self.balance -= amount
        else:
            print("Insufficient balance.")


numAccounts = 5
accounts = [
    Bank("AC001", 1000.0),
    Bank("AC002", 1500.0),
    Bank("AC003", 800.0),
    Bank("AC004", 2000.0),
    Bank("AC005", 1200.0)
]

found = False

accountNumber = input("What is your account number? ")

for i in range(numAccounts):
    if accounts[i].getAccountNumber() == accountNumber:
        found = True
        while True:
            act = input("Do you want to add or subtract from your account? (deposit-de/withdraw-wi/stop): ")

            if act == "deposit" or act == "de":
                money = float(input("How much do you want to deposit to your account? "))
                accounts[i].deposit(money)
            elif act == "withdraw" or act == "wi":
                money = float(input("How much do you want to withdraw from your account? "))
                accounts[i].withdraw(money)
            elif act == "stop":
                break
            else:
                print("Invalid action. Please enter 'add', 'subtract', or 'stop'.")

        print("Your account status:", accounts[i].getBalance())
        break

if not found:
    print("\nYour account has not been found in our system,\nAre you sure this is your account?", accountNumber)
