

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FighterCloud{
    private static int CLOUD_WIDTH = 100;
    private static int CLOUD_HIEGHT = 64;
    private int x;
    private int y;
    private BufferedImage cloud;
    private int speed = 1;
    private int SCREEN_WIDTH;
    
    FighterCloud(int x, int y, int SCREEN_WIDTH){
        getCloudImage();
        this.x = x;
        this.y = y;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
    }

    public void getCloudImage(){
        try{
            cloud = ImageIO.read(getClass().getResourceAsStream("FighterImagesField/cloud.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, CLOUD_WIDTH, CLOUD_HIEGHT);
    }

    public void move(){
        x+= speed;   
        if(x > SCREEN_WIDTH){
            x = 0 - CLOUD_WIDTH * 2;
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(cloud, x, y, CLOUD_WIDTH * 2, CLOUD_HIEGHT * 2, null);
    }
}