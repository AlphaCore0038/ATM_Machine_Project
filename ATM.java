import java.util.Scanner;

public class ATM {
    private UserAccount[] accounts;
    private int accountCount;
    private UserAccount currentUser;
    private Scanner scanner;
    private int accountCounter;

    public ATM() {
        accounts = new UserAccount[100];
        accountCount = 0;
        scanner = new Scanner(System.in);
        accountCounter = 1001;

        accounts[accountCount++] = new UserAccount("Demo User", "demo", "demo123", "1234", 5000.0, "ACC" + accountCounter++);
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== ATM MAIN MENU ===");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                handleLogin();
            } else if (choice.equals("2")) {
                createNewAccount();
            } else if (choice.equals("3")) {
                System.out.println("\nThank you for using our ATM. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    private void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        UserAccount found = findUser(username);

        if (found != null && found.getPassword().equals(password)) {
            currentUser = found;
            System.out.println("\nLogin successful! Welcome, " + currentUser.getName());
            showATMMenu();
        } else {
            System.out.println("Error: Invalid Username or Password.");
        }
    }

    private void showATMMenu() {
        while (true) {
            System.out.println("\n=== ATM SERVICES ===");
            System.out.println("User: " + currentUser.getName() + " | Account: " + currentUser.getAccountNumber());
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Change Password");
            System.out.println("7. Print Receipt");
            System.out.println("8. Logout");
            System.out.print("Select choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\nYour Current Balance is: Rs. " + currentUser.getBalance());
            } else if (choice.equals("2")) {
                depositMoney();
            } else if (choice.equals("3")) {
                withdrawMoney();
            } else if (choice.equals("4")) {
                currentUser.showTransactionHistory();
            } else if (choice.equals("5")) {
                changePIN();
            } else if (choice.equals("6")) {
                changePassword();
            } else if (choice.equals("7")) {
                currentUser.printReceipt();
            } else if (choice.equals("8")) {
                System.out.println("Successfully logged out.");
                currentUser = null;
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void depositMoney() {
        System.out.println("\n--- Deposit ---");
        System.out.print("Enter amount to deposit: Rs. ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Error: Deposit amount must be positive.");
                return;
            }
            currentUser.deposit(amount);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        }
    }

    private void withdrawMoney() {
        System.out.println("\n--- Withdrawal ---");
        System.out.print("Enter amount to withdraw: Rs. ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Error: Withdrawal amount must be positive.");
                return;
            }

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            currentUser.withdraw(amount, pin);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        }
    }

    private void changePIN() {
        System.out.println("\n--- Change PIN ---");
        System.out.print("Enter old PIN: ");
        String oldPin = scanner.nextLine().trim();

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine().trim();

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.nextLine().trim();

        currentUser.changePin(oldPin, newPin, confirmPin);
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter old Password: ");
        String oldPassword = scanner.nextLine().trim();

        System.out.print("Enter new Password: ");
        String newPassword = scanner.nextLine().trim();

        System.out.print("Confirm new Password: ");
        String confirmPassword = scanner.nextLine().trim();

        currentUser.changePassword(oldPassword, newPassword, confirmPassword);
    }

    private void createNewAccount() {
        System.out.println("\n--- Create New Account ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        if (findUser(username) != null) {
            System.out.println("Error: Username is already taken.");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Set 4-digit PIN: ");
        String pin = scanner.nextLine().trim();

        if (pin.length() != 4) {
            System.out.println("Error: PIN must be exactly 4 digits.");
            return;
        }

        System.out.print("Enter Initial Deposit: Rs. ");
        try {
            double balance = Double.parseDouble(scanner.nextLine().trim());
            if (balance < 0) {
                System.out.println("Error: Initial deposit cannot be negative.");
                return;
            }

            String accNo = "ACC" + accountCounter++;
            accounts[accountCount++] = new UserAccount(name, username, password, pin, balance, accNo);

            System.out.println("\nAccount Created Successfully!");
            System.out.println("Your Account Number is: " + accNo);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid deposit amount.");
        }
    }

    public UserAccount findUser(String username) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getUsername().equalsIgnoreCase(username)) {
                return accounts[i];
            }
        }
        return null;
    }

    public void setCurrentUser(UserAccount user) {
        this.currentUser = user;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public int getNextAccountNumber() {
        return accountCounter;
    }

    public void addAccount(UserAccount account) {
        if (accountCount < accounts.length) {
            accounts[accountCount++] = account;
            accountCounter++;
        }
    }
}
