import javax.swing.*;
import java.awt.*;

public class TransactionHistoryWindow extends JDialog {

    private final DashboardWindow parentDashboard;
    private final UserAccount user;

    public TransactionHistoryWindow(DashboardWindow parent, UserAccount user) {
        super(parent, "Transaction History", true);
        this.parentDashboard = parent;
        this.user = user;

        initializeUI();
    }

    private void initializeUI() {
        setSize(480, 480);
        setResizable(false);
        setLocationRelativeTo(parentDashboard);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.LEFT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));

        JLabel subLabel = new JLabel("Account: " + user.getAccountNumber() + " | " + user.getName());
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] history = user.getTransactionHistory();
        int count = user.getTransactionCount();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (count == 0) {
            listModel.addElement("No transactions recorded yet.");
        } else {
            for (int i = 0; i < count; i++) {
                listModel.addElement((i + 1) + ". " + history[i]);
            }
        }

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));
        list.setFixedCellHeight(35);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
                if (!isSelected) {
                    label.setBackground(index % 2 == 0 ? new Color(248, 250, 252) : Color.WHITE);
                } else {
                    label.setBackground(new Color(0, 102, 204));
                    label.setForeground(Color.WHITE);
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230), 1, true));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);

        JButton closeButton = new JButton("Close");
        closeButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setBackground(new Color(0, 102, 204));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        footerPanel.add(closeButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
