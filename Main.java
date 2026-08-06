import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("ATM System Starting...");
            System.out.println("Test account -> Username: demo | Password: demo123 | PIN: 1234");

            ATM atm = new ATM();
            new LoginWindow(atm).setVisible(true);
        });
    }
}