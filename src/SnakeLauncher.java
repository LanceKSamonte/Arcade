import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SnakeLauncher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                SnakeGUI snakeGUI = new SnakeGUI();
                snakeGUI.displayGUI();
            } catch (UnsupportedAudioFileException | LineUnavailableException e) {
                e.printStackTrace();
            }
        });
    }
}
