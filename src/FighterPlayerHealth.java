

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FighterPlayerHealth{

    private static int HEALTH_WIDTH = 150;
    private static int HEALTH_HIEGHT = 130;
    private int x;
    private int y;
    public int id;
    private BufferedImage playerOneHealth[] = new BufferedImage[4];
    private BufferedImage playerTwoHealth[] = new BufferedImage[4];
    private int SCREEN_WIDTH;
    private int SCREEN_HIEGHT;
    public int playerHp;
    boolean wins = true;

    FighterPlayerHealth(int SCREEN_WIDTH, int SCREEN_HIEGHT, int id, int x, int y){
        getHealthImage();
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HIEGHT = SCREEN_HIEGHT;
        this.x = x;
        this.y = y;
        this.id = id;
        setHealth();
    }

    public void setHealth(){
        playerHp = 3;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, HEALTH_WIDTH, HEALTH_HIEGHT);
    }

    public void checkHealth(){
        if(playerHp == 0){
            wins = false;
        }
    }
    
    public void getHealthImage(){
        try{
            playerOneHealth[0] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_1_full.png"));
            playerOneHealth[1] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_1_two.png"));
            playerOneHealth[2] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_1_one.png"));
            playerOneHealth[3] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_1_defeat.png"));

            playerTwoHealth[0] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_2_full.png"));
            playerTwoHealth[1] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_2_two.png"));
            playerTwoHealth[2] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_2_one.png"));
            playerTwoHealth[3] = ImageIO.read(getClass().getResourceAsStream("FighterImagesHP/player_2_defeat.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2, Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Droid Sans", Font.PLAIN, 64));
        if(id == 1){
            if(playerHp == 3){
                g2.drawImage(playerOneHealth[0], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp == 2){
                g2.drawImage(playerOneHealth[1], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp == 1){
                g2.drawImage(playerOneHealth[2], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp <= 0){
                g2.drawImage(playerOneHealth[3], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
                g.setColor(Color.blue);
                g.drawString("BLUE WINS", SCREEN_WIDTH / 2 - 160, SCREEN_HIEGHT / 2);
            }
        }
        else if(id == 2){
            if(playerHp == 3){
                g2.drawImage(playerTwoHealth[0], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp == 2){
                g2.drawImage(playerTwoHealth[1], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp == 1){
                g2.drawImage(playerTwoHealth[2], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
            }
            if(playerHp <= 0){
                g2.drawImage(playerTwoHealth[3], x, y, HEALTH_WIDTH, HEALTH_HIEGHT, null);
                g.setColor(Color.red);
                g.drawString("RED WINS", SCREEN_WIDTH / 2 - 160, SCREEN_HIEGHT / 2);
            }
        }
        
    }
}
