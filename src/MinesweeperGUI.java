import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MinesweeperGUI {
    private static JFrame frame;
    private JLabel statusbar;
    private int boardWidth = 250; // You might need to adjust these dimensions
    private int boardHeight = 300; // based on the size of your Minesweeper board

    public MinesweeperGUI() {
        // Initialize the JFrame here but don't display it yet
        frame = new JFrame("Minesweeper");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        
     // Set the icon image from the resources folder
        try {
            InputStream imgStream = getClass().getResourceAsStream("games/minesweeperIcon.png");
            BufferedImage myImg = ImageIO.read(imgStream);
            frame.setIconImage(myImg);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Icon could not be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        statusbar = new JLabel("");
        frame.add(statusbar, BorderLayout.SOUTH);
        
        MinesweeperBoard board = new MinesweeperBoard(statusbar);
        frame.add(board);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.pack(); // Adjusts the frame to the preferred size of its components
        board.requestFocus(); // Requests the input focus for the Minesweeper board component
    }

    public static void displayGUI() {
        // This method makes the GUI visible to the user.
        frame.setVisible(true);
    }

    
}
