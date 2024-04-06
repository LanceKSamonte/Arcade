import java.awt.Color;

public class Tetris_Square_Shape extends Tetris_Shape{
    public Tetris_Square_Shape(){
        create(Color.yellow);
    }

    public void setXY(int x, int y){
        /**
         * 0
         * 0
         * 0 0
         */

        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
        b[1].y = b[0].y + Tetris_Block.SIZE; 
        b[2].x = b[0].x + Tetris_Block.SIZE; 
        b[2].y = b[0].y;
        b[3].x = b[0].x + Tetris_Block.SIZE; 
        b[3].y = b[0].y + Tetris_Block.SIZE; 
        
    }
    public void getDirection1(){
    }

    public void getDirection2(){    
    }

    public void getDirection3(){ 
    }
    
    public void getDirection4(){ 
    }
}
