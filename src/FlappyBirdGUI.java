import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FlappyBirdGUI {
    private JFrame frame;
    private int boardWidth = 360;
    private int boardHeight = 640;

    public FlappyBirdGUI() {
        // Initialize the JFrame here but don't display it yet
        frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
     // Set the icon image from the resources folder
        ImageIcon icon = new ImageIcon(getClass().getResource("games/flappybirdIcon.png"));
        frame.setIconImage(icon.getImage());
        

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        
        frame.pack(); // Adjusts the frame to the preferred size of its components
        flappyBird.requestFocus(); // Requests the input focus for the FlappyBird component
    }

    public void displayGUI() {
        // This method makes the GUI visible to the user.
        frame.setVisible(true);
    }
}
