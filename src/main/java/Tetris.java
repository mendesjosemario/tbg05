import Background.Game;
import Background.TerminalHandler;

import java.io.IOException;



public class Tetris {

    public static void main(String[] args) throws IOException {
        TerminalHandler terminalHandler = new TerminalHandler();
        Game game = new Game(terminalHandler);
        game.start();
    }
}