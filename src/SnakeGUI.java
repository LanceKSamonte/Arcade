import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class SnakeGUI {
    private JFrame frame;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    

    public SnakeGUI() throws UnsupportedAudioFileException, LineUnavailableException {
        frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);
        final SnakeGamePanel game = new SnakeGamePanel(WIDTH, HEIGHT);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); // Adjust frame to the preferred size of its components

        // Start the game once the GUI is displayed
        game.startGame();
    }

    public void displayGUI() {
        frame.setVisible(true);
    }
}
