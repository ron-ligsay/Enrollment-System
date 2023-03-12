/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProjectSpecification;

import javax.swing.JPasswordField;


class User extends Main {
    private String userID;
    private String userType;
    private JPasswordField passwordField;
    
    public User(String userID,String userType) {
        this.userID = userID;
        this.userType = userType;
        
    }
    
    public String getUserID() {
        return userID;
    }
    
    public String getUserType() {
        return userType;
    }

}
   

