import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ArcadeFrame extends JFrame {

    private Image backgroundImage;
    private ImageIcon flappyBirdIcon;
    private ImageIcon minesweeperIcon;
    private ImageIcon tetrisIcon;
    private ImageIcon snakeIcon;
    private ImageIcon NinjaFightIcon;
    private ImageIcon chessIcon;
    private ImageIcon highScoreIcon;
    
    public ArcadeFrame() {
        // Load the background image
      super("Arcade");
     
        backgroundImage = Toolkit.getDefaultToolkit().getImage("resources/background1.png");
        flappyBirdIcon = new ImageIcon("resources/flappybirdIcon.png");
        minesweeperIcon = new ImageIcon("resources/minesweeperIcon.png");
        tetrisIcon = new ImageIcon("resources/TetrisIcon.png");
        snakeIcon = new ImageIcon("resources/snakeIcon.png");
        NinjaFightIcon = new ImageIcon("resources/NinjaFightIcon.png");
        chessIcon = new ImageIcon("resources/chessIcon.png");
        highScoreIcon = new ImageIcon("resources/highScoreIcon.png");
        // Ensure the images are loaded
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(backgroundImage, 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this, "Error loading images.", "Image Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        

        // Set the frame to be full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        
     // Add the Flappy Bird icon
        JLabel flappyBirdLabel = new JLabel(resizeIcon(flappyBirdIcon, 250, 250));
        // Set the size of the label (which will also set the size of the icon)
        flappyBirdLabel.setSize(250, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        flappyBirdLabel.setLocation(60, 500); // Replace with the X and Y coordinates of your marking
       
        backgroundPanel.add(flappyBirdLabel);
        flappyBirdLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    FlappyBirdGUI flappyBirdGUI = new FlappyBirdGUI();
                    flappyBirdGUI.displayGUI();
                });
            }
        });
        
        
     // Add the minesweeper icon
        JLabel minesweeprLabel = new JLabel(resizeIcon(minesweeperIcon, 250, 250));
        // Set the size of the label (which will also set the size of the icon)
        minesweeprLabel.setSize(200, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        minesweeprLabel.setLocation(300, 500); // Replace with the X and Y coordinates of your marking
        backgroundPanel.add(minesweeprLabel);
      
        minesweeprLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                	MinesweeperGUI minesweeperGUI = new MinesweeperGUI();
                    minesweeperGUI.displayGUI();
                });
            }
        });
        
     
        
     // Add the tetris icon
        JLabel tetrisLabel = new JLabel(resizeIcon(tetrisIcon, 315, 315));
        // Set the size of the label (which will also set the size of the icon)
        tetrisLabel .setSize(200, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        tetrisLabel .setLocation(520, 500); // Replace with the X and Y coordinates of your marking
        backgroundPanel.add(tetrisLabel);
       
        tetrisLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                	TetrisGUI tetrisGUI = new TetrisGUI();
                    tetrisGUI.displayGUI();
                });
            }
        });
        
        
        
        
        
        
        // Add the snake icon
           JLabel snakeLabel = new JLabel(resizeIcon(snakeIcon, 310, 310));
           // Set the size of the label (which will also set the size of the icon)
           snakeLabel .setSize(200, 200);
           // Place the label at the specified location (you'll need to adjust these values based on your requirement)
           snakeLabel .setLocation(750, 500); // Replace with the X and Y coordinates of your marking
           backgroundPanel.add(snakeLabel);
           snakeLabel .addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   
            	   javax.swing.SwingUtilities.invokeLater(() -> {
                       try {
                           SnakeGUI snakeGUI = new SnakeGUI();
                           snakeGUI.displayGUI();
                       } catch (UnsupportedAudioFileException | LineUnavailableException e1) {
                           e1.printStackTrace();
                       }
                   });
               }
           });
           
        
        
     // Add the ninjaFight icon
        JLabel fighterLabel = new JLabel(resizeIcon(NinjaFightIcon, 260, 260));
        // Set the size of the label (which will also set the size of the icon)
        fighterLabel .setSize(200, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        fighterLabel .setLocation(980, 500); // Replace with the X and Y coordinates of your marking
        backgroundPanel.add(fighterLabel);
       
        
        fighterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                	FighterFrame frame = new FighterFrame();
                });
            }
        });
        
        
        // Add the chessIcon
        JLabel chessLabel = new JLabel(resizeIcon(chessIcon, 260, 260));
        // Set the size of the label (which will also set the size of the icon)
        chessLabel .setSize(200, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        chessLabel .setLocation(1210, 500); // Replace with the X and Y coordinates of your marking
        backgroundPanel.add(chessLabel);
        
        chessLabel .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                	SwingUtilities.invokeLater(new ChessGame());
                });
            }
        });

        JLabel highScoreLabel = new JLabel(resizeIcon(highScoreIcon, 260, 260));
        // Set the size of the label (which will also set the size of the icon)
        highScoreLabel .setSize(200, 200);
        // Place the label at the specified location (you'll need to adjust these values based on your requirement)
        highScoreLabel .setLocation(650, 300); // Replace with the X and Y coordinates of your marking
        backgroundPanel.add(highScoreLabel);
        
        highScoreLabel .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                	HighScoreDisplay display = new HighScoreDisplay();
                });
            }
        });
        
        
        

        // Add ESC key listener to toggle the frame decoration
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose(); // destroy the JFrame object, effectively closing the application
                    setUndecorated(false); // Turn decorations back on
                    setVisible(true); // Show the frame again
                }
            }
        });

        // Make the frame visible
        setVisible(true);
    }
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
   


    private class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArcadeFrame::new);
    }
}
