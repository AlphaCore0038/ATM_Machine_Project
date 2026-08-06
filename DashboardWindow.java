import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardWindow extends JFrame {

    private final ATM atm;
    private final UserAccount user;
    private JLabel balanceValueLabel;
    private JLabel userGreetingLabel;

    public DashboardWindow(ATM atm) {
        this.atm = atm;
        this.user = atm.getCurrentUser();

        initializeUI();
    }

    private void initializeUI() {
        setTitle("ATM System - Dashboard");
        setSize(560, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel headerCard = new JPanel(new BorderLayout());
        headerCard.setBackground(Color.WHITE);
        headerCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1, true),
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));
        headerCard.setMaximumSize(new Dimension(500, 100));

        JPanel headerTextPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerTextPanel.setBackground(Color.WHITE);

        userGreetingLabel = new JLabel("Welcome back, " + (user != null ? user.getName() : "Valued Customer") + "!");
        userGreetingLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        userGreetingLabel.setForeground(new Color(30, 40, 50));

        JLabel accNoLabel = new JLabel("Account Number: " + (user != null ? user.getAccountNumber() : "N/A"));
        accNoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        accNoLabel.setForeground(new Color(100, 110, 120));

        headerTextPanel.add(userGreetingLabel);
        headerTextPanel.add(accNoLabel);
        headerCard.add(headerTextPanel, BorderLayout.CENTER);

        JPanel balanceCard = new JPanel(new BorderLayout());
        balanceCard.setBackground(new Color(0, 102, 204));
        balanceCard.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
        balanceCard.setMaximumSize(new Dimension(500, 95));

        JLabel balanceTitleLabel = new JLabel("AVAILABLE BALANCE");
        balanceTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        balanceTitleLabel.setForeground(new Color(210, 235, 255));

        balanceValueLabel = new JLabel(formatBalance());
        balanceValueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        balanceValueLabel.setForeground(Color.WHITE);

        balanceCard.add(balanceTitleLabel, BorderLayout.NORTH);
        balanceCard.add(balanceValueLabel, BorderLayout.SOUTH);

        JLabel servicesTitle = new JLabel("Select ATM Service");
        servicesTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        servicesTitle.setForeground(new Color(40, 50, 60));
        servicesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        gridPanel.setBackground(new Color(245, 247, 250));
        gridPanel.setMaximumSize(new Dimension(500, 280));

        JButton depositBtn = createServiceButton("Deposit Money", "Deposit funds into account");
        JButton withdrawBtn = createServiceButton("Withdraw Money", "Withdraw cash using PIN");
        JButton historyBtn = createServiceButton("Transaction History", "View last 10 transactions");
        JButton receiptBtn = createServiceButton("Print Receipt", "View digital account receipt");
        JButton changePinBtn = createServiceButton("Change PIN", "Update 4-digit security PIN");
        JButton changePassBtn = createServiceButton("Change Password", "Update account password");
        JButton refreshBtn = createServiceButton("Refresh Balance", "Fetch latest account balance");
        JButton logoutBtn = createLogoutButton();

        depositBtn.addActionListener(e -> openDepositWindow());
        withdrawBtn.addActionListener(e -> openWithdrawWindow());
        historyBtn.addActionListener(e -> openTransactionHistoryWindow());
        receiptBtn.addActionListener(e -> openReceiptWindow());
        changePinBtn.addActionListener(e -> openChangePinWindow());
        changePassBtn.addActionListener(e -> openChangePasswordWindow());
        refreshBtn.addActionListener(e -> refreshBalanceDisplay());
        logoutBtn.addActionListener(e -> handleLogout());

        gridPanel.add(depositBtn);
        gridPanel.add(withdrawBtn);
        gridPanel.add(historyBtn);
        gridPanel.add(receiptBtn);
        gridPanel.add(changePinBtn);
        gridPanel.add(changePassBtn);
        gridPanel.add(refreshBtn);
        gridPanel.add(logoutBtn);

        mainPanel.add(headerCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(balanceCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(servicesTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        mainPanel.add(gridPanel);

        add(mainPanel);
    }

    private String formatBalance() {
        if (user == null) return "Rs. 0.00";
        return String.format("Rs. %,.2f", user.getBalance());
    }

    public void refreshBalanceDisplay() {
        if (user != null) {
            balanceValueLabel.setText(formatBalance());
        }
    }

    private JButton createServiceButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 102, 204));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true),
            BorderFactory.createEmptyBorder(12, 10, 12, 10)
        ));
        button.setToolTipText(tooltip);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 102, 204));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(0, 102, 204));
            }
        });

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(220, 53, 69));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 30, 45), 2, true),
            BorderFactory.createEmptyBorder(12, 10, 12, 10)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 30, 45));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(220, 53, 69));
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private void openDepositWindow() {
        new DepositWindow(this, user).setVisible(true);
    }

    private void openWithdrawWindow() {
        new WithdrawWindow(this, user).setVisible(true);
    }

    private void openTransactionHistoryWindow() {
        new TransactionHistoryWindow(this, user).setVisible(true);
    }

    private void openReceiptWindow() {
        new ReceiptWindow(this, user).setVisible(true);
    }

    private void openChangePinWindow() {
        new ChangePinWindow(this, user).setVisible(true);
    }

    private void openChangePasswordWindow() {
        new ChangePasswordWindow(this, user).setVisible(true);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            atm.setCurrentUser(null);
            dispose();
            new LoginWindow(atm).setVisible(true);
        }
    }
}
