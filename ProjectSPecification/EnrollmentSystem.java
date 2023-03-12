package ProjectSpecification;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class EnrollmentSystem extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JRadioButton studentRadioButton;
    private JRadioButton adminRadioButton;

    private StudentFrame studentFrame;

    public EnrollmentSystem() {
        super("ENROLLMENT SYSTEM");

        JLabel userIdLabel = new JLabel("USER ID:");
        JLabel passwordLabel = new JLabel("PASSWORD:");
        JLabel accountTypeLabel = new JLabel("ACCOUNT TYPE:");

        userIdField = new JTextField(20);
        passwordField = new JPasswordField(20);
        studentRadioButton = new JRadioButton("Student");
        adminRadioButton = new JRadioButton("Admin");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(studentRadioButton);
        accountTypeGroup.add(adminRadioButton);
        JButton loginButton = new JButton("Log in");
        JButton registerButton = new JButton("Register");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(accountTypeLabel);
        panel.add(studentRadioButton);
        panel.add(adminRadioButton);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Log in")) {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());
            String accountType = studentRadioButton.isSelected() ? "Student" : "Admin";

            try {
                BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"));
                String line = null;
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[0].equals(userId) && tokens[1].equals(password) && tokens[2].equals(accountType)) {
                        JOptionPane.showMessageDialog(this, "Login successful!");
                        found = true;
                        if (accountType.equals("Student")) {
                            studentFrame = new StudentFrame();
                            this.setVisible(false);
                            break;
                        }
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Invalid user ID or password");
                }
                br.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else if (e.getActionCommand().equals("Register")) {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());
            String accountType = studentRadioButton.isSelected() ? "Student" : "Admin";

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("enrollment.txt", true));
                bw.write(userId + "," + password + "," + accountType);
                bw.newLine();
                bw.close();
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new EnrollmentSystem();
    }
}