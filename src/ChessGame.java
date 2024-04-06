
import javax.swing.*;

public class ChessGame implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new ChessStartMenu());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ChessGame());
    }
}
