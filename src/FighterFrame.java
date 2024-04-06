

import javax.swing.JFrame;


public class FighterFrame extends JFrame{
    FighterFrame(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Battle Knight");
        
        FighterPanel panel = new FighterPanel();
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        panel.startGame();
    }
}