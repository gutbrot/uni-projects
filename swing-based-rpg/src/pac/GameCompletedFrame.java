package pac;

import javax.swing.*;
import java.awt.*;

public class GameCompletedFrame extends JFrame {

    public GameCompletedFrame() {
        setTitle("Congratulations!");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Congratulations! You completed the game!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        add(label);

        setVisible(true);
    }
}
