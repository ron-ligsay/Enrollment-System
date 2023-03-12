import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EnrollmentSystem  implements ActionListener{
    private JFrame loginFrame, studentFrame, adminFrame;
    private JPanel loginPanel, studentPanel, adminPanel;
    private JLabel titleLabel, usernameLabel, passwordLabel, firstNameLabel, lastNameLabel, confirmPasswordLabel;
    private JTextField usernameField, firstNameField, lastNameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton loginButton, registerButton, logoutButton, enrollButton, createSubjectButton;
    private JTextArea subjectListArea;
    private JScrollPane subjectListScrollPane;
    private JComboBox<String> subjectComboBox;

    private User currentUser;

    private enum UserType {
        STUDENT,
        ADMIN
    }

    private class User {
        String username;
        String password;
        String firstName;
        String lastName;
        UserType userType;

        User(String username, String password, String firstName, String lastName, UserType userType) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userType = userType;
        }

        @Override
        public String toString() {
            return username + "," + password + "," + firstName + "," + lastName + "," + userType.toString();
        }
    }

    private class Subject {
        String name;

        Subject(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public EnrollmentSystem() {
        // Initialize login GUI
        loginFrame = new JFrame("Enrollment System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);

        titleLabel = new JLabel("Login to Enrollment System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    private void showStudentPortal() {
        // Initialize student portal GUI
        studentFrame = new JFrame("Student Portal");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(500, 300);

        titleLabel = new JLabel("Welcome, " + currentUser.firstName + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(this);

        subjectListArea = new JTextArea(10, 30);
        subjectListArea.setEditable(false);

        subjectListScrollPane = new JScrollPane(subjectListArea);
    
        studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(titleLabel);
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(enrollButton);
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(subjectListScrollPane);
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(logoutButton);
    
        studentFrame.add(studentPanel);
        studentFrame.setVisible(true);
    }

    private void showAdminPortal() {
        // Initialize admin portal GUI
        adminFrame = new JFrame("Admin Portal");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(500, 300);
    
        titleLabel = new JLabel("Welcome, " + currentUser.firstName + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
    
        createSubjectButton = new JButton("Create Subject");
        createSubjectButton.addActionListener(this);
    
        subjectComboBox = new JComboBox<String>();
        subjectComboBox.addItem("");
        subjectComboBox.addActionListener(this);
    
        adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        adminPanel.add(titleLabel);
        adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        adminPanel.add(createSubjectButton);
        adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        adminPanel.add(subjectComboBox);
        adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        adminPanel.add(logoutButton);
    
        adminFrame.add(adminPanel);
        adminFrame.setVisible(true);
    }
    
    private void loadSubjects() {
        // Load subjects from file
        try {
            BufferedReader br = new BufferedReader(new FileReader("subjects.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                Subject subject = new Subject(line);
                subjectComboBox.addItem(subject.toString());
                subjectListArea.append(subject.toString() + "\n");
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading subjects from file: " + e.getMessage());
        }
    }
    
    private void saveUser(User user) {
        // Save user information to file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
            bw.write(user.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }
    
    private boolean validateUser(String username, String password) {
        // Validate user credentials against file
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (username.equals(userData[0]) && password.equals(userData[1])) {
                    currentUser = new User(userData[0], userData[1], userData[2], userData[3], UserType.valueOf(userData[4]));
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
        return false;
    }
    
    private boolean validateRegistration(String username, String password, String firstName, String lastName, UserType userType) {
        // Validate registration details
        if (username.equals("") || password.equals("") || firstName.equals("") || lastName.equals("")) {
        JOptionPane.showMessageDialog(loginFrame, "Please fill out all fields.");
        return false;
        } else if (userExists(username)) {
        JOptionPane.showMessageDialog(loginFrame, "Username already exists.");
        return false;
        } else {
        saveUser(new User(username, password, firstName, lastName, userType));
        return true;
        }
        }
        
        /* private boolean userExists(String username) {
            // Check if user already exists in file
            try {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (username.equals(userData[0])) {
                        br.close();
                        return true;
                    }
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error loading users from file: " + e.getMessage());
            }
            return false;
        } */
        
        /* public static void main(String[] args) {
            new EnrollmentSystem();
        } */
        //}
        private boolean userExists(String username) {
            // Check if user already exists in file
            try {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (username.equals(userData[0])) {
                        br.close();
                        return true;
                    }
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error loading users from file: " + e.getMessage());
            }
            return false;
        }
        
        public static void main(String[] args) {
            new EnrollmentSystem();
        }
    }
        
        
        
        
        