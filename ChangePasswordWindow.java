import javax.swing.*;
import java.awt.*;

public class ChangePasswordWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;

    private final JPasswordField oldPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;
    private final JButton updateButton;
    private final JButton cancelButton;

    public ChangePasswordWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "Change Account Password", true);
        this.parentDashboard = parent;
        this.user = user;

        oldPasswordField = new JPasswordField(15);
        newPasswordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        updateButton = new JButton("Update Password");
        cancelButton = new JButton("Cancel");

        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setSize(450, 380);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Change Password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Update your account login password", SwingConstants.CENTER);
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10);
        mainPanel.add(subLabel, gbc);
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;

        addFormRow(mainPanel, gbc, "Current Password:", oldPasswordField, 2);
        addFormRow(mainPanel, gbc, "New Password:", newPasswordField, 3);
        addFormRow(mainPanel, gbc, "Confirm New Password:", confirmPasswordField, 4);

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
        updateButton.addActionListener(e -> handlePasswordChange());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(updateButton);
    }

    private void handlePasswordChange() {
        String oldPassword = new String(oldPasswordField.getPassword()).trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all password fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPassword.length() < 4) {
            JOptionPane.showMessageDialog(this, "New password must be at least 4 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = user.changePassword(oldPassword, newPassword, confirmPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change password. Please verify current password and matching fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
