import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class HighScoreSnakeCircle implements ActionListener {
    private int x;
    private int y;
    private int cellSize;
    private LinkedList<Point> snake;
    private Timer timer;
    private BufferedImage snakeHeadSprite;
    private BufferedImage rotatedSprite;

    public HighScoreSnakeCircle(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.cellSize = cellSize;
        snake = new LinkedList<>();
        initializeSnake();
        
        startTimer();
    }

    private void initializeSnake() {
        // Add segments of the snake at the specified position
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(x - i * cellSize, y));
        }
    }

    private void startTimer() {
        timer = new Timer(100, this); // Adjust the interval as needed
        timer.start();
    }

    private int stepsInCurrentDirection = 0;
    private int stepsToMoveInCurrentDirection = 2;
    private Direction currentDirection = Direction.RIGHT; // Initial direction

    void moveSnake() {
        // Move the snake in the current direction
        Point head = snake.getFirst();
        Point newHead;
        loadSnakeHeadSprite();
        switch (currentDirection) {
            case UP:
                newHead = new Point(head.x, head.y - cellSize);
                break;
            case DOWN:
                newHead = new Point(head.x, head.y + cellSize);
                break;
            case LEFT:
                newHead = new Point(head.x - cellSize, head.y);
                break;
            case RIGHT:
                newHead = new Point(head.x + cellSize, head.y);
                break;
            default:
                newHead = head;
        }
        snake.addFirst(newHead);

        // Remove the tail segment
        snake.removeLast();

        // Increment steps in the current direction
        stepsInCurrentDirection++;

        // Change direction if reached the number of steps
        if (stepsInCurrentDirection >= stepsToMoveInCurrentDirection) {
            stepsInCurrentDirection = 0;
            switch (currentDirection) {
                case UP:
                    currentDirection = Direction.LEFT;
                    break;
                case DOWN:
                    currentDirection = Direction.RIGHT;
                    break;
                case LEFT:
                    currentDirection = Direction.DOWN;
                    break;
                case RIGHT:
                    currentDirection = Direction.UP;
                    break;
            }
        }
    }


    private void loadSnakeHeadSprite() {
        try {
            snakeHeadSprite = ImageIO.read(getClass().getResource("games\\snakeSprite.png"));
            rotatedSprite = rotateSprite(snakeHeadSprite, currentDirection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics graphics) {
        // Draw the snake segments
        for (int i = 0; i < snake.size(); i++) {
            Point segment = snake.get(i);
            if (i == 0 && rotatedSprite != null) {
                graphics.drawImage(rotatedSprite, segment.x, segment.y, cellSize, cellSize, null);
            } else {
                int alpha = (int) (255 * (1 - 0.6 * (double) i / snake.size()));
                graphics.setColor(new Color(0, 255, 0, alpha));
                graphics.fillRect(segment.x, segment.y, cellSize, cellSize);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        moveSnake();
    }
    private enum Direction {
        UP, DOWN, RIGHT, LEFT
    }
}
