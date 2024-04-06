import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class HighScorePanel extends JPanel implements Runnable{
    private String DataPath = "src\\saveData";
    private String fileName[] = new String[6];
    private String name[] = new String[6];
    Thread thread;
    private String flappyScore[] = new String[5];
    private String mineScore[] = new String[5];
    private String tetrisScore[] = new String[5];
    private String snakeScore[] = new String[5];
    private String fighterString[] = new String[2];
    private String chessString[] = new String[2];

    public final int tileSize = 64; //64x64 tiles
    final int maxScreenCol = 21;
    final int maxScreenRow = 10;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    Dimension dimension = new Dimension(screenWidth, screenHeight);

    HighScoreKnight knight1 = new HighScoreKnight(500, 500, screenWidth, 1);
    HighScoreKnight knight2 = new HighScoreKnight(650, 500, screenWidth, 2);
    HighScoreSnakeCircle snake = new HighScoreSnakeCircle(200, 600, tileSize / 2);
    HighScoreBird bird = new HighScoreBird(30, 30, screenWidth);
    SaveData newSaveOne, newSaveTwo, newSaveThree, newSaveFour, newSaveFive, newSaveSix;

    HighScorePanel(){
        fileName[0] = "FlappySaveData";
        name[0] = "Flappy Bird";
        fileName[1] = "MineSweeperSaveData";
        name[1] = "Mine Sweeper";
        fileName[2] = "TetrisSaveData";
        name[2] = "Tetris";
        fileName[3] = "SnakeSaveData";
        name[3] = "Snake";
        fileName[4] = "FighterSaveData";
        name[4] = "Ninja Knight Fight";
        fileName[5] = "ChessSaveData";
        name[5] = "Chess";
        newSaveOne = new SaveData(1);
        newSaveOne.loadHighScore(0);
        newSaveTwo = new SaveData(2);
        newSaveTwo.loadHighScore(0);
        newSaveThree = new SaveData(3);
        newSaveThree.loadHighScore(0);
        newSaveFour = new SaveData(4);
        newSaveFour.loadHighScore(0);
        newSaveFive = new SaveData(5);
        newSaveFive.saveDataFighterChess(0, 1);//normally you pass in 1 for this class, but by passing in 0, we add nothing to it.
        newSaveSix = new SaveData(6);
        newSaveSix.saveDataFighterChess(0, 1);
        getScores();
        this.setPreferredSize(dimension);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        start();
        snake.moveSnake();
        //bird = new HighScoreBird(0, 0, screenWidth);
    }

    public void getScores(){//check to see if the user has opened up (and thus would have made) a save file for high scores for all the games
        //if none exist, create the file (will default set all values to 0) and then send them into gameScore[]
        ////
        getFlappy();
        getMine();
        getTetris();
        getSnake();
        getFighter();
        getChess();
    }

    private void getFighter(){
        try {
            File f5 = new File(DataPath, fileName[4]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f5)));
            for(int i = 0; i < 2; i++){
                fighterString[i] = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getChess(){
        try {
            File f6 = new File(DataPath, fileName[5]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f6)));
            for(int i = 0; i < 2; i++){
                chessString[i] = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFlappy(){
        try {
            File f0 = new File(DataPath, fileName[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f0)));
            for(int i = 0; i < 5; i++){
                flappyScore[i] = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMine(){
        try {
            File f1 = new File(DataPath, fileName[1]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
            for(int i = 0; i < 5; i++){
                mineScore[i] = reader.readLine();
             }
             reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTetris(){
        try {
            File f2 = new File(DataPath, fileName[2]);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
             for(int i = 0; i < 5; i++){
                tetrisScore[i] = reader.readLine();
             }
             reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSnake(){
        try {
            File f3 = new File(DataPath, fileName[3]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f3)));
            for(int i = 0; i < 5; i++){
                snakeScore[i] = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        draw(g);
        bird.draw(g2);
        knight1.draw(g2);
        knight2.draw(g2);
        snake.draw(g);
        g.dispose();
    }

    public void update(){
        knight1.move();
        knight2.move();
        bird.move();
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    //runs the animation on the high score menu
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
                update();
                repaint();
				delta--;
                //System.out.println(lastTime);
			}
		}
	}

    public void draw(Graphics g){
        int nameArea = screenHeight / 2 - (32);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString("Flappy Bird", 30, nameArea);
        g.drawString(flappyScore[0], 30, nameArea + (32));
        g.drawString(flappyScore[1], 30, nameArea + (32 * 2));
        g.drawString(flappyScore[2], 30, nameArea + (32 * 3));
        g.drawString(flappyScore[3], 30, nameArea + (32 * 4));
        g.drawString(flappyScore[4], 30, nameArea + (32 * 5));

        g.drawString("Mine Sweeper", 230, nameArea);
        g.drawString(mineScore[0], 240, nameArea + (32));
        g.drawString(mineScore[1], 240, nameArea + (32 * 2));
        g.drawString(mineScore[2], 240, nameArea + (32 * 3));
        g.drawString(mineScore[3], 240, nameArea + (32 * 4));
        g.drawString(mineScore[4], 240, nameArea + (32 * 5));

        g.drawString("Tetris", 510, nameArea);
        g.drawString(tetrisScore[0], 460, nameArea + (32));
        g.drawString(tetrisScore[1], 460, nameArea + (32 * 2));
        g.drawString(tetrisScore[2], 460, nameArea + (32 * 3));
        g.drawString(tetrisScore[3], 460, nameArea + (32 * 4));
        g.drawString(tetrisScore[4], 460, nameArea + (32 * 5));

        g.drawString("Snake", 690, nameArea);
        g.drawString(snakeScore[0], 660, nameArea + (32));
        g.drawString(snakeScore[1], 660, nameArea + (32 * 2));
        g.drawString(snakeScore[2], 660, nameArea + (32 * 3));
        g.drawString(snakeScore[3], 660, nameArea + (32 * 4));
        g.drawString(snakeScore[4], 660, nameArea + (32 * 5));

        g.drawString("Fighter", 910, nameArea);
        g.drawString(fighterString[0], 860, nameArea + (32));
        g.drawString(fighterString[1], 860, nameArea + (32 * 2));

        g.drawString("Chess", 1150, nameArea);
        g.drawString(chessString[0], 1100, nameArea + (32));
        g.drawString(chessString[1], 1100, nameArea + (32 * 2));
        
    }
    
}
