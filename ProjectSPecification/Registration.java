
package ProjectSpecification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

class Registration extends Main {
    
      JFrame F = new JFrame();
      
    Registration(){
    
        
    registerBtn.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent ae) {
                // Get the registration data from the text fields
                String userId = idText.getText();
                String password = new String(passwordField.getPassword());
                String userType = studentBtn.isSelected() ? "student" : "admin";
             
                // Write the data to the file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
                    writer.write(userId + "," + password + "," + userType + "\n");
                    writer.close();
                    
                    System.out.println("Registration successful!");
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }
          });
    
        F.setLayout(null);
        F.setSize(500,420);
        F.setVisible(true);
       
    }
}



    

