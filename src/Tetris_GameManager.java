import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

public class Tetris_GameManager {
    
    //Game Section
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Shapes
    Tetris_Shape currentShape;
    final int SHAPE_START_X;
    final int SHAPE_START_Y;
    Tetris_Shape nextShape;
    final int NEXTSHAPE_X;
    final int NEXTSHAPE_Y;
    public static ArrayList<Tetris_Block> staticBlocks = new ArrayList<>();

    //Play
    public static int dropInterval = 60;
    static boolean gameOver;
    private SaveData save = new SaveData(3);

    //Effects
    boolean effectCounterOn;
    int effectCoutner;
    ArrayList<Integer> effectY = new ArrayList<>();
    
    //Score
    int level = 1;
    int lines;
    int score;

    public Tetris_GameManager(){ 
        left_x = (Tetris_GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
        save.loadHighScore(Double.valueOf(score));
        //shape start position
        SHAPE_START_X = left_x + (WIDTH/2) - Tetris_Block.SIZE;
        SHAPE_START_Y = top_y + Tetris_Block.SIZE;

        NEXTSHAPE_X = right_x + 175;
        NEXTSHAPE_Y = top_y + 500;

        //init shape
        currentShape = pickShape();
        currentShape.setXY(SHAPE_START_X, SHAPE_START_Y);
        nextShape = pickShape();
        nextShape.setXY(NEXTSHAPE_X, NEXTSHAPE_Y);

    }

    private Tetris_Shape pickShape(){

        Tetris_Shape shape = null;
        int i = new Random().nextInt(7);
        switch(i){
            case 0: shape = new Tetris_L1_Shape();break;
            case 1: shape = new Tetris_L2_Shape();break;
            case 2: shape = new Tetris_Square_Shape();break;
            case 3: shape = new Tetris_Long_Shape();break;
            case 4: shape = new Tetris_Z1_Shape();break;
            case 5: shape = new Tetris_Z2_Shape();break;
            case 6: shape = new Tetris_T_Shape();break;
        }
        return shape;
    }
    public void update(){

        if(currentShape.active == false){
            staticBlocks.add(currentShape.b[0]);
            staticBlocks.add(currentShape.b[1]);
            staticBlocks.add(currentShape.b[2]);
            staticBlocks.add(currentShape.b[3]);

            if(currentShape.b[0].x == SHAPE_START_X && currentShape.b[0].y == SHAPE_START_Y){
                gameOver = true;
                save.sortHighScore(Double.valueOf(score / 10));
                Tetris_GamePanel.music.stop();
                Tetris_GamePanel.soundEffect.play(2, false);
            }

            currentShape.deactivating = false;

            currentShape = nextShape;
            currentShape.setXY(SHAPE_START_X, SHAPE_START_Y);
            nextShape = pickShape();
            nextShape.setXY(NEXTSHAPE_X, NEXTSHAPE_Y);
            
            checkDelete();
        }else{
            currentShape.update();
        }
        
    }

    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while(x < right_x && y < bottom_y){

            for(int i = 0; i < staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }

            x += Tetris_Block.SIZE;

            if(x == right_x){
                    
                if(blockCount == 12){
                    
                    effectCounterOn = true;
                    effectY.add(y);

                    for(int i = staticBlocks.size()-1; i > -1; i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }
                     
                    lineCount++;
                    lines++;
                    
                    //drop speed
                    if(lines % 10 == 0 && dropInterval > 1){
                        level++;
                        if(dropInterval > 10){
                            dropInterval -= 10;
                        }else{
                            dropInterval -= 1;
                        }
                    }

                    for(int i = 0; i < staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Tetris_Block.SIZE;
                        }
                    }

                }
                blockCount = 0;
                x = left_x;
                y += Tetris_Block.SIZE;

            }
        }

        //add Score
        if(lineCount > 0){
            Tetris_GamePanel.soundEffect.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2){
        
        //Game Space
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
        
        //next shape frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);
        
        //Score frame
        g2.drawRect(x, top_y, 250, 300);
        x+= 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y); y+=70;
        g2.drawString("LINES: " + lines, x, y); y+=70;
        g2.drawString("SCORE: " + score, x, y);
        
        if(currentShape != null){
            currentShape.draw(g2);
        }

        //next Shape
        nextShape.draw(g2);

        //draw static shapes
        for(int i = 0; i <staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        //draw effect
        if(effectCounterOn){
            effectCoutner++;

            g2.setColor(Color.white);
            for(int i = 0; i< effectY.size(); i++){
                g2.fillRect(left_x, effectY.get(i), WIDTH, Tetris_Block.SIZE);
            }

            if(effectCoutner == 10){
                effectCounterOn = false;
                effectCoutner = 0;
                effectY.clear();
            }
        }

        //pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        
        if(gameOver){
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        else if(Tetris_KeyHandler.pausePressed){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("Paused", x, y);
        }

        //draw title
        x = 150;
        y = top_y + 100;
        g2.setColor(Color.cyan);
        g2.setFont(new Font("Arial", Font.PLAIN, 60));
        g2.drawString("Tetris", x, y);

        //controls
        x = 100;
        y = top_y + 350;
        g2.setColor(Color.white);
        g2.setFont(null);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("WASD or Arrow Keys to move", x, y);
        g2.drawString("W/UP to rotate", x, y + 50);
    }

}
