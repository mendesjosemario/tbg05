package Background;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;




public class Game {
    public TerminalHandler getTerminalHandler() {
        return terminalHandler;
    }

    private final TerminalHandler terminalHandler;




    private final Screen screen;

    private final GameController gameController;
    private GameState currentState ;
    private boolean isPaused = false;  // Rastreia se o jogo está pausado


    private Piece piece;
    private  Piece nextPiece;

    public int getGameScreenXoffset() {
        return gameScreenXoffset;
    }

    public int getGameScreenYoffset() {
        return gameScreenYoffset;
    }

    public int getGameScreenWidth() {
        return gameScreenWidth;
    }


    private  final int gameScreenXoffset = 6;
    private  final int gameScreenYoffset = 2;
    private  final int gameScreenWidth = 26;
    private final int gameScreenLength = 26;
    private final int gameSpeed = 5;  //smaller is faster, ticks needed to force piece down
    private int nTickCounter = 0;
    private final Score score;
    private final Arena arena;
    public Game(TerminalHandler terminalHandler) throws IOException {
        this.terminalHandler = terminalHandler;
        Terminal terminal = terminalHandler.getTerminal();
        this.arena = new Arena(gameScreenWidth, gameScreenLength);
        this.screen = new TerminalScreen(terminal);
        this.gameController = new GameController(screen, this);
        this.score = new Score();
        currentState = GameState.MENU;
        screen.startScreen();
    }

    public void start() throws IOException {
        while (true) {
            switch (currentState) {
                case MENU -> {
                    screen.clear();
                    drawMenu();
                    handleGameplayInput();
                }
                case INSTRUCTIONS -> {
                    screen.clear();
                    drawInstructions();
                    handleGameplayInput();
                }
                case PLAYING -> {
                    screen.clear();
                    arena.setRunning(true);
                    arena.drawArena(screen, gameController);
                    handleGameplayInput();
                }
                case PAUSED -> {
                    screen.clear();
                    currentState = GameState.PAUSED;
                    drawMenu();
                    handleGameplayInput();
                }
            }
            if (currentState == GameState.QUIT) {
                arena.setRunning(false);
                break;
            }
        }

        screen.stopScreen();
        terminalHandler.getTerminal().close();
    }

    private void drawMenu() throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(10, 5, "TETRIS", SGR.BOLD);
        textGraphics.putString(10, 7, "1. Jogar");
        textGraphics.putString(10, 8, "2. Instruções");
        textGraphics.putString(10, 9, "Q. Sair");
        screen.refresh();
    }



    private void drawInstructions() throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(10, 5,  "             Instruções do Jogo", SGR.BOLD);
        textGraphics.putString(10, 7,  "Seta Esquerda:       Mover para esquerda");
        textGraphics.putString(10, 8,  "Seta Direita:        Mover para direita");
        textGraphics.putString(10, 9,  "Seta Cima:           Rotacionar");
        textGraphics.putString(10, 10, "Seta Baixo:          Dobrar velocidade");
        textGraphics.putString(10, 11, "Esc:                 Voltar ao menu");
        screen.refresh();
    }

    private void handleGameplayInput() throws IOException {
        KeyStroke key = terminalHandler.getKeyPress();
        if (key != null) {
            if (key.getKeyType() == KeyType.Escape) {
                if (isPaused) {
                    currentState = GameState.PAUSED;
                } else {
                    isPaused = true;
                    // Adicionar lógica para mostrar a tela de pausa, se necessário
                }
            } if (key.getKeyType() == KeyType.Character) {
                switch (key.getCharacter()) {
                    case '1' -> currentState = GameState.PLAYING;
                    case '2' -> currentState = GameState.INSTRUCTIONS;
                }
            }
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                currentState = GameState.QUIT;
            }
            // Adicionar lógica para outras teclas durante o jogo
        }
    }
    public void nextTick(){
        score.addToScore(arena.checkLineCompletition(new RemoveLine()));
        if (nTickCounter == gameSpeed) {
            if (arena.hasHitBottom(piece))
                piece = null;
            else
                piece.forceDown();
            nTickCounter = 0;
        } else {
            nTickCounter++;
        }
    }
    public boolean isPieceNull(){
        if(nextPiece == null && piece == null){
            piece = new Piece(gameScreenWidth/4);
            nextPiece = new Piece(gameScreenWidth/4);
            return true;
        }
        if (piece == null) {
            piece = nextPiece;
            nextPiece = null;
            nextPiece = new Piece(gameScreenWidth/4);
            return true;
        }
        return false;
    }

    public void pressedLeft(){
        if (piece!=null && piece.getPos_x()>0 && arena.canMove(piece.getPos_x()-1, piece))
            piece.moveLeft();
    }
    public void pressedRight(){
        if (piece!=null && piece.getRightPos()<gameScreenWidth/2-1 && arena.canMove(piece.getPos_x()+1, piece))
            piece.moveRight();
    }
    public void pressedDown(){
        if(piece==null) return;

        if(arena.hasHitBottom(piece))
            piece = null;
        else
            piece.forceDown();
        nTickCounter = 0;
    }
    public void pressedUp(){
        if(piece!=null && arena.canRotate(piece)){
            piece.rotate();
        }
    }

    public Piece getPiece() {
        return piece;
    }
    public Piece getNextPiece(){return nextPiece;}
    public Arena getBoard() {
        return arena;
    }

    public boolean gameOver(){return arena.gameOver();}


}