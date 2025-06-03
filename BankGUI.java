import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGUI extends JFrame {
    private BankSystem bank;
    private JTextArea displayArea;
    private JTextField nameField, idField, balanceField;

    public BankGUI() {
        showLoginScreen(); // Show login first
    }

    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setSize(350, 200);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
		loginFrame.getContentPane().setBackground(new Color(245, 248, 252));


        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        loginFrame.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(120, 30, 160, 25);
        loginFrame.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        loginFrame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 70, 160, 25);
        loginFrame.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 160, 30);
        styleButton(loginButton, new Color(33, 150, 243));
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("admin") && pass.equals("1234")) {
                loginFrame.dispose();  // Close login window
                buildMainFrame();         // Launch main app
                setVisible(true);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }

    private void buildMainFrame() {
        bank = new BankSystem(10);

        setTitle("Bank Management System");
        setSize(520, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 240, 250));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(20, 20, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        styleTextField(nameField);
        nameField.setBounds(110, 20, 150, 25);
        add(nameField);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(labelFont);
        idLabel.setBounds(20, 60, 80, 25);
        add(idLabel);

        idField = new JTextField();
        styleTextField(idField);
        idField.setBounds(110, 60, 150, 25);
        add(idField);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setFont(labelFont);
        balanceLabel.setBounds(20, 100, 80, 25);
        add(balanceLabel);

        balanceField = new JTextField();
        styleTextField(balanceField);
        balanceField.setBounds(110, 100, 150, 25);
        add(balanceField);

        JButton addBtn = new JButton("Add Customer");
        styleButton(addBtn, new Color(76, 175, 80));
        addBtn.setBounds(280, 20, 180, 30);
        add(addBtn);

        JButton deleteBtn = new JButton("Delete Customer");
        styleButton(deleteBtn, new Color(244, 67, 54));
        deleteBtn.setBounds(280, 60, 180, 30);
        add(deleteBtn);

        JButton saveBtn = new JButton("Save");
        styleButton(saveBtn, new Color(33, 150, 243));
        saveBtn.setBounds(280, 100, 85, 30);
        add(saveBtn);

        JButton loadBtn = new JButton("Load");
        styleButton(loadBtn, new Color(33, 150, 243));
        loadBtn.setBounds(375, 100, 85, 30);
        add(loadBtn);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(245, 248, 252));
        displayArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 210)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(20, 150, 440, 250);
        add(scrollPane);

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                double balance = Double.parseDouble(balanceField.getText());

                bank.addCustomer(new Customer(name, id, balance));
                displayCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding customer: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            String id = idField.getText();
            bank.deleteCustomer(id);
            displayCustomers();
        });

        saveBtn.addActionListener(e -> {
            bank.saveToFile("customers.txt");
            JOptionPane.showMessageDialog(null, "Customers saved.");
        });

        loadBtn.addActionListener(e -> {
            bank.loadFromFile("customers.txt");
            displayCustomers();
            JOptionPane.showMessageDialog(null, "Customers loaded.");
        });
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 200)));
    }

    private void styleButton(JButton button, Color color) {
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.white);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void displayCustomers() {
        displayArea.setText("");

        Customer[] customers = bank.getCustomers();
        for (int i = 0; i < customers.length; i++) {
			Customer c = customers[i];
			displayArea.append("Name: " + c.getName() + "\n");
			displayArea.append("ID: " + c.getId() + "\n");
			displayArea.append("Balance: $" + c.getBalance() + "\n\n");
        }

    }

    public static void main(String[] args) {
        new BankGUI();
    }
}
