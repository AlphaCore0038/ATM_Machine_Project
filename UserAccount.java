public class UserAccount {
    private String name;
    private String username;
    private String password;
    private String pin;
    private double balance;
    private String accountNumber;
    private String[] transactionHistory;
    private int transactionCount;

    public UserAccount(String name, String username, String password, String pin, double initialBalance, String accountNumber) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        this.transactionHistory = new String[10];
        this.transactionCount = 0;

        addTransaction("Account opened with Rs. " + initialBalance);
    }

    private void addTransaction(String message) {
        if (transactionCount < 10) {
            transactionHistory[transactionCount++] = message;
        } else {
            for (int i = 1; i < 10; i++) {
                transactionHistory[i - 1] = transactionHistory[i];
            }
            transactionHistory[9] = message;
        }
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposited Rs. " + amount);
        System.out.println("Successfully deposited Rs. " + amount);
        System.out.println("New Balance: Rs. " + balance);
    }

    public boolean withdraw(double amount, String enteredPin) {
        if (!enteredPin.equals(this.pin)) {
            System.out.println("Error: Incorrect PIN.");
            return false;
        }

        if (amount > balance) {
            System.out.println("Error: Insufficient balance. Current balance: Rs. " + balance);
            return false;
        }

        balance -= amount;
        addTransaction("Withdrew Rs. " + amount);
        System.out.println("Successfully withdrew Rs. " + amount);
        System.out.println("Remaining Balance: Rs. " + balance);
        return true;
    }

    public void showTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        if (transactionCount == 0) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        for (int i = 0; i < transactionCount; i++) {
            System.out.println((i + 1) + ". " + transactionHistory[i]);
        }
        System.out.println("---------------------------");
    }

    public boolean changePin(String oldPin, String newPin, String confirmPin) {
        if (!oldPin.equals(this.pin)) {
            System.out.println("Error: Incorrect old PIN.");
            return false;
        }
        if (!newPin.equals(confirmPin)) {
            System.out.println("Error: New PINs do not match.");
            return false;
        }
        if (newPin.length() != 4) {
            System.out.println("Error: PIN must be exactly 4 digits.");
            return false;
        }
        this.pin = newPin;
        addTransaction("Changed PIN");
        System.out.println("PIN changed successfully!");
        return true;
    }

    public boolean changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!oldPassword.equals(this.password)) {
            System.out.println("Error: Incorrect old password.");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Error: New passwords do not match.");
            return false;
        }
        if (newPassword.length() < 4) {
            System.out.println("Error: Password must be at least 4 characters.");
            return false;
        }
        this.password = newPassword;
        addTransaction("Changed Password");
        System.out.println("Password changed successfully!");
        return true;
    }

    public void printReceipt() {
        System.out.println("\n=======================");
        System.out.println("       ATM RECEIPT");
        System.out.println("=======================");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + name);
        System.out.println("Current Balance: Rs. " + balance);
        System.out.println("=======================");
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String[] getTransactionHistory() {
        return transactionHistory;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
