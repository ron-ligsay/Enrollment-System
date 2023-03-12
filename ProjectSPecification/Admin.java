
package ProjectSpecification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Admin extends JFrame{
    private JButton addCourses, modifyCourses, viewStudents;
    
    Admin() {
        setTitle("Admin Portal");
            setSize(500, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);
            
        addCourses = new JButton("Add Courses"); 
        addCourses.setBounds(150, 100, 200, 60);
        
        modifyCourses = new JButton("Modify Courses"); 
        modifyCourses.setBounds(150, 200, 200, 60);
        
        viewStudents = new JButton("View Enrolled Students"); 
        viewStudents.setBounds(150, 300, 200, 60);
        
        c.add(addCourses);
        c.add(modifyCourses);
        c.add(viewStudents);
        
        setVisible(true);
        
        addCourses.addActionListener(e -> {
            JFrame nextFrame = new AddCrs();
            nextFrame.setVisible(true);
        });
        
        modifyCourses.addActionListener(e -> {
            JFrame nextFrame = new ModCrs();
            nextFrame.setVisible(true);
        });
        
        viewStudents.addActionListener(e -> {

            
            JFrame nextFrame = new ViewStd();
            nextFrame.setVisible(true);
        });
                
    }
    
    class AddCrs extends JFrame {
        private JLabel label;
        
        public AddCrs() {
            setTitle("Add Courses and Subjects Here");
            setSize(500, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);

            label = new JLabel("This is the next frame.");
            label.setBounds(150, 200, 200, 60);
            c.add(label);

            setVisible(true);
        }
    }
    
    class ModCrs extends JFrame {
        private JLabel label;
        
        public ModCrs() {
            setTitle("Modify & Delete Courses and Subjects Here");
            setSize(500, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);

            label = new JLabel("This is the next frame.");
            label.setBounds(150, 200, 200, 60);
            c.add(label);

            setVisible(true);
        }
    }
    
    class ViewStd extends JFrame {
        private JLabel label;
        
        public ViewStd() {
            setTitle("View Enrolled Students Here");
            setSize(500, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);

            label = new JLabel("This is the next frame.");
            label.setBounds(150, 200, 200, 60);
            c.add(label);

            setVisible(true);
        }
    }

        public static void main(String[] args) {
            Admin frame = new Admin();
    }
}