import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris_KeyHandler implements KeyListener{

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            if(Tetris_GameManager.gameOver == false){
                if(pausePressed){
                    pausePressed = false;
                    Tetris_GamePanel.music.play(0,true);
                    Tetris_GamePanel.music.loop();
                }
                else{
                    pausePressed = true;
                    Tetris_GamePanel.music.stop();
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    
    
}
