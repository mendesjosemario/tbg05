package Background;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class GameTest {

    private Game game;
    private TerminalHandler terminalHandler;

    @BeforeEach
    public void setUp() throws IOException {
        // Inicializar terminalHandler com um stub ou mock simplificado
        // O TerminalHandler pode precisar ser adaptado para permitir testes unitários
        terminalHandler = new TerminalHandler(); // Substitua com a inicialização adequada
        game = new Game(terminalHandler);
    }

    @Test
    public void testNextTick() {
        int initialScore = game.getScore().getScore();
        game.nextTick();
        assertTrue(game.getScore().getScore() >= initialScore);
    }

    @Test
    public void testIsPieceNull() {
        game.getArena().setModel(null);
        assertTrue(game.isPieceNull());
    }

    @Test
    public void testPressedLeft() {
        int initialX = game.getPiece().getPos_x();
        game.pressedLeft();
        assertTrue(game.getPiece().getPos_x() <= initialX);
    }

    @Test
    public void testPressedRight() {
        int initialX = game.getPiece().getPos_x();
        game.pressedRight();
        assertTrue(game.getPiece().getPos_x() >= initialX);
    }

    @Test
    public void testPressedDown() {
        int initialY = game.getPiece().getPos_y();
        game.pressedDown();
        assertTrue(game.getPiece().getPos_y() >= initialY);
    }

    @Test
    public void testPressedUp() {
        int initialRotation = game.getPiece().getMatrix().hashCode();
        game.pressedUp();
        int newRotation = game.getPiece().getMatrix().hashCode();
        assertNotEquals(initialRotation, newRotation);
    }

    @Test
    public void testGameOver() {
        assertFalse(game.gameOver());
        // Configurar o cenário para fim de jogo e testar novamente
    }


}
