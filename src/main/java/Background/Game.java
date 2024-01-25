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
    private final TerminalHandler terminalHandler;
    private Screen screen;
    private GameState currentState ;
    private boolean isPaused = false;  // Rastreia se o jogo está pausado
    protected Piece piece;
    protected  Piece nextPiece;
    protected static final int gameScreenXoffset = 6;
    protected static final int gameScreenYoffset = 2;
    protected static final int gameScreenWidth = 26;
    protected static final int gameScreenLength = 26;
    protected int gameSpeed = 5;  //smaller is faster, ticks needed to force piece down
    protected int nTickCounter = 0;
    protected Score score;
    private Arena arena;
    public Game(TerminalHandler terminalHandler) throws IOException {
        this.terminalHandler = terminalHandler;
        Terminal terminal = terminalHandler.getTerminal();
        this.arena = new Arena(gameScreenWidth, gameScreenLength);
        this.screen = new TerminalScreen(terminal);
        currentState = GameState.MENU;
        screen.startScreen();
    }

    public void start() throws IOException {
        while (true) {
            switch (currentState) {
                case MENU:
                    screen.clear();
                    drawMenu();
                    handleGameplayInput();
                    break;
                case INSTRUCTIONS:
                    screen.clear();
                    drawInstructions();
                    handleGameplayInput();
                    break;
                case PLAYING:
                    screen.clear();
                    arena.setRunning(true);
                    arena.drawArena(screen);
                    handleGameplayInput();
                    break;
                case PAUSED:
                    screen.clear();
                    currentState=GameState.PAUSED;
                    drawMenu();
                    handleGameplayInput();
                    break;
            }
            if (currentState == GameState.QUIT) {
                arena.setRunning(false);
                break; // Se o jogo estiver pausado (estado PAUSED), sai do loop
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
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                // Lógica para mover o tetromino para esquerda
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                // Lógica para mover o tetromino para direita
            } if (key.getKeyType() == KeyType.Character) {
                switch (key.getCharacter()) {
                    case '1':
                        currentState = GameState.PLAYING;
                        break;
                    case '2':
                        currentState = GameState.INSTRUCTIONS;
                        break;
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
    public Score getScore() { return score; }
    public int getGameSpeed() {
        return gameSpeed;
    }
    public int getTickCount() {
        return nTickCounter;
    }
    public boolean gameOver(){return arena.gameOver();}
    public void increaseGameSpeed(){
        if(gameSpeed>1){
            gameSpeed--;
        }
    }
    public void decreaseGameSpeed(){
        if(gameSpeed<10){
            gameSpeed++;
        }
    }

}