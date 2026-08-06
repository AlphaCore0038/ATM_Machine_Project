import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;

    public ReceiptWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "ATM Digital Receipt", true);
        this.parentDashboard = parent;
        this.user = user;

        initializeUI();
    }

    private void initializeUI() {
        setSize(420, 500);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel receiptCard = new JPanel();
        receiptCard.setLayout(new BoxLayout(receiptCard, BoxLayout.Y_AXIS));
        receiptCard.setBackground(Color.WHITE);
        receiptCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 215, 220), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JLabel logoLabel = new JLabel("=== ATM SYSTEM RECEIPT ===", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        logoLabel.setForeground(new Color(0, 102, 204));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subHeader = new JLabel("Official Bank Transaction Record", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.ITALIC, 11));
        subHeader.setForeground(Color.GRAY);
        subHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(350, 2));

        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        JPanel infoPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(createReceiptLine("Date & Time:", dateStr));
        infoPanel.add(createReceiptLine("Account No:", user.getAccountNumber()));
        infoPanel.add(createReceiptLine("Account Holder:", user.getName()));
        infoPanel.add(createReceiptLine("Username:", user.getUsername()));
        infoPanel.add(createReceiptLine("Current Balance:", String.format("Rs. %,.2f", user.getBalance())));
        infoPanel.add(createReceiptLine("Total Logged Ops:", String.valueOf(user.getTransactionCount())));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(350, 2));

        JLabel footerLabel = new JLabel("Thank you for using our ATM!", SwingConstants.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        footerLabel.setForeground(new Color(60, 70, 80));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        receiptCard.add(logoLabel);
        receiptCard.add(Box.createRigidArea(new Dimension(0, 3)));
        receiptCard.add(subHeader);
        receiptCard.add(Box.createRigidArea(new Dimension(0, 15)));
        receiptCard.add(sep1);
        receiptCard.add(Box.createRigidArea(new Dimension(0, 15)));
        receiptCard.add(infoPanel);
        receiptCard.add(Box.createRigidArea(new Dimension(0, 15)));
        receiptCard.add(sep2);
        receiptCard.add(Box.createRigidArea(new Dimension(0, 15)));
        receiptCard.add(footerLabel);

        mainPanel.add(receiptCard, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 247, 250));

        JButton printBtn = new JButton("Print / Save");
        printBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        printBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        printBtn.setBackground(new Color(0, 102, 204));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        printBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        printBtn.addActionListener(e -> {
            user.printReceipt();
            JOptionPane.showMessageDialog(this, "Receipt sent to printer and logged to console!", "Receipt Printed", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton closeBtn = new JButton("Close");
        closeBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        closeBtn.setBackground(new Color(220, 225, 230));
        closeBtn.setForeground(new Color(40, 50, 60));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());

        buttonPanel.add(printBtn);
        buttonPanel.add(closeBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createReceiptLine(String label, String value) {
        JPanel line = new JPanel(new BorderLayout());
        line.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(Color.DARK_GRAY);

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 13));
        val.setForeground(Color.BLACK);

        line.add(lbl, BorderLayout.WEST);
        line.add(val, BorderLayout.EAST);
        return line;
    }
}
