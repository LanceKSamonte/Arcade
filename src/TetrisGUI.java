import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class TetrisGUI {
    private JFrame frame;
    private Tetris_GamePanel gp; // gp is now an instance variable

    public TetrisGUI() {
        // Initialize the JFrame here but don't display it yet
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        gp = new Tetris_GamePanel(); // Assign to the instance variable
        frame.add(gp);
        frame.pack(); // This will set the frame size based on the preferred size of gp

        frame.setLocationRelativeTo(null);
        // Add a window listener to stop the music when the window is closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Stop the music here
            	gp.stopGame();
                Tetris_GamePanel.music.stop();
            }
        });
    }

    public void displayGUI() {
        // This method makes the GUI visible to the user.
        frame.setVisible(true);
        gp.startGame(); // Access the instance variable here
    }
}
