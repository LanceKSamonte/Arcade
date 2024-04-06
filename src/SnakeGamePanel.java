import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

public class SnakeGamePanel extends JPanel implements ActionListener {

    private  int width;
    private  int height;
    private  int cellSize;
    private  Random random = new Random();
    private static  int FRAME_RATE = 20;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    //private int highScore;
    private GamePoint food;
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;
    private  LinkedList<GamePoint> snake = new LinkedList<>();
   // private  List<GamePoint> snake = new ArrayList<>();
	private BufferedImage foodSprite;
	private BufferedImage snakeSprite;
	private int score = 0;
	private Clip foodSoundClip;
    private Clip collisionSoundClip;
    private Clip startSoundClip;

    private SaveData save = new SaveData(4);

    public SnakeGamePanel( int width,  int height) throws UnsupportedAudioFileException, LineUnavailableException {
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2) + 5;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
		try{
			foodSprite = ImageIO.read(getClass().getResource("/games/snake_apple.png"));
			snakeSprite = ImageIO.read(getClass().getResource("/games/snakeSprite.png"));
			foodSoundClip = loadSound("/games/snake_collision.wav");
            collisionSoundClip = loadSound("/games/snake_collisionpad.wav");
            startSoundClip = loadSound("/games/snake_startgame.wav");
		}catch(IOException e){
			e.printStackTrace();
		}
    }

    public void startGame() {
        save.loadHighScore(Double.valueOf(score));
        resetGameData();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e) {
                handleKeyEvent(e.getKeyCode());
            }
        });

        new Timer(55, this).start();
    }

	private Clip loadSound(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
        clip.open(inputStream);
        return clip;
    }
    //save.sortHighScore(Double.valueOf(score));
	private void playFoodSound() {
        if (foodSoundClip != null) {
            foodSoundClip.setFramePosition(0);
            foodSoundClip.start();
        }
    }

    private void playCollisionSound() {
        if (collisionSoundClip != null) {
            collisionSoundClip.setFramePosition(0);
            collisionSoundClip.start();
        }
    }

    private void playstartSound() {
        if (startSoundClip != null) {
            startSoundClip.setFramePosition(0);
            startSoundClip.start();
        }
    }

    private void handleKeyEvent( int keyCode) {
        if (!gameStarted) {
            if (keyCode == KeyEvent.VK_SPACE) {
                gameStarted = true;
				playstartSound();
            }
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        newDirection = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        newDirection = Direction.DOWN;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        newDirection = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        newDirection = Direction.LEFT;
                    }
                    break;
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameStarted = false;
            gameOver = false;
            resetGameData();
        }
    }

    private void resetGameData() {
		snake.clear();
		score = 0;
		
		for (int i = 0; i < 3; i++) {
	
			int initialX = width / 2 - i * cellSize;
			if (initialX < 0) {
				initialX = 0;
			}
			snake.add(new GamePoint(initialX, height / 2)); 
		}
		direction = Direction.RIGHT;
		newDirection = Direction.RIGHT;
		generateFood();
	}
	

    private void generateFood() {
		score++;
        do {
            food = new GamePoint(random.nextInt(width / cellSize) * cellSize,
                    random.nextInt(height / cellSize) * cellSize);
        } while (snake.contains(food));
    }

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial", Font.BOLD, 20));
		graphics.drawString(String.valueOf(score - 1), 10, 20);

		if (!gameStarted) {
			printMessage(graphics, "Press Space Bar to Begin Game\nUse Arrow keys to move");
		} else {
			for (int i = 0; i < snake.size(); i++) {
				GamePoint point = snake.get(i);
				if (i == 0) {
					if (snakeSprite != null) {
						BufferedImage rotatedSprite = rotateSprite(snakeSprite, direction);
						graphics.drawImage(rotatedSprite, point.x, point.y, cellSize, cellSize, null);
					} else {
						graphics.setColor(Color.GREEN); // or any other color
						graphics.fillRect(point.x, point.y, cellSize, cellSize);
					}
				} else {
					int alpha = (int) (255 * (1 - 0.6 * (double) i / snake.size())); // Calculate alpha value based on segment position
					graphics.setColor(new Color(0, 255, 0, alpha)); // Set color with alpha value
					graphics.fillRect(point.x, point.y, cellSize, cellSize);
				}
			}

			if (foodSprite != null) {
				graphics.drawImage(foodSprite, food.x, food.y, cellSize, cellSize, null);
			} else {
				graphics.setColor(Color.red);
				graphics.fillOval(food.x, food.y, cellSize, cellSize);
			}

			if (gameOver) {
				int currentScore = score - 1;
                /*
				if (currentScore > highScore) {
					highScore = currentScore;
				}
                */
				printMessage(graphics, "Your Score: " + currentScore
						//+ "\nHigh Score: " + highScore
						+ "\nPress Space Bar to Reset");
			}
		}
	}

	private BufferedImage rotateSprite(BufferedImage sprite, Direction direction) {
		double rotationAngle = 0; // Default angle for right direction
		switch (direction) {
			case UP:
				rotationAngle = -Math.PI / 2; // 90 degrees counter-clockwise
				break;
			case DOWN:
				rotationAngle = Math.PI / 2; // 90 degrees clockwise
				break;
			case LEFT:
				rotationAngle = Math.PI; // 180 degrees
				break;
			// For RIGHT direction, no rotation needed (default angle is 0)
		}

		AffineTransform tx = AffineTransform.getRotateInstance(rotationAngle, sprite.getWidth() / 2, sprite.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(sprite, null);
	}

	
    private void printMessage( Graphics graphics,  String message) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(graphics.getFont().deriveFont(30F));
        int currentHeight = height / 3;
         var graphics2D = (Graphics2D) graphics;
         var frc = graphics2D.getFontRenderContext();
        for ( var line : message.split("\n")) {
             var layout = new TextLayout(line, graphics.getFont(), frc);
             var bounds = layout.getBounds();
             var targetWidth = (float) (width - bounds.getWidth()) / 2;
            layout.draw(graphics2D, targetWidth, currentHeight);
            currentHeight += graphics.getFontMetrics().getHeight();
        }
    }

    private void move() {
        direction = newDirection;

         GamePoint head = snake.getFirst();
         GamePoint newHead = switch (direction) {
            case UP -> new GamePoint(head.x, head.y - cellSize);
            case DOWN -> new GamePoint(head.x, head.y + cellSize);
            case LEFT -> new GamePoint(head.x - cellSize, head.y);
            case RIGHT -> new GamePoint(head.x + cellSize, head.y);
        };
        snake.addFirst(newHead);

        if (newHead.equals(food)) {
			playFoodSound();
            generateFood();
        } else if (isCollision()) {
			playCollisionSound();
            save.sortHighScore(Double.valueOf(score - 1));
            gameOver = true;
            snake.removeFirst();
        } else {
            snake.removeLast();
        }
    }

    private boolean isCollision() {
         GamePoint head = snake.getFirst();
         var invalidWidth = (head.x < 0) || (head.x >= width);
         var invalidHeight = (head.y < 0) || (head.y >= height);
        if (invalidWidth || invalidHeight) {
            return true;
        }

        return snake.size() != new HashSet<>(snake).size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            move();
        }
        repaint();
    }

    private record GamePoint(int x, int y) {
    }

    private enum Direction {
        UP, DOWN, RIGHT, LEFT
    }
}