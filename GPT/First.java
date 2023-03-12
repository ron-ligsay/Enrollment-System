import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class EnrollmentSystem implements ActionListener {

    // GUI components
    private JFrame frame;
    private JLabel loginLabel, registerLabel, nameLabel, passwordLabel, confirmPasswordLabel;
    private JTextField nameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton loginButton, registerButton;
    private JComboBox<String> userTypeComboBox;
    private JTextArea messageArea;

    // Constants
    private final String[] USER_TYPES = {"Student", "Admin"};
    private final String STUDENT_RECORDS_FILE = "student_records.txt";
    private final String ADMIN_RECORDS_FILE = "admin_records.txt";
    private final String SUBJECTS_FILE = "subjects.txt";

    // Instance variables
    private String loggedInUserType;
    private String loggedInUsername;

    public EnrollmentSystem() {
        // Create GUI components
        frame = new JFrame("Enrollment System");
        frame.setLayout(new GridLayout(2, 1));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(5, 1));
        loginLabel = new JLabel("Login");
        nameLabel = new JLabel("Username:");
        nameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        userTypeComboBox = new JComboBox<>(USER_TYPES);

        loginPanel.add(loginLabel);
        loginPanel.add(nameLabel);
        loginPanel.add(nameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(userTypeComboBox);
        loginPanel.add(loginButton);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(5, 1));
        registerLabel = new JLabel("Register");
        nameLabel = new JLabel("Username:");
        nameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        registerPanel.add(registerLabel);
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(confirmPasswordField);
        registerPanel.add(registerButton);

        // Add GUI components to frame
        frame.add(loginPanel);
        frame.add(registerPanel);

        messageArea = new JTextArea(10, 40);
        frame.add(new JScrollPane(messageArea));

        // Set frame properties
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = nameField.getText();
            String password = new String(passwordField.getPassword());
            String userType = (String) userTypeComboBox.getSelectedItem();

            if (userType.equals("Student")) {
                if (login(STUDENT_RECORDS_FILE, username, password)) {
                    loggedInUserType = "Student";
                    loggedInUsername = username;
                    messageArea.setText("Login successful. Welcome, " + username + ".");
                    showStudentPortal();
                } else {
                    messageArea.setText("Login failed. Please try again.");
                }
            } else if (userType.equals("Admin")) {
                if (login(ADMIN_RECORDS_FILE, username, password)) {
                    loggedInUserType = "Admin";
                    loggedInUsername = username;
                    messageArea.setText("Login successful. Welcome, "
