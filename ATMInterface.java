import java.util.Scanner;

class BankAccount {
    private double balance;
    private String accountHolderName;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
            return false;
        }
        balance -= amount;
        return true;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void withdraw(double amount) {
        if (bankAccount.withdraw(amount)) {
            System.out.println("Withdrawal successful. Remaining balance: " + bankAccount.getBalance());
        }
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
        System.out.println("Deposit successful. New balance: " + bankAccount.getBalance());
    }

    public void checkBalance() {
        System.out.println("Current balance: " + bankAccount.getBalance());
    }

    public void showInfo() {
        System.out.println("\nAccount Holder: " + bankAccount.getAccountHolderName());
        System.out.println("Current balance: " + bankAccount.getBalance());
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        System.out.println("\n");
        for (int i = 0; i < 100; i++) {
            if(i==15){
                System.out.print(" Program Developed By : PIYUSH PATEL ");
            }
            System.out.print("-");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n Enter your name: ");
        String accountHolderName = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        BankAccount userAccount = new BankAccount(accountHolderName, initialBalance);
        ATM atm = new ATM(userAccount);

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Show Info");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 3:
                    atm.checkBalance();
                    break;
                case 4:
                    atm.showInfo();
                    break;
                case 5:
                    System.out.println("Exiting ATM. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
