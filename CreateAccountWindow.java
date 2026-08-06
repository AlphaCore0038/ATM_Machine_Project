import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccountWindow extends JFrame {

    private final ATM atm;
    private final JTextField nameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JPasswordField pinField;
    private final JTextField depositField;
    private final JButton createButton;
    private final JButton backButton;

    public CreateAccountWindow(ATM atm) {
        this.atm = atm;

        nameField = new JTextField(15);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        pinField = new JPasswordField(15);
        depositField = new JTextField(15);
        createButton = new JButton("Create Account");
        backButton = new JButton("Back");

        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setTitle("Create New Account");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Create New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 10, 20, 10);
        mainPanel.add(titleLabel, gbc);
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;

        addFormRow(mainPanel, gbc, "Full Name:", nameField, 1);
        addFormRow(mainPanel, gbc, "Username:", usernameField, 2);
        addFormRow(mainPanel, gbc, "Password:", passwordField, 3);
        addFormRow(mainPanel, gbc, "Confirm Password:", confirmPasswordField, 4);
        addFormRow(mainPanel, gbc, "4-Digit PIN:", pinField, 5);
        addFormRow(mainPanel, gbc, "Initial Deposit (Rs.):", depositField, 6);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(300, 50));

        styleButton(createButton);
        styleButton(backButton);

        buttonPanel.add(createButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(field, gbc);
    }

    private void styleButton(JButton button) {
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 82, 184));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 102, 204));
            }
        });
    }

    private void setupEventHandlers() {
        createButton.addActionListener(e -> handleCreateAccount());

        backButton.addActionListener(e -> {
            dispose();
            new LoginWindow(atm).setVisible(true);
        });

        getRootPane().setDefaultButton(createButton);
    }

    private void handleCreateAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String pin = new String(pinField.getPassword()).trim();
        String depositStr = depositField.getText().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() ||
            confirmPassword.isEmpty() || pin.isEmpty() || depositStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (atm.findUser(username) != null) {
            JOptionPane.showMessageDialog(
                this,
                "Username is already taken. Please choose a different username.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                this,
                "Passwords do not match.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (pin.length() != 4 || !pin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(
                this,
                "PIN must be exactly 4 digits.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositStr);
            if (initialDeposit < 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Initial deposit cannot be negative.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid deposit amount. Please enter a valid number.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String accountNumber = "ACC" + atm.getNextAccountNumber();
        UserAccount newAccount = new UserAccount(name, username, password, pin, initialDeposit, accountNumber);

        atm.addAccount(newAccount);

        JOptionPane.showMessageDialog(
            this,
            "Account Created Successfully!\n\nYour Account Number is: " + accountNumber,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
        new LoginWindow(atm).setVisible(true);
    }
}