

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "chess/wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "chess/bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "chess/wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "chess/bknight.png";
	private static final String RESOURCES_WROOK_PNG = "chess/wrook.png";
	private static final String RESOURCES_BROOK_PNG = "chess/brook.png";
	private static final String RESOURCES_WKING_PNG = "chess/wking.png";
	private static final String RESOURCES_BKING_PNG = "chess/bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "chess/bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "chess/wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "chess/wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "chess/bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final ChessGameWindow g;
    
    // List of pieces and whether they are movable
    public final LinkedList<Piece> Bpieces;
    public final LinkedList<Piece> Wpieces;
    public List<Square> movable;
    
    private boolean whiteTurn;

    private Piece currPiece;
    private int currX;
    private int currY;
    
    private ChessCheckmateDetector cmd;
    
    public Board(ChessGameWindow g) {
        this.g = g;
        board = new Square[8][8];
        Bpieces = new LinkedList<Piece>();
        Wpieces = new LinkedList<Piece>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                    this.add(board[x][y]);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    private void initializePieces() {
    	
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new ChessPawn(0, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new ChessPawn(1, board[6][x], RESOURCES_WPAWN_PNG));
        }
        
        board[7][3].put(new ChessQueen(1, board[7][3], RESOURCES_WQUEEN_PNG));
        board[0][3].put(new ChessQueen(0, board[0][3], RESOURCES_BQUEEN_PNG));
        
        ChessKing bk = new ChessKing(0, board[0][4], RESOURCES_BKING_PNG);
        ChessKing wk = new ChessKing(1, board[7][4], RESOURCES_WKING_PNG);
        board[0][4].put(bk);
        board[7][4].put(wk);

        board[0][0].put(new ChessRook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new ChessRook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new ChessRook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new ChessRook(1, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new ChessKnight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new ChessKnight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new ChessKnight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new ChessKnight(1, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new ChessBishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new ChessBishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new ChessBishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new ChessBishop(1, board[7][5], RESOURCES_WBISHOP_PNG));
        
        
        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                Bpieces.add(board[y][x].getOccupyingPiece());
                Wpieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        
        cmd = new ChessCheckmateDetector(this, Wpieces, Bpieces, wk, bk);
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;

            List<Square> legalMoves = currPiece.getLegalMoves(this);
            movable = cmd.getAllowableSquares(whiteTurn);

            if (legalMoves.contains(sq) && movable.contains(sq)
                    && cmd.testMove(currPiece, sq)) {
                sq.setDisplay(true);
                currPiece.move(sq);
                cmd.update();

                if (cmd.blackCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(0);
                } else if (cmd.whiteCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(1);
                } else {
                    currPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = cmd.getAllowableSquares(whiteTurn);
                }

            } else {
                currPiece.getPosition().setDisplay(true);
                currPiece = null;
            }
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}