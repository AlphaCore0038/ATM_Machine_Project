import javax.swing.*;
import java.awt.*;

public class WithdrawWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;
    private final JTextField amountField;
    private final JPasswordField pinField;
    private final JButton withdrawButton;
    private final JButton cancelButton;

    public WithdrawWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "Withdraw Cash", true);
        this.parentDashboard = parent;
        this.user = user;

        amountField = new JTextField(15);
        pinField = new JPasswordField(15);
        withdrawButton = new JButton("Withdraw");
        cancelButton = new JButton("Cancel");

        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setSize(440, 360);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Withdraw Money", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Enter amount and verify your 4-digit PIN", SwingConstants.CENTER);
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10);
        mainPanel.add(subLabel, gbc);
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel amountLabel = new JLabel("Amount (Rs.):");
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(amountLabel, gbc);

        amountField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        amountField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(amountField, gbc);

        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(pinLabel, gbc);

        pinField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pinField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(pinField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(withdrawButton, new Color(0, 102, 204), Color.WHITE);
        styleButton(cancelButton, new Color(220, 225, 230), new Color(40, 50, 60));

        buttonPanel.add(withdrawButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 0, 10);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupEventHandlers() {
        withdrawButton.addActionListener(e -> handleWithdrawal());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(withdrawButton);
    }

    private void handleWithdrawal() {
        String amountStr = amountField.getText().trim();
        String pinStr = new String(pinField.getPassword()).trim();

        if (amountStr.isEmpty() || pinStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both withdrawal amount and PIN.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Withdrawal amount must be positive.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pinStr.equals(user.getPin())) {
                JOptionPane.showMessageDialog(this, "Incorrect PIN entered.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                pinField.setText("");
                return;
            }

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(
                    this,
                    String.format("Insufficient balance!\nCurrent Balance: Rs. %,.2f", user.getBalance()),
                    "Transaction Failed",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean success = user.withdraw(amount, pinStr);
            if (success) {
                parentDashboard.refreshBalanceDisplay();
                JOptionPane.showMessageDialog(
                    this,
                    String.format("Successfully withdrew Rs. %,.2f\nRemaining Balance: Rs. %,.2f", amount, user.getBalance()),
                    "Withdrawal Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Withdrawal failed. Please check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
