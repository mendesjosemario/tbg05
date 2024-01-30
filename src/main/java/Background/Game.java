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


    public Screen getScreen() {
        return screen;
    }

    private final Screen screen;
    private GameState currentState ;
    private boolean isPaused = false;  // Rastreia se o jogo está pausado
    private  NextPiece nextPiece;
    private  final int gameScreenXoffset = 2;
    private  final int gameScreenYoffset = 2;
    private  final int gameScreenWidth = 80;
    private final int gameScreenLength = 40;
    private final int gameSpeed = 160;  //Temp de espera ate a proxima peca cair
    private int nTickCounter = 0;

    public Score getScore() {
        return score;
    }

    private final Score score;

    public Arena getArena() {
        return arena;
    }

    private Arena arena;
    public Game(TerminalHandler terminalHandler) throws IOException {
        this.terminalHandler = terminalHandler;
        Terminal terminal = terminalHandler.getTerminal();
        this.arena = new Arena(30, 30);
        this.screen = new TerminalScreen(terminal);
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
                    gameOver();
                    arena.setRunning(true);
                    arena.update();
                    score.draw(screen.newTextGraphics());
                    if (nextPiece != null){
                        nextPiece.draw(screen.newTextGraphics());
                    }
                    arena.drawArena(screen);
                    screen.refresh();
                    if(isPieceNull()) continue;
                    nextTick();  // Adicione esta linha para avançar o jogo
                    handleGameplayInput();
                }
                case GAME_OVER -> {
                    screen.clear();
                    drawGameOverMenu();
                    handleGameOverInput();
                }
                case PAUSED -> {
                    screen.clear();
                    currentState = GameState.PAUSED;
                    drawMenu();
                    handleGameplayInput();
                }
            }
            if (currentState == GameState.QUIT) {
                System.exit(0);
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
        textGraphics.putString(30, 5, "TETRIS\n", SGR.BOLD);
        textGraphics.putString(30, 7, "1. Jogar");
        textGraphics.putString(30, 8, "2. Instrucoes");
        textGraphics.putString(30, 9, "Q. Sair");
        screen.refresh();
    }

    private void drawGameOverMenu() throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(30, 5, "GAME OVER", SGR.BOLD);
        textGraphics.putString(30, 7, "R. Restart Game");
        textGraphics.putString(30, 8, "Q. Quit Game");
        screen.refresh();
    }
    private void handleGameOverInput() throws IOException {
        KeyStroke key = terminalHandler.getKeyPress();
        if (key != null && key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'r', 'R' -> restartGame();
                case 'q', 'Q' -> quitGame();
            }
        }
    }
    private void restartGame() throws IOException {
        arena = new Arena(30 , 30); // Reinicia a arena
        score.setScore(0); // Reinicia a pontuação, supondo que você tenha um método reset
        currentState = GameState.PLAYING; // Muda o estado do jogo para jogar
    }
    private void quitGame() {
        currentState = GameState.QUIT;
    }


    public void update() {
        arena.update();
    }

    private void drawInstructions() throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(10, 5,  "             Instrucoes do Jogo\n", SGR.BOLD);
        textGraphics.putString(10, 7,  "Seta Esquerda:          Mover para esquerda");
        textGraphics.putString(10, 8,  "Seta Direita:           Mover para direita");
        textGraphics.putString(10, 9,  "Seta Cima:              Rotacionar");
        textGraphics.putString(10, 10, "Seta Baixo:             Dobrar velocidade");
        textGraphics.putString(10, 11, "Esc:                    Voltar ao menu");
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
                }
            } if (key.getKeyType() == KeyType.Character) {
                switch (key.getCharacter()) {
                    case '1' -> currentState = GameState.PLAYING;
                    case '2' -> currentState = GameState.INSTRUCTIONS;
                }
            }
            if (key.getKeyType() == KeyType.ArrowDown) {
                pressedDown();
            }
            if (key.getKeyType() == KeyType.ArrowLeft) {
                pressedLeft();
            }
            if (key.getKeyType() == KeyType.ArrowRight) {
                pressedRight();
            }
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == ' ') {
                pressedUp();
            }
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                currentState = GameState.QUIT;
            }
            if (key.getKeyType() == KeyType.EOF) {
                arena.setRunning(false);
                System.exit(0);
            }
            // Adicionar lógica para outras teclas durante o jogo
        }
    }
    public void nextTick() {
        score.addToScore(arena.checkLineCompletition(new RemoveLine()));

        if (nTickCounter == gameSpeed) {
            if (arena.hasHitBottom(arena.getModel())) {
                arena.setModel(createNewPiece()); // Criar uma nova peça
            } else {
                arena.getModel().forceDown();
            }
            nTickCounter = 0;
        } else {
            nTickCounter++;
        }
    }
    private Piece createNewPiece() {
        int initialX = (arena.getWidth()) / 2;
        arena.setModel(new Piece(initialX));
        return arena.getModel();
    }
    public boolean isPieceNull(){
        if(nextPiece == null && arena.getModel() == null){
            arena.setModel(new Piece(gameScreenWidth/4));
            nextPiece = new NextPiece(new Piece(gameScreenWidth/4));
            return true;
        }
        if (arena.getModel() == null) {
            arena.setModel(nextPiece.getModel());
            nextPiece = null;
            nextPiece = new NextPiece(new Piece(gameScreenWidth/4));
            return true;
        }
        return false;
    }

    public void pressedLeft(){
        if (arena.getModel()!=null && arena.getModel().getPos_x()>0 && arena.canMove(arena.getModel().getPos_x()-1, arena.getModel()))
            arena.getModel().moveLeft();
    }
    public void pressedRight(){
        if (arena.getModel() != null &&
                arena.getModel().getPos_x() + arena.getModel().getMatrix()[0].length < arena.getWidth() &&
                arena.canMove(arena.getModel().getPos_x() + 1, arena.getModel())) {
            arena.getModel().moveRight();
        }

    }
    public int getGameScreenLength() {
        return gameScreenLength;
    }

    public int getGameScreenXoffset() {
        return gameScreenXoffset;
    }

    public int getGameScreenYoffset() {
        return gameScreenYoffset;
    }

    public int getGameScreenWidth() {
        return gameScreenWidth;
    }

    public void pressedDown(){
        if(arena.getModel()==null) return;

        if(arena.hasHitBottom(arena.getModel()))
            arena.setModel(null);
        else
            arena.getModel().moveDown();
        nTickCounter = 0;
    }
    public void pressedUp(){
        if(arena.getModel()!=null && arena.canRotate(arena.getModel())){
            arena.getModel().rotate();
        }
    }


    public Piece getPiece() {
        return arena.getModel();
    }
    public Piece getNextPiece(){return nextPiece.getModel();}
    public Arena getBoard() {
        return arena;
    }

    public void  gameOver(){
        if (arena.gameOver()) {
            currentState = GameState.GAME_OVER;
        }
    }


}