import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

public class Tetris_GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    Tetris_GameManager gm;
    public static Tetris_Sound music; 
    public static Tetris_Sound soundEffect;
    
    public Tetris_GamePanel(){

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        
        this.addKeyListener(new Tetris_KeyHandler());
        this.setFocusable(true);

        gm = new Tetris_GameManager();
        music = new Tetris_Sound();
        soundEffect = new Tetris_Sound();
    }

    public void startGame(){
        
        gameThread = new Thread(this);
        gameThread.start();

        music.play(0, true);
        music.loop();
    }
    public void stopGame() {
        if (gameThread != null) {
            gameThread.interrupt(); // Attempt to stop the game loop
            gameThread = null;
        }
        // Stop any sound effects that might be playing
        // Ensure Tetris_Sound class has a static reference to soundEffect similar to music, or manage it appropriately
    }
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
            lastTime = currentTime;
        }
        
    }

    public void update(){
        if(Tetris_KeyHandler.pausePressed == false && Tetris_GameManager.gameOver == false){
            gm.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        gm.draw(g2);

    }

}
