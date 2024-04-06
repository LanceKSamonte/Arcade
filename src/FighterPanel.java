


import java.awt.*;
import javax.swing.JPanel;

public class FighterPanel extends JPanel implements Runnable{

    public final int tileSize = 64; //64x64 tiles
    final int maxScreenCol = 14;
    final int maxScreenRow = 8;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final int floor = 300;

    double lastAttackOne = 0;
    double lastAttacktwo = 0;
    double attackCooldown = 1000000000;
    Dimension dimension = new Dimension(screenWidth, screenHeight);

    FighterKeyboard keyboard = new FighterKeyboard();
    FighterField field = new FighterField(tileSize, maxScreenCol, maxScreenRow);
    Thread gameThread;
    FighterPlayerOne playerOne;
    FighterPlayerTwo playerTwo;
    FighterPlayerHealth healthOne;
    FighterPlayerHealth healthTwo;
    FighterCloud cloudOne;
    FighterCloud cloudTwo;
    FighterCloud cloudThree;
    SaveData save = new SaveData(5);
    //PlayerTwo playerTwo;

    FighterPanel(){
        this.setPreferredSize(dimension);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboard);
        this.setFocusable(true);
        newPlayers();
        newClouds();
    }

    public void newPlayers(){
        playerOne = new FighterPlayerOne(tileSize, keyboard, floor, screenWidth, screenHeight, this);
        playerTwo = new FighterPlayerTwo(tileSize, keyboard, floor, screenWidth, screenHeight, this);
        healthOne = new FighterPlayerHealth(screenWidth, screenHeight, 1, 10, screenHeight - 84);
        healthTwo = new FighterPlayerHealth(screenWidth, screenHeight, 2, screenWidth - 160, screenHeight - 84);
    }

    public void newClouds(){
        cloudOne = new FighterCloud(50, 30, screenWidth);
        cloudTwo = new FighterCloud(300, 125, screenWidth);
        cloudThree = new FighterCloud(600, 75, screenWidth);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        field.draw(g2);
        cloudOne.draw(g2);
        cloudTwo.draw(g2);
        cloudThree.draw(g2);
        healthOne.draw(g2, g);
        healthTwo.draw(g2, g);
        playerOne.draw(g2);
        playerTwo.draw(g2);
        g.dispose();
    }

    public void update(double delta){
        cloudOne.move();
        cloudTwo.move();
        cloudThree.move();
        playerOne.update(delta);
        playerTwo.update(delta);
        //checkCollision();
        checkAttack(delta);
        checkWin();
        healthOne.checkHealth();
        healthTwo.checkHealth();
    }

    public void checkWin(){
        if(healthOne.wins == false){
            save.saveDataFighterChess(1, 2);
            gameThread.stop();
        }
        if(healthTwo.wins == false){
            save.saveDataFighterChess(1, 1);
            gameThread.stop();
        }
    }
    /*
    public void checkCollision(){

        if(playerOne.getBounds().intersects(playerTwo.getBounds())){
                playerTwo.x = playerTwo.x + tileSize;
                playerOne.x = playerOne.x - tileSize;
        }
    }
    */

    public void checkAttack(double delta){
        if(playerOne.getBounds().intersects(playerTwo.getBounds())){
            if(delta >= lastAttackOne){
                lastAttackOne = delta + attackCooldown;
                if(keyboard.attackPressed_1){
                    healthTwo.playerHp = healthTwo.playerHp - 1;
                }
            }
            if(delta >= lastAttacktwo){
                lastAttacktwo = delta + attackCooldown;
                if(keyboard.attackPressed_2){
                    healthOne.playerHp = healthOne.playerHp - 1;
                }
            }
        }
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
                update(lastTime);
                repaint();
				delta--;
                //System.out.println(lastTime);
			}
		}
	}

}