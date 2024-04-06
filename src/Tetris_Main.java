import javax.swing.*;
public class Tetris_Main{
    public static void main(String[] args){
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Tetris_GamePanel gp = new Tetris_GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
   
        gp.startGame();
    }
}