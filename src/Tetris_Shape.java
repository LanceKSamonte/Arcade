
import java.awt.Color;
import java.awt.Graphics2D;


public class Tetris_Shape{

    public Tetris_Block b[] = new Tetris_Block[4];
    public Tetris_Block tempB[] = new Tetris_Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c){
        b[0] = new Tetris_Block(c);
        b[1] = new Tetris_Block(c);
        b[2] = new Tetris_Block(c);
        b[3] = new Tetris_Block(c);
        tempB[0] = new Tetris_Block(c);
        tempB[1] = new Tetris_Block(c);
        tempB[2] = new Tetris_Block(c);
        tempB[3] = new Tetris_Block(c);

    }

    public void setXY(int x, int y){

    }

    public void updateXY(int direction){

        checkMovementCollision();
        if(leftCollision == false && rightCollision == false && bottomCollision == false){
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        //left wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x == Tetris_GameManager.left_x){
                leftCollision = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Tetris_Block.SIZE == Tetris_GameManager.right_x){
                rightCollision = true;
            }
        }

        // bottom
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Tetris_Block.SIZE == Tetris_GameManager.bottom_y){
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        //left wall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < Tetris_GameManager.left_x){
                leftCollision = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x + Tetris_Block.SIZE > Tetris_GameManager.right_x){
                rightCollision = true;
            }
        }

        // bottom
        for(int i = 0; i < b.length; i++){
            if(tempB[i].y + Tetris_Block.SIZE > Tetris_GameManager.bottom_y){
                bottomCollision = true;
            }
        }
    }

    private void checkStaticBlockCollision(){
        for(int i = 0; i < Tetris_GameManager.staticBlocks.size(); i++){
            int targetX = Tetris_GameManager.staticBlocks.get(i).x;
            int targetY = Tetris_GameManager.staticBlocks.get(i).y;


            for(int j = 0; j < b.length; j++){
                if(b[j].y + Tetris_Block.SIZE == targetY && b[j].x == targetX){
                    bottomCollision = true;
                }
            }

            for(int j = 0; j < b.length; j++){
                if(b[j].x - Tetris_Block.SIZE == targetX && b[j].y == targetY){
                    leftCollision = true;
                }
            }

            for(int j = 0; j < b.length; j++){
                if(b[j].x + Tetris_Block.SIZE == targetX && b[j].y == targetY){
                    rightCollision = true;
                }
            }
        }
    }

    public void update(){

        if(deactivating){
            deactivating();
        }

        //movement
        if(Tetris_KeyHandler.upPressed){
            switch(direction){
                case 1: getDirection2();break;
                case 2: getDirection3();break;
                case 3: getDirection4();break;
                case 4: getDirection1();break;
            }
            Tetris_KeyHandler.upPressed = false;
            Tetris_GamePanel.soundEffect.play(3, false);
        }

        checkMovementCollision();

        if(Tetris_KeyHandler.downPressed){

            if(bottomCollision == false){
                b[0].y += Tetris_Block.SIZE;
                b[1].y += Tetris_Block.SIZE;
                b[2].y += Tetris_Block.SIZE;
                b[3].y += Tetris_Block.SIZE;
                autoDropCounter = 0;
            }
        
            Tetris_KeyHandler.downPressed = false;
        }
        if(Tetris_KeyHandler.rightPressed){

            if(rightCollision == false){
                b[0].x += Tetris_Block.SIZE;
                b[1].x += Tetris_Block.SIZE;
                b[2].x += Tetris_Block.SIZE;
                b[3].x += Tetris_Block.SIZE;
            }
            Tetris_KeyHandler.rightPressed = false;
        }
        if(Tetris_KeyHandler.leftPressed){
            
            if(leftCollision == false){
                b[0].x -= Tetris_Block.SIZE;
                b[1].x -= Tetris_Block.SIZE;
                b[2].x -= Tetris_Block.SIZE;
                b[3].x -= Tetris_Block.SIZE;
            }
            Tetris_KeyHandler.leftPressed = false;
        }

        if(bottomCollision){

            if(deactivating == false){
                Tetris_GamePanel.soundEffect.play(4, false);
            }
            deactivating = true; 

        }
        else{
            //autodrop
            autoDropCounter++;
            if(autoDropCounter == Tetris_GameManager.dropInterval){
                b[0].y += Tetris_Block.SIZE;
                b[1].y += Tetris_Block.SIZE;
                b[2].y += Tetris_Block.SIZE;
                b[3].y += Tetris_Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    private void deactivating(){
        deactivateCounter++;

        if(deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();

            if(bottomCollision){
                active = false;

            }
        }
    }

    public void draw(Graphics2D g2){
        int margin = 2;

        g2.setColor(b[0].c);
        
        g2.fillRect(b[0].x + margin, b[0].y + margin, Tetris_Block.SIZE - (margin * 2), Tetris_Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x + margin, b[1].y + margin, Tetris_Block.SIZE - (margin * 2), Tetris_Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x + margin, b[2].y + margin, Tetris_Block.SIZE - (margin * 2), Tetris_Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x + margin, b[3].y + margin, Tetris_Block.SIZE - (margin * 2), Tetris_Block.SIZE - (margin * 2));
    
    }
}