import javax.swing.*;
import java.awt.*;

public class DepositWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;
    private final JTextField amountField;
    private final JButton depositButton;
    private final JButton cancelButton;

    public DepositWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "Deposit Funds", true);
        this.parentDashboard = parent;
        this.user = user;

        amountField = new JTextField(15);
        depositButton = new JButton("Deposit");
        cancelButton = new JButton("Cancel");

        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setSize(420, 320);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Deposit Money", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Enter the amount you wish to deposit", SwingConstants.CENTER);
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        mainPanel.add(subLabel, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(depositButton, new Color(0, 102, 204), Color.WHITE);
        styleButton(cancelButton, new Color(220, 225, 230), new Color(40, 50, 60));

        buttonPanel.add(depositButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
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
        depositButton.addActionListener(e -> handleDeposit());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(depositButton);
    }

    private void handleDeposit() {
        String input = amountField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Deposit amount must be positive.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.deposit(amount);
            parentDashboard.refreshBalanceDisplay();

            JOptionPane.showMessageDialog(
                this,
                String.format("Successfully deposited Rs. %,.2f\nNew Balance: Rs. %,.2f", amount, user.getBalance()),
                "Deposit Successful",
                JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
