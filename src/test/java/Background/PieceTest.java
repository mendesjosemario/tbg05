package Background;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    private Piece piece;
    private Background.PieceStates.PieceState state;

    @BeforeEach
    public void setUp() {
        // Inicializa a peça com um estado específico para testar
        state = new Background.PieceStates.LinePiece();
        piece = new Piece(0);
        piece.setState(state);
    }

    @Test
    public void testMoveLeft() {
        int initialX = piece.getPos_x();
        piece.moveLeft();
        assertEquals(initialX - 1, piece.getPos_x(), "MoveLeft deveria diminuir a posição x em 1");
    }

    @Test
    public void testMoveRight() {
        int initialX = piece.getPos_x();
        piece.moveRight();
        assertEquals(initialX + 1, piece.getPos_x(), "MoveRight deveria aumentar a posição x em 1");
    }

    @Test
    public void testForceDown() {
        int initialY = piece.getPos_y();
        piece.forceDown();
        assertEquals(initialY + 1, piece.getPos_y(), "ForceDown deveria aumentar a posição y em 1");
    }

    @Test
    public void testRotate() {
        String[][] initialMatrix = piece.getMatrix().clone();
        piece.rotate();

    }

    @Test
    public void testIsBlockAt() {

    }
}
