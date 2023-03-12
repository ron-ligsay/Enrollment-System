import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class EnrollmentSystem implements ActionListener {

    private static final String USERS_FILE = "users.txt";
    private static final String SUBJECTS_FILE = "subjects.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";

    private Map<String, User> users;
    private Map<String, Subject> subjects;
    private Map<String, Set<String>> enrollments;

    private JFrame loginFrame;
    private JFrame registerFrame;
    private JFrame studentFrame;
    private JFrame adminFrame;

    private User currentUser;

    public static void main(String[] args) {
        EnrollmentSystem enrollmentSystem = new EnrollmentSystem();
        enrollmentSystem.loadUsers();
        enrollmentSystem.loadSubjects();
        enrollmentSystem.loadEnrollments();
        enrollmentSystem.showLoginForm();
    }

    private void loadUsers() {
        users = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(USERS_FILE))) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String email = tokens[0];
                String password = tokens[1];
                String firstName = tokens[2];
                String lastName = tokens[3];
                String userType = tokens[4];
                users.put(email, new User(email, password, firstName, lastName, userType));
            }
        } catch (FileNotFoundException e) {
            // If file not found, create an empty file
            saveUsers();
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new File(USERS_FILE))) {
            for (User user : users.values()) {
                writer.println(user.getEmail() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getUserType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadSubjects() {
        subjects = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(SUBJECTS_FILE))) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String name = tokens[0];
                int units = Integer.parseInt(tokens[1]);
                subjects.put(name, new Subject(name, units));
            }
        } catch (FileNotFoundException e) {
            // If file not found, create an empty file
            saveSubjects();
        }
    }

    private void saveSubjects() {
        try (PrintWriter writer = new PrintWriter(new File(SUBJECTS_FILE))) {
            for (Subject subject : subjects.values()) {
                writer.println(subject.getName() + "," + subject.getUnits());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadEnrollments() {
        enrollments = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(ENROLLMENTS_FILE))) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String email = tokens[0];
                String subject = tokens[1];
                enrollments.computeIfAbsent(email, k -> new HashSet<>()).add(subject);
            }
        } catch (FileNotFoundException e) {
            // If file not found, create an empty file
            saveEnrollments();
        }
    }

    private void saveEnrollments() {
        try (PrintWriter writer = new PrintWriter(new File(ENROLLMENTS_FILE))) {
            for (Map.Entry<String, Set<String>> entry : enrollments.entrySet()) {
                String email = entry.getKey();
                Set<String> subjects = entry.getValue();
                for (String subject : subjects) {
                    writer.println(email + "," + subject);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void showLoginForm() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel(new GridLayout(3, 2));
    
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);
    
        JTextField emailField = new JTextField();
        panel.add(emailField);
    
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
    
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);
    
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);
    
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);
    
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
    
    private void showRegisterForm() {
        registerFrame = new JFrame("Register");
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(300, 200);
        registerFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel(new GridLayout(6, 2));
    
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);
    
        JTextField emailField = new JTextField();
        panel.add(emailField);
    
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
    
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);
    
        JLabel firstNameLabel = new JLabel("First Name:");
        panel.add(firstNameLabel);
    
        JTextField firstNameField = new JTextField();
        panel.add(firstNameField);
    
        JLabel lastNameLabel = new JLabel("Last Name:");
        panel.add(lastNameLabel);
    
        JTextField lastNameField = new JTextField();
        panel.add(lastNameField);
    
        JLabel userTypeLabel = new JLabel("User Type:");
        panel.add(userTypeLabel);
    
        JComboBox<String> userTypeCombo = new JComboBox<>(new String[]{"Student", "Admin"});
        panel.add(userTypeCombo);
    
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);
    
        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }
    
    private void showStudentPortal() {
        studentFrame = new JFrame("Student Portal");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(300, 200);
        studentFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel(new GridLayout(2, 1));
    
        JLabel titleLabel = new JLabel("Enroll to Subjects:");
        panel.add(titleLabel);
    
        JPanel subjectPanel = new JPanel(new GridLayout(subjects.size(), 1));
        for (Subject subject : subjects.values()) {
            JCheckBox checkBox = new JCheckBox(subject.getName() + " (" + subject.getUnits() + " units)");
            checkBox.setActionCommand(subject.getName());
            subjectPanel.add(checkBox);
        }
        panel.add(subjectPanel);
    
        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(this);
        panel.add(enrollButton);
    
        studentFrame.add(panel);
        studentFrame.setVisible(true);
    }
    
    private void showAdminPortal() {
        adminFrame = new JFrame("Admin Portal");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(300, 200);
        adminFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel(new GridLayout(3, 1));
    
        JLabel titleLabel = new JLabel("Create Subject:");
        panel.add(titleLabel);
    
        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);
    
        JLabel unitsLabel = new JLabel("Units:");
        panel.add(unitsLabel);
    
        JSpinner unitsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        panel.add(unitsSpinner);
    
        JButton createButton = new JButton("Create");
        createButton.addActionListener(this);
        panel.add(createButton);
    
        adminFrame.add(panel);
        adminFrame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Login":
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                User user = users.get(email);
                if (user != null && user.getPassword().equals(password)) {
                    if (user instanceof Student) {
                        showStudentPortal();
                    } else if (user instanceof Admin) {
                        showAdminPortal();
                    }
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid email or password");
                }
                break;
            case "Register":
                showRegisterForm();
                break;
            case "Enroll":
                Set<String> selectedSubjects = new HashSet<>();
                for (Component component : studentFrame.getContentPane().getComponents()) {
                    if (component instanceof JPanel) {
                        for (Component checkBox : ((JPanel) component).getComponents()) {
                            if (checkBox instanceof JCheckBox && ((JCheckBox) checkBox).isSelected()) {
                                selectedSubjects.add(((JCheckBox) checkBox).getActionCommand());
                            }
                        }
                    }
                }
                if (!selectedSubjects.isEmpty()) {
                    enrolledSubjects.put(currentUser.getEmail(), selectedSubjects);
                    saveEnrolledSubjects();
                    JOptionPane.showMessageDialog(studentFrame, "Subjects enrolled successfully");
                    studentFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(studentFrame, "Please select at least one subject to enroll");
                }
                break;
            case "Create":
                String name = nameField.getText();
                int units = (int) unitsSpinner.getValue();
                Subject subject = new Subject(name, units);
                subjects.put(name, subject);
                saveSubjects();
                JOptionPane.showMessageDialog(adminFrame, "Subject created successfully");
                nameField.setText("");
                unitsSpinner.setValue(1);
                break;
            case "Register User":
                String registerEmail = registerEmailField.getText();
                String registerPassword = new String(registerPasswordField.getPassword());
                String registerFirstName = registerFirstNameField.getText();
                String registerLastName = registerLastNameField.getText();
                UserType userType = UserType.valueOf((String) registerUserTypeCombo.getSelectedItem());
                User newUser = userType == UserType.STUDENT ?
                        new Student(registerEmail, registerPassword, registerFirstName, registerLastName) :
                        new Admin(registerEmail, registerPassword, registerFirstName, registerLastName);
                users.put(registerEmail, newUser);
                saveUsers();
                JOptionPane.showMessageDialog(registerFrame, "User registered successfully");
                registerFrame.dispose();
                break;
            default:
                break;
        }
    }
    
    public static void main(String[] args) {
        EnrollmentSystem enrollmentSystem = new EnrollmentSystem();
        enrollmentSystem.loadUsers();
        enrollmentSystem.loadSubjects();
        enrollmentSystem.loadEnrolledSubjects();
        enrollmentSystem.showLoginForm();
    }
}    
    
               
