package ProjectSpecification;

import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


 public class void main{
    
    JLabel login, userID, passwordLbl, accType;
    JTextField idText;
    JPasswordField passwordField;
    JButton loginBtn, registerBtn;
    JRadioButton studentBtn, adminBtn;
    ButtonGroup accGrp = new ButtonGroup();
    //private Students Students;
  
    public main(){
        JFrame F = new JFrame();
        
        F.setTitle("Enrollment System");
        
        login =  new JLabel("Login");
        login.setBounds(145,5,100,80);
        login.setFont(new Font("Sanssserif", Font.BOLD, 30));
        
        userID = new JLabel("User ID");
        userID.setBounds(60,95,100,24);
        userID.setFont(new Font("Sansserif", Font.PLAIN, 12));
        
        idText = new JTextField();
        idText.setBounds(60,120,260,25);
    
        passwordLbl = new JLabel("Password");
        passwordLbl.setBounds(60,152,100,24);
        passwordLbl.setFont(new Font("Sansserif", Font.PLAIN, 12));
        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(60,175,260,25);
        
        accType = new JLabel("User Type:");
        accType.setBounds(80,220,80,48);
        
        studentBtn = new JRadioButton("Student");
        studentBtn.setBounds(155,210,80,48);
        studentBtn.setFont(new Font("Sansserif", Font.PLAIN, 12));
        
        adminBtn = new JRadioButton("Admin");
        adminBtn.setBounds(155,240,80,48);
        adminBtn.setFont(new Font("Sansserif",Font.PLAIN, 12));
        
        accGrp.add(studentBtn);
        accGrp.add(adminBtn);
        
        loginBtn = new JButton("Login");
        loginBtn.setForeground(Color.white);
        loginBtn.setBackground(new Color(0,61,167));
        loginBtn.setBounds(60, 300, 260, 25);
        

        F.add(login);
        F.add(userID);
        F.add(idText);
        F.add(passwordLbl);
        F.add(passwordField);
        F.add(accType);
        F.add(studentBtn);
        F.add(adminBtn);
        F.add(loginBtn);
        F.add(registerBtn);   
        
        F.setLayout(null);
        F.setSize(395,507);
        F.setVisible(true);
        F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        loginBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
    
        /* if (e.getSource() == loginBtn) {
        String userID = idText.getText().trim();
        String pass = new String(passwordField.getPassword());
        if (studentBtn.isSelected()) {
            if (StudentClass.login(userID, pass)) {
                F.dispose();
               new Students();
            }
        } else if (adminBtn.isSelected()) {
            if (userID.equals("admin") && pass.equals("admin")) {
                F.dispose();
                new Admin();
            } else {
                JOptionPane.showMessageDialog(F, "Invalid login credentials or not an admin account");
            }
        }
    } else if (e.getSource() == registerBtn) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
            String userID = JOptionPane.showInputDialog(F, "Enter a user ID:");
            String password = JOptionPane.showInputDialog(F, "Enter a password:");
            String accountType = JOptionPane.showInputDialog(F, "Enter an account type (student or admin):");
            writer.write(userID + "," + password + "," + accountType + "\n");
            writer.close();
            JOptionPane.showMessageDialog(F, "User registered successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } */
}
    });
         
       /* public void actionPerformed(ActionEvent ae) {
        List<User> userList = readUsersFromFile();
        String enteredUserID = idText.getText();
        String enteredPassword = passwordField.getText();
        boolean foundUser = false;
        for (User user : userList) {
            if (user.getUserID().equals(enteredUserID) && user.getPassword().equals(enteredPassword)) {
                foundUser = true;
                if (user.getUserType().equals("Student")) {
                    F.dispose();
                    new Students();
                } else if (user.getUserType().equals("Admin")) {
                    F.dispose();
                    new Admin();
                }
                break;
            }
        }
        if (!foundUser) {
            JOptionPane.showMessageDialog(null, "Invalid user ID or password.");
        }
    }
})*/;
        
        registerBtn = new JButton("Register");
        registerBtn.setForeground(Color.white);
        registerBtn.setBackground(new Color(92,184,92));
        registerBtn.setBounds(60, 330, 260, 25);
        registerBtn.setBorder(BorderFactory.createLineBorder(new Color(76,174,76)));

           registerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                F.dispose();
                //new Registration();
            };
        }); 
     
        
        F.add(login);
        F.add(userID);
        F.add(idText);
        F.add(passwordLbl);
        F.add(passwordField);
        F.add(accType);
        F.add(studentBtn);
        F.add(adminBtn);
        F.add(loginBtn);
        F.add(registerBtn);   
        
        F.setLayout(null);
        F.setSize(395,507);
        F.setVisible(true);
        F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
   
    
    public static void Main(String[] args){
        new main();
        //new StudentClass();
    }
}
                  
