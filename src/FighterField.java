

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FighterField {

    private int tileSize;
    private int c;
    private int r;
    private BufferedImage sky, ground, bush;
    
    FighterField(int x, int c, int r){
        this.tileSize = x;
        this.c = c;
        this.r = r;
        getFieldImage();
    }

    public void getFieldImage(){
        try{
            ground = ImageIO.read(getClass().getResourceAsStream("FighterImagesField/ground2.png"));
            sky = ImageIO.read(getClass().getResourceAsStream("FighterImagesField/sky.png"));
            bush = ImageIO.read(getClass().getResourceAsStream("FighterImagesField/bush.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        for(int i = 0; i < c; i++){
            for(int j = 0; j < r; j++){
                g2.drawImage(sky, i * tileSize, j * tileSize, tileSize, tileSize,null);
            }
        }
        g2.drawImage(ground, 00, 420, 1200, 100, null);
        int i = 30;
        while(i < 1000){
            g2.drawImage(bush, i, 356, 100, 64, null);
            i = i + 150;
        }
    }    
}