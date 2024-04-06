import java.awt.EventQueue;

public class TetrisLauncher {

    public static void main(String[] args) {
        // Use EventQueue.invokeLater to ensure the GUI is updated properly on the EDT
       
            TetrisGUI tetris = new TetrisGUI();
            tetris.displayGUI();
       
    }
}
