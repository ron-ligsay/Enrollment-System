package ProjectSpecification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    class Students extends JFrame implements ActionListener{
        private JLabel label1, label2;
        private JTextArea ta1, ta2;
        private JButton enroll;

        Students(){
            setTitle("Student Portal");
            setSize(400, 235);
            setLocationRelativeTo(null); 
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);

            label1 = new JLabel("Program:");
            label1.setBounds(20, 20, 100, 20);

            label2 = new JLabel("Courses:");
            label2.setBounds(20, 70, 100, 20);


            ta1 = new JTextArea("");
            ta1.setBounds(90, 20, 260, 40);

            ta2 = new JTextArea("");
            ta2.setBounds(90, 70, 260, 40);


            enroll = new JButton("Enroll");
            enroll.setBounds(150, 130, 90, 40);

            c.add(label1);
            c.add(label2);
            c.add(ta1);
            c.add(ta2);
            c.add(enroll);

            setVisible(true);


            enroll.addActionListener(e -> {
            JFrame nextFrame = new Enrolled();
            nextFrame.setVisible(true);
            });

        }

        class Enrolled extends JFrame {
            private JComboBox cs, ts;
            private JLabel label1, label2;

            public Enrolled() {
                setTitle("Enrollment");
                setSize(400, 350);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                Container c = getContentPane();
                c.setLayout(null);

                label1 = new JLabel("Choose your preferred Program: ");
                label1.setBounds(40, 30, 200, 20);

                label2 = new JLabel("Choose your preferred Courses: ");
                label2.setBounds(40, 130, 200, 20);


                String compsci[] = {"BS - Computer Science Major in Machine Learning","BS - Computer Science Major in Digital Forensics"};
                cs = new JComboBox(compsci);
                cs.setBounds(40, 60, 290, 30);

                String termSubj[] = {"Fundamentals of Programming", "Introduction to Computing", "Understanding the Self"};
                ts = new JComboBox(termSubj);
                ts.setBounds(40, 160, 290, 30);


                enroll = new JButton("Enroll");
                enroll.setBounds(150, 230, 90, 40);

                c.add(label1);
                c.add(label2);
                c.add(cs);
                c.add(ts);
                c.add(enroll);

                setVisible(true);

                enroll.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "You are now officialy enrolled!", "FINISHED", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
        }

    }


    class StudentReg {
        public static void main(String[] args) {
            Students frame = new Students();
    }
}