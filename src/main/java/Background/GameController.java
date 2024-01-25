package Background;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameController extends Game{
    private static volatile boolean leftPressed = false;
    private static volatile boolean rightPressed = false;
    private static volatile boolean upPressed = false;
    private static volatile boolean downPressed = false;
    private static volatile boolean escPressed = false;
    private static volatile boolean zeroPressed = false;
    private static volatile boolean onePressed = false;
    private static volatile boolean twoPressed = false;
    private static volatile boolean threePressed = false;
    boolean on = true;
    private Game game;
    private Screen screen;
    private Piece pieceview;
    private Arena boardview;
    private Score scoreView;
    private NextPiece nextPieceView;

    public GameController(Screen scr) {
        game = new Game();
        boardview = new Arena(game.getBoard());
        scoreView = new Score(game.getScore());
        screen = scr;
        //setup();
    }

    public void setup() {
        try {
            TerminalSize terminalSize = new TerminalSize(75, 30);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        while (on) {
            if(game.gameOver()){
                System.out.println("Game Over");
                on = false;
                break;
            }


            //game timing
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            //Game Logic
            if (game.isPieceNull()) {
                pieceview = new Piece(game.getPiece());
                nextPieceView = new NextPiece(game.getNextPiece());
            }
            game.nextTick();

            //input
            sendInputToModel();

            //render output
            try {
                draw();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void draw() throws IOException {
        screen.clear();
        //initialize and draw background
        TextGraphics screenGraphics = screen.newTextGraphics();
        screenGraphics.setBackgroundColor(TextColor.Factory.fromString("#3A3A3A"));
        screenGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(80, 40), ' ');

        //NEXT PIECE - will need refactoring

        //SCORE - will need refactoring

        scoreView.draw(screenGraphics);
        nextPieceView.draw(screenGraphics);
        boardview.draw(screenGraphics);
        pieceview.draw(screenGraphics);

        screen.refresh();
    }

    public static int getGameScreenXoffset() {
        return gameScreenXoffset;
    }
    public static int getGameScreenYoffset() {
        return gameScreenYoffset;
    }
    public static int getGameScreenWidth() {
        return gameScreenWidth / 2;
    }
    public static int getGameScreenLength() {
        return gameScreenWidth;
    }

    public void sendInputToModel() throws IOException {
        if (isLeftPressed()) {
            game.pressedLeft();
        }
        if (isRightPressed()) {
            game.pressedRight();
        }
        if (isUpPressed()) {
            game.pressedUp();
        }
        if (isDownPressed()) {
            game.pressedDown();
        }
        if (isEscPressed()) {
            on = false;
        }
    }

    public void setOn() {
        on = true;
    }

    public Game getGame() {
        return game;
    }
    public static boolean isLeftPressed() {
        synchronized (GameController.class) {
            return leftPressed;
        }
    }
    public static boolean isRightPressed() {
        synchronized (GameController.class) {
            return rightPressed;
        }
    }
    public static boolean isUpPressed() {
        synchronized (GameController.class) {
            return upPressed;
        }
    }
    public static boolean isDownPressed() {
        synchronized (GameController.class) {
            return downPressed;
        }
    }
    public static boolean isEscPressed() {
        synchronized (GameController.class) {
            return escPressed;
        }
    }
    public static boolean isZeroPressed() {
        synchronized (GameController.class) {
            return zeroPressed;
        }
    }
    public static boolean isOnePressed() {
        synchronized (GameController.class) {
            return onePressed;
        }
    }
    public static boolean isTwoPressed() {
        synchronized (GameController.class) {
            return twoPressed;
        }
    }
    public static boolean isThreePressed() {
        synchronized (GameController.class) {
            return threePressed;
        }
    }

    public static void controller_override() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent mykey) {
                synchronized (GameController.class) {
                    switch (mykey.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            if (mykey.getKeyCode() == KeyEvent.VK_LEFT) {
                                leftPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_RIGHT) {
                                rightPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_UP) {
                                upPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_DOWN) {
                                downPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                escPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_0) {
                                zeroPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_1) {
                                onePressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_2) {
                                twoPressed = true;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_3) {
                                threePressed = true;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            if (mykey.getKeyCode() == KeyEvent.VK_LEFT) {
                                leftPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_RIGHT) {
                                rightPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_UP) {
                                upPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_DOWN) {
                                downPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                escPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_0) {
                                zeroPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_1) {
                                onePressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_2) {
                                twoPressed = false;
                            }
                            else if (mykey.getKeyCode() == KeyEvent.VK_3) {
                                threePressed = false;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }
}