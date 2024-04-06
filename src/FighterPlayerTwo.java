

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FighterPlayerTwo {

    private int size;//due to player model being square, size can be used for width and hieght.
    int x;
    int y;
    private int SCREEN_WIDTH;
    private int SCREEN_HIEGHT;

    FighterPanel panel;

    //variables for movement
    int speed;
    private int jumpNum;
    private int floor;
    private int weight;
    private int dashDistance = 200;
    private int dashing;
    private double lastDash = 0;
    private double coolDown = 1000000000;
    private double lastAttack = 0;
    private double attackCooldown = 1000000000;

    private boolean isMovingRight = false;
    private boolean isJumping = false;
    private boolean isNeutral = true;
    private boolean isDashing = false;
    private boolean attacking = false;

    BufferedImage image = null;
    private int spriteNum = 1;
    private int sprintCounter = 0;
    private int dashNum = 1;
    private int dashCounter = 0;
    private int attackNum = 1;
    private int attackCounter = 0;


    FighterKeyboard keyboard;
    BufferedImage right, left, jumpUp, jumpUpLeft, jumpDown, neutral, neutralLeft;
    BufferedImage attackOne, attackTwo, attackThree;
    BufferedImage attackOneLeft, attackTwoLeft, attackThreeLeft;
    

    FighterPlayerTwo(int size, FighterKeyboard keyboard, int floor, int SCREEN_WIDTH, int SCREEN_HIEGHT, FighterPanel panel){
        this.keyboard = keyboard;
        this.size = size * 2;//all sprites are 64-64, however that is to small so we are increasing the size of the sprite and model here.
        this.floor = floor;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HIEGHT = SCREEN_HIEGHT;
        this.panel = panel;

        setDefault();
        getPlayerImage();
    }

    public void setDefault(){
        x = SCREEN_WIDTH - size * 2;
        y = floor;
        weight = 2;
        speed = 6;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, size, size);
    }

    public void update(double delta){
        move(delta);
        dashAttack(delta);
        checkCollision();
    }

    public void checkCollision(){
        if(x <= 0){
            x = 0;
        }
        else if( x >= (SCREEN_WIDTH - size)){
            x = SCREEN_WIDTH - size;
        }
    }

    public void move(double delta){
        if(keyboard.attackPressed_2 && !keyboard.jump_1){
            if(delta > lastAttack){
                attacking = true;
                attack(delta);
            }
        }
        else if (keyboard.leftPressed_2) {
            if(isJumping){
                speed = 10;
            }
            else{
                speed = 6;
            }
            x -= speed;
            isMovingRight = false;
            isNeutral = false;
        }
        else if(keyboard.rightPressed_2){
            if(isJumping){
                speed = 10;
            }
            else{
                speed = 6;
            }
            x += speed;
            isMovingRight = true;
            isNeutral = false;
        }
        if(keyboard.jump_2 && y >= floor){
            jumpNum = 33;
            isJumping = true;
            isNeutral = false;
        }
        y -= jumpNum;
        jumpNum -= weight;

        if(y >= floor){
            y = floor;
            isJumping = false;
        }

        if(keyboard.jump_2 == false && keyboard.leftPressed_2 == false && keyboard.rightPressed_2 == false){
            isNeutral = true;
        }
        sprintCounter++;
        if(sprintCounter > 10){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            sprintCounter = 0;
        }
    }

    private void dashAttack(double delta){
        if(delta >= lastDash){
            if(isMovingRight && !isJumping){
                if(keyboard.dash_2){
                    lastDash = delta + coolDown; 
                    isDashing = true;
                    dashing = 25;          
                }
            }
            if(!isMovingRight && !isJumping){
                if(keyboard.dash_2){
                    lastDash = delta + coolDown;
                    isDashing = true;
                    dashing = 25;
                }
            }
            dashCounter++;
            dashTransition();
            
        }
    }

    public void attack(double delta){
        attackCounter++;
        if(attackCounter <= 5){
            attackNum = 1;
        }
        if(attackCounter > 5 && attackCounter <= 10){
            attackNum = 2;
        }
        if(attackCounter > 10 && attackCounter <= 15){
            attackNum = 3;
        }
        if(attackCounter > 15 && attackCounter <= 20){
            attackNum = 4;
        }
        if(attackCounter > 20 && attackCounter <= 25){
            attackNum = 5;
        }
        if(attackCounter > 25){
            attacking = false;
            attackCounter = 0;
            lastAttack = delta + attackCooldown;
        }
    }

    private void dashTransition(){
        if(isMovingRight && isDashing){
            while(dashing <= dashDistance){
                x = x + 25;
                dashing+=25;
            }
        }
        else if(!isMovingRight && isDashing){
            while(dashing <= dashDistance){
                x = x - 25;
                dashing+=25;
            }
        }
    }
    

    public void getPlayerImage(){
        try{
            neutral = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_neutral_1.png"));
            neutralLeft = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_neutral_left_1.png"));
            right = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_step_right.png"));
            left = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_step_left.png"));
            jumpUp = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_jump.png"));
            jumpUpLeft = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_jump_left.png"));

            attackOne = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_1.png"));
            attackTwo = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_2.png"));
            attackThree = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_3.png"));

            attackOneLeft = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_1_left.png"));
            attackTwoLeft = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_2_left.png"));
            attackThreeLeft = ImageIO.read(getClass().getResourceAsStream("FighterImagesP2/knight_attack_3_left.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics g2){
        if(attacking && isMovingRight){
            if(attackNum == 1){
                image = attackOne;
            }
            else if(attackNum == 2){
                image = neutral;
            }
            else if(attackNum == 3){
                image = attackTwo;
            }
            else if(attackNum == 4){
                image = attackThree;
            }
            else if(attackNum == 5){
                image = attackTwo;
            }
            else if(attackNum == 6){
                image = neutral;
            }
        }
        else if(attacking && !isMovingRight){
            if(attackNum == 1){
                image = attackOneLeft;
            }
            else if(attackNum == 2){
                image = neutralLeft;
            }
            else if(attackNum == 3){
                image = attackTwoLeft;
            }
            else if(attackNum == 4){
                image = attackThreeLeft;
            }
            else if(attackNum == 5){
                image = attackTwoLeft;
            }
            else if(attackNum == 6){
                image = neutralLeft;
            }
        }
        else if(!attacking){
            if(isNeutral){
                image = neutral;
                if(!isMovingRight){
                    image = neutralLeft;
                }
            }
            else{
                if(isJumping){
                    image = jumpUp;
                    if(!isMovingRight){
                        image = jumpUpLeft;
                    }
                }
                else{
                    if(isMovingRight){
                        if(spriteNum == 1){
                            image = right;
                        }
                        else if(spriteNum == 2){
                            image = neutral;
                        }
                    }
                    else{
                        if(spriteNum == 1){
                            image = left;
                        }
                        else if(spriteNum == 2){
                            image = neutralLeft;
                        }
                    }
                }
            }
        }
        g2.drawImage(image, x, y, size, size, null);
    }
}
/*
 * if(dashCounter > 10){
                if(dashNum == 1){
                    dashNum = 2;
                }
                else if(dashNum == 2){
                    dashNum = 3;
                }
                else if(dashNum == 3){
                    dashNum = 4;
                }
                else if(dashNum == 4){
                    dashNum = 5;
                }
                else if(dashNum == 5){
                    dashNum = 1;
                }
                dashCounter = 0;
            }
        }
        else{
            isDashing = false;
        }

if(isDashing && isMovingRight){
                if(dashNum == 1){
                    image = attackOne;
                }
                else if(dashNum == 2){
                    image = neutral;
                }
                else if(dashNum == 3){
                    image = attackTwo;
                }
                else if(dashNum == 4){
                    image = attackThree;
                }
                else if(dashNum == 5){
                    image = attackTwo;
                }
            }
            
            else if(isDashing && !isMovingRight){
                if(dashNum == 1){
                    image = attackOneLeft;
                }
                else if(dashNum == 2){
                    image = neutralLeft;
                }
                else if(dashNum == 3){
                    image = attackTwoLeft;
                }
                else if(dashNum == 4){
                    image = attackThreeLeft;
                }
                else if(dashNum == 5){
                    image = attackTwoLeft;
                }
            }
 */