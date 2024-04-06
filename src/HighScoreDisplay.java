import javax.swing.*;

public class HighScoreDisplay extends JFrame{
    HighScoreDisplay(){
        this.setResizable(false);
        this.setTitle("Aracder High Score");

        HighScorePanel panel = new HighScorePanel();
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
