import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HighScoreBird {
    private static int BIRD_WIDTH = 34;
    private static int BIRD_HIEGHT = 24;
    private int x;
    private int y;
    private BufferedImage bird;
    private int speed = 2;
    private int SCREEN_WIDTH;
    private int jumpNum;
    private int maxFly = 150;
    private int lowFly = 20;
    
    HighScoreBird(int x, int y, int SCREEN_WIDTH){
        getBIRDImage();
        this.x = x;
        this.y = maxFly;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, BIRD_WIDTH, BIRD_HIEGHT);
    }

    public void move(){
        x+= speed;
        y+= jumpNum;
        if(x > SCREEN_WIDTH){
            x = 0 - BIRD_WIDTH * 2;
        }
        if(y == maxFly){
            jumpNum = -3;
        }
        else if(y < lowFly){
            jumpNum = 3;
        }
    }

    public void getBIRDImage(){
        try{
            bird = ImageIO.read(getClass().getResourceAsStream("HighScoreImages\\flappybird.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(bird, x, y, BIRD_WIDTH * 3, BIRD_HIEGHT * 3, null);
    }
}

