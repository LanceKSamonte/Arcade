import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HighScoreKnight {
    private static int KNIGHT_WIDTH = 64;
    private int x;
    private int y;
    private int id;
    private BufferedImage knight1, knight2;
    private BufferedImage image = null;
    private int SCREEN_WIDTH;//loaded in in case was needed

    private int spriteCounter = 0;
    private int spriteNum = 1;
    
    HighScoreKnight(int x, int y, int SCREEN_WIDTH, int id){
        getKnightImage(id);
        this.x = x;
        this.y = y;
        this.id = id;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, KNIGHT_WIDTH, KNIGHT_WIDTH);
    }

    public void move(){
        spriteCounter++;
        if(spriteCounter > 20){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
            //System.out.println("Switch");
        }
    }

    public void getKnightImage(int id){
        if(id == 1){
            try{
                knight1 = ImageIO.read(getClass().getResourceAsStream("HighScoreImages\\knight_neutral_1.png"));
                knight2 = ImageIO.read(getClass().getResourceAsStream("HighScoreImages\\knight_step_right.png"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else if(id == 2){
            try{
                knight1 = ImageIO.read(getClass().getResourceAsStream("HighScoreImages\\knight_neutral_left_1.png"));
                knight2 = ImageIO.read(getClass().getResourceAsStream("HighScoreImages\\knight_step_left.png"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        image = knight1;
        
    }

    public void draw(Graphics2D g2){
        if(id == 1){
            if(spriteNum == 1){
                image = knight1;
            }
            else if(spriteNum == 2){
                image = knight2;
            }
            g2.drawImage(image, x, y, KNIGHT_WIDTH * 2, KNIGHT_WIDTH * 2, null);
        }
        else if(id == 2){
            if(spriteNum == 1){
                image = knight2;
            }
            else if(spriteNum == 2){
                image = knight1;
            }
            g2.drawImage(image, x, y, KNIGHT_WIDTH * 2, KNIGHT_WIDTH * 2, null);
        }
    }
}
