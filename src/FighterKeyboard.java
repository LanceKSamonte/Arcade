

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FighterKeyboard implements KeyListener{
    public boolean jump_1, dash_1, leftPressed_1, rightPressed_1, attackPressed_1;
    public boolean jump_2, dash_2, leftPressed_2, rightPressed_2, attackPressed_2;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //player 1
        if(code == KeyEvent.VK_W ){
            jump_1 = true;
        }
        if(code == KeyEvent.VK_S){
            dash_1 = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed_1 = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed_1 = true;
        }
        if(code == KeyEvent.VK_E){
            attackPressed_1 = true;
        }

        //player 2
        if(code == KeyEvent.VK_UP){
            jump_2 = true;
        }
        if(code == KeyEvent.VK_DOWN){
            dash_2 = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed_2 = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed_2 = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            attackPressed_2 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            jump_1 = false;
        }
        if(code == KeyEvent.VK_S){
            dash_1 = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed_1 = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed_1 = false;
        }
        if(code == KeyEvent.VK_E){
            attackPressed_1 = false;
        }

        if(code == KeyEvent.VK_UP){
            jump_2 = false;
        }
        if(code == KeyEvent.VK_DOWN){
            dash_2 = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed_2 = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed_2 = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            attackPressed_2 = false;
        }
    }
}