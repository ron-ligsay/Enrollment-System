import javax.swing.*;

public class SimpleGUI {
   public static void main(String[] args) {
      JFrame frame = new JFrame("My Simple GUI");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JLabel label = new JLabel("Welcome to my GUI!");
      frame.getContentPane().add(label);
      frame.pack();
      frame.setVisible(true);
   }
}