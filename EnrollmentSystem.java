import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class EnrollmentSystem implements ActionListener {
    private Map<String, List<String>> enrollments = new HashMap<>();

    private JFrame loginFrame, studentFrame, adminFrame;
    private JPanel loginPanel, studentPanel, adminPanel;
    private JLabel titleLabel, usernameLabel, passwordLabel, firstNameLabel, lastNameLabel, userTypeLabel;
    private JTextField usernameField, firstNameField, lastNameField;
    private JPasswordField passwordField;
    private JComboBox<UserType> userTypeComboBox;
    private JButton loginButton, registerButton, enrollButton, createSubjectButton, logoutButton;
    private JComboBox<String> subjectComboBox;
    private JTextArea subjectListArea;
    private JScrollPane subjectListScrollPane;
    private User currentUser;

    public EnrollmentSystem() {
        // Initialize login GUI
        loginFrame = new JFrame("Enrollment System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 300);

        titleLabel = new JLabel("Welcome to the Enrollment System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

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
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(registerButton);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateUser(username, password)) {
                if (currentUser.userType == UserType.STUDENT) {
                    showStudentPortal();
                } else if (currentUser.userType == UserType.ADMIN) {
                    showAdminPortal();
                }
                loginFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
            }
        } else if (e.getSource() == registerButton) {
            // Initialize registration GUI
            JFrame registerFrame = new JFrame("Registration");
            registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            registerFrame.setSize(500, 300);

            titleLabel = new JLabel("Registration");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            usernameLabel = new JLabel("Username:");
            passwordLabel = new JLabel("Password:");
            firstNameLabel = new JLabel("First Name:");
            lastNameLabel = new JLabel("Last Name:");
            userTypeLabel = new JLabel("User Type:");

            usernameField = new JTextField();
            passwordField = new JPasswordField();
            firstNameField = new JTextField();
            lastNameField = new JTextField();
            userTypeComboBox = new JComboBox<UserType>(UserType.values());
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    UserType userType = (UserType) userTypeComboBox.getSelectedItem();
    
                    if (registerUser(username, password, firstName, lastName, userType)) {
                        JOptionPane.showMessageDialog(registerFrame, "Registration successful.");
                        registerFrame.dispose();
                    }
                }
            });
    
            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(titleLabel);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(usernameLabel);
            registerPanel.add(usernameField);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(passwordLabel);
            registerPanel.add(passwordField);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(firstNameLabel);
            registerPanel.add(firstNameField);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(lastNameLabel);
            registerPanel.add(lastNameField);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(userTypeLabel);
            registerPanel.add(userTypeComboBox);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            registerPanel.add(registerButton);
    
            registerFrame.add(registerPanel);
            registerFrame.setVisible(true);
        } else if (e.getSource() == enrollButton) {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            enrollSubject(selectedSubject);
        } else if (e.getSource() == createSubjectButton) {
            String subjectName = JOptionPane.showInputDialog(adminFrame, "Enter subject name:");
            createSubject(subjectName);
        } else if (e.getSource() == logoutButton) {
            currentUser = null;
            showLogin();
        }
    }
    
    private boolean validateUser(String username, String password) {
        // Check if user exists and password matches
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
    
    private boolean registerUser(String username, String password, String firstName, String lastName, UserType userType) {
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
    
    private boolean userExists(String username) {
        // Check if user already exists in file
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData
                //////////////
                String[] fields = line.split(",");
            if (fields[0].equals(username)) {
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

private void saveUser(User user) {
    // Save new user to file
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
        bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getUserType().toString());
        bw.newLine();
        bw.close();
    } catch (IOException e) {
        System.out.println("Error saving user to file: " + e.getMessage());
    }
}

private void createSubject(String subjectName) {
    // Save new subject to file
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("subjects.txt", true));
        bw.write(subjectName);
        bw.newLine();
        bw.close();
        JOptionPane.showMessageDialog(adminFrame, "Subject created.");
    } catch (IOException e) {
        System.out.println("Error saving subject to file: " + e.getMessage());
    }
}

/* private void enrollSubject(String subjectName) {
    // Save enrolled subject to file
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("enrollments.txt", true));
        bw.write(currentUser.getUsername() + "," + subjectName);
        bw.newLine();
        bw.close();
        JOptionPane.showMessageDialog(studentFrame, "Enrollment successful.");
    } catch (IOException e) {
        System.out.println("Error saving enrollment to file: " + e.getMessage());
    }
} */
private void enrollSubject(String subjectName) {
    // Add subject to student's enrollments
    List<String> enrollmentsList = enrollments.get(currentUser.getUsername());
    if (enrollmentsList == null) {
        enrollmentsList = new ArrayList<>();
        enrollments.put(currentUser.getUsername(), enrollmentsList);
    }
    enrollmentsList.add(subjectName);

    JOptionPane.showMessageDialog(studentFrame, "Enrollment successful.");
}
private void saveEnrollments() {
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("enrollments.txt"));
        for (Map.Entry<String, List<String>> entry : enrollments.entrySet()) {
            bw.write(entry.getKey() + ",");
            bw.write(String.join(",", entry.getValue()));
            bw.newLine();
        }
        bw.close();
    } catch (IOException e) {
        System.out.println("Error saving enrollments to file: " + e.getMessage());
    }
}

private void loadEnrollments() {
    try {
        BufferedReader br = new BufferedReader(new FileReader("enrollments.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            String username = fields[0];
            List<String> enrollmentsList = new ArrayList<>();
            for (int i = 1; i < fields.length; i++) {
                enrollmentsList.add(fields[i]);
            }
            enrollments.put(username, enrollmentsList);
        }
        br.close();
    } catch (IOException e) {
        System.out.println("Error loading enrollments from file: " + e.getMessage());
    }
}


/* private void showStudentPortal() {
    // Load subjects and display student portal
    try {
        BufferedReader br = new BufferedReader(new FileReader("subjects.txt"));
        List<String> subjects = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            subjects.add(line);
        }
        br.close();

        subjectComboBox = new JComboBox(subjects.toArray());
        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(this);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(new JLabel("Welcome, " + currentUser.getFirstName() + "!"));
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(new JLabel("Select subject to enroll:"));
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(subjectComboBox);
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(enrollButton);
        studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        studentPanel.add(logoutButton);

        studentFrame = new JFrame("Student Portal");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setContentPane(studentPanel);
        studentFrame.pack();
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setVisible(true);
    } catch (IOException e) {
        System.out.println("Error loading subjects from file: " + e.getMessage());
    }
} */
private void showStudentPortal() {
    // Load student's enrollments
    List<String> enrollmentsList = enrollments.get(currentUser.getUsername());
    if (enrollmentsList == null) {
        enrollmentsList = new ArrayList<>();
    }

    // Create student portal GUI
    JPanel studentPanel = new JPanel();
    studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
    studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    studentPanel.add(new JLabel("Welcome, " + currentUser.getFirstName() + "!"));
    studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    studentPanel.add(new JLabel("Enrolled Subjects:    "
    JList<String> enrollmentList = new JList<>(enrollmentsList.toArray(new String[enrollmentsList.size()]));
    JScrollPane enrollmentScroll = new JScrollPane(enrollmentList);
    enrollmentScroll.setPreferredSize(new Dimension(200, 100));
    studentPanel.add(enrollmentScroll);
    studentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    
    // Create subject selection GUI
    JPanel subjectPanel = new JPanel(new GridLayout(0, 1));
    subjectPanel.setBorder(BorderFactory.createTitledBorder("Available Subjects"));
    
    for (Subject subject : subjects) {
        JButton button = new JButton(subject.getName());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enrollmentsList.contains(subject.getName())) {
                    JOptionPane.showMessageDialog(studentFrame, "You are already enrolled in " + subject.getName() + ".");
                } else {
                    enrollSubject(subject.getName());
                    enrollmentsList.add(subject.getName());
                    enrollmentList.setListData(enrollmentsList.toArray(new String[enrollmentsList.size()]));
                    saveEnrollments();
                }
            }
        });
        subjectPanel.add(button);
    }
    
    JScrollPane subjectScroll = new JScrollPane(subjectPanel);
    subjectScroll.setPreferredSize(new Dimension(200, 200));
    
    // Create student portal frame
    studentFrame = new JFrame("Student Portal");
    studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    studentFrame.setResizable(false);
    studentFrame.setContentPane(studentPanel);
    studentFrame.add(subjectScroll, BorderLayout.EAST);
    studentFrame.pack();
    studentFrame.setLocationRelativeTo(null);
    studentFrame.setVisible(true);
}

/* private void showAdminPortal() {
    // Display admin portal
    JButton createSubjectButton = new JButton("Create Subject");
    createSubjectButton.addActionListener(this);
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(this);

    JPanel adminPanel = new JPanel();
    adminPanel.setLayout(new BoxLayout(admin

           
    Panel, BoxLayout.Y_AXIS));
    adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    adminPanel.add(new JLabel("Welcome, " + currentUser.getFirstName() + "!"));
    adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    adminPanel.add(createSubjectButton);
    adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    adminPanel.add(logoutButton);
    
    adminFrame = new JFrame("Admin Portal");
    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    adminFrame.setContentPane(adminPanel);
    adminFrame.pack();
    adminFrame.setLocationRelativeTo(null);
    adminFrame.setVisible(true);
} */

private void showAdminPortal() {
    // Create admin portal GUI
    JPanel adminPanel = new JPanel();
    adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
    adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    adminPanel.add(new JLabel("Welcome, " + currentUser.getFirstName() + "!"));
    adminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    adminPanel.add(new JLabel("Create New Subject:"));
    JTextField subjectNameField = new JTextField(20);
adminPanel.add(subjectNameField);

JButton createButton = new JButton("Create");
createButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String subjectName = subjectNameField.getText();
        if (subjectName.isEmpty()) {
            JOptionPane.showMessageDialog(adminFrame, "Please enter a subject name.");
        } else if (subjectExists(subjectName)) {
            JOptionPane.showMessageDialog(adminFrame, "Subject already exists.");
        } else {
            createSubject(subjectName);
            saveSubjects();
            JOptionPane.showMessageDialog(adminFrame, "Subject created successfully.");
        }
    }
});
adminPanel.add(createButton);

// Create admin portal frame
adminFrame = new JFrame("Admin Portal");
adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
adminFrame.setResizable(false);
adminFrame.setContentPane(adminPanel);
adminFrame.pack();
adminFrame.setLocationRelativeTo(null);
adminFrame.setVisible(true);
}

private void loginUser(String username, String password) {
    // Check if user exists and password is correct
    if (checkUserExists(username) && checkUserPassword(username, password)) {
        // Load user from file
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(username)) {
                    currentUser = new User(fields[0], fields[1], fields[2], fields[3], UserType.valueOf(fields[4]));
                    br.close();
                    if (currentUser.getUserType() == UserType.STUDENT) {
                        showStudentPortal();
                    } else if (currentUser.getUserType() == UserType.ADMIN) {
                        showAdminPortal();
                    }
                    return;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
    }
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == registerButton) {
        // Show register form
        showRegisterForm();
    } else if (e.getSource() == loginButton) {
        // Login user
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        loginUser(username, password);
    } else if (e.getSource() == registerSaveButton) {
        // Save new user
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String firstName = registerFirstNameField.getText();
        String lastName = registerLastNameField.getText();
        UserType userType = UserType.valueOf(registerUserTypeComboBox.getSelectedItem().toString());
        User newUser = new User(username, password, firstName, lastName, userType);
        saveUser(newUser);
        JOptionPane.showMessageDialog(registerFrame, "Registration successful.");
        registerFrame.dispose();
    } else if (e.getSource() == createSubjectButton) {
        // Show create subject form
        showCreateSubjectForm();
    } else if (e.getSource() == createSubjectSaveButton) {
        // Create new subject
        String subjectName = createSubjectNameField.getText();
        createSubject(subjectName);
        createSubjectFrame.dispose();
    } else if (e.getSource() == enrollButton) {
        // Enroll student to selected subject
        String subjectName = (String) subjectComboBox.getSelectedItem();
        enrollSubject(subjectName);
    } else if (e.getSource() == logoutButton) {
        // Logout user and go back to login form
        currentUser = null;
        studentFrame.dispose();
        adminFrame.dispose();
        showLoginForm();
    }
}

public static void main(String[] args) {
    EnrollmentSystem enrollmentSystem = new EnrollmentSystem();
    enrollmentSystem.showLoginForm();
}
