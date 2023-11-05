import java.util.Scanner;

public class BankingSystem_java {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        final int numAccounts = 5;
        Bank[] accounts = new Bank[]{           
            new Bank("AC001", 1000.0),
            new Bank("AC002", 1500.0),
            new Bank("AC003", 800.0),
            new Bank("AC004", 2000.0),
            new Bank("AC005", 1200.0)
        };
    
        int[] arr = new int[]{2, 2};

        String accountNumber;
        String act;
        double money;
        boolean found = false;
        
        System.out.print("What is your account number? ");
        accountNumber = in.nextLine();

        for (int i = 0; i < numAccounts; i++)
        {
            if (accounts[i].getAccountNumber() ==  accountNumber)
            {
                found = true;
                do
                {
                    System.out.print("Do you want to add or subtract from your account? (deposit-de/withdrow-wi/stop): ");
                    act = in.nextLine();
    
                    if (act == "deposit" || act == "de")
                    {
                        System.out.print("How much do you want to deposit to your account? ");
                        money = in.nextDouble();
                        accounts[i].deposit(money);
                    }
                    else if (act == "withdrow" || act == "wi")
                    {
                        System.out.print("How much do you want to withdrow from your account? ");
                        money = in.nextDouble();
                        accounts[i].withdraw(money);
                    }
                    else if (act == "stop")
                    {
                        break; 
                    }
                    else
                    {
                        System.out.println("Invalid action. Please enter 'add', 'subtract', or 'stop'.");
                    }
    
                } while (true);
    
                System.out.println("Your account status: " + accounts[i].getBalance());

            }
            if (found)
                break;
        }
        if (!found) 
            System.out.println("\nYour account has not been found in our system,\nAre you sure this is your account?");

    
    }
}

class Bank{

    private String accountNumber;
    private double balance;

    public Bank(String accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

}
