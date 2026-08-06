import javax.swing.*;
import java.awt.*;

public class ChangePinWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;

    private final JPasswordField oldPinField;
    private final JPasswordField newPinField;
    private final JPasswordField confirmPinField;
    private final JButton updateButton;
    private final JButton cancelButton;

    public ChangePinWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "Change Security PIN", true);
        this.parentDashboard = parent;
        this.user = user;

        oldPinField = new JPasswordField(15);
        newPinField = new JPasswordField(15);
        confirmPinField = new JPasswordField(15);
        updateButton = new JButton("Update PIN");
        cancelButton = new JButton("Cancel");

        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setSize(440, 380);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Change PIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Update your 4-digit security PIN", SwingConstants.CENTER);
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10);
        mainPanel.add(subLabel, gbc);
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;

        addFormRow(mainPanel, gbc, "Current PIN:", oldPinField, 2);
        addFormRow(mainPanel, gbc, "New 4-Digit PIN:", newPinField, 3);
        addFormRow(mainPanel, gbc, "Confirm New PIN:", confirmPinField, 4);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(updateButton, new Color(0, 102, 204), Color.WHITE);
        styleButton(cancelButton, new Color(220, 225, 230), new Color(40, 50, 60));

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 0, 10);
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
        field.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(field, gbc);
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
        updateButton.addActionListener(e -> handlePinChange());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(updateButton);
    }

    private void handlePinChange() {
        String oldPin = new String(oldPinField.getPassword()).trim();
        String newPin = new String(newPinField.getPassword()).trim();
        String confirmPin = new String(confirmPinField.getPassword()).trim();

        if (oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all PIN fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "New PIN must be exactly 4 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = user.changePin(oldPin, newPin, confirmPin);
        if (success) {
            JOptionPane.showMessageDialog(this, "PIN changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change PIN. Please verify your current PIN and inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
