package Background;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {

    private Arena arena;
    private Piece piece;

    @BeforeEach
    public void setUp() {
        arena = new Arena(10, 20, 2, 2);
        piece = new Piece(5);
    }

    @Test
    public void testCanMove() {
        assertTrue(arena.canMove(1, piece)); // Posição válida para movimento

        piece.pos_x = arena.getWidth() - piece.getMatrix()[0].length;
        assertFalse(arena.canMove(piece.pos_x + 1, piece)); // Movimento além do limite
    }


    @Test
    public void testCanRotate() {
        piece.pos_x = 1; // Garantir que a peça esteja em uma posição que permita a rotação
        piece.pos_y = 1;
        assertTrue(arena.canRotate(piece));

        piece.pos_x = arena.getWidth() - piece.getMatrix()[0].length;
        assertFalse(arena.canRotate(piece));
    }


    @Test
    public void testHasHitBottom() {
        piece.pos_y = arena.getLength() - piece.getLength();
        assertTrue(arena.hasHitBottom(piece));
        piece.pos_y = 0;
        assertFalse(arena.hasHitBottom(piece));
    }

    @Test
    public void testAddPiece() {
        piece.pos_y = arena.getLength() - piece.getLength();
        arena.addPiece(piece);
        for (int y = 0; y < piece.getLength(); y++) {
            for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                if (!piece.getMatrix()[y][x].equals("#000000")) {
                    assertEquals(piece.getMatrix()[y][x], arena.getMatrix()[piece.getPos_y() + y][piece.getPos_x() + x]);
                }
            }
        }
    }

    @Test
    public void testCheckLineCompletion() {
        RemoveLine remover = new RemoveLine();
        // Preencha uma linha completa para teste
        for (int x = 0; x < arena.getWidth(); x++) {
            arena.getMatrix()[arena.getLength() - 1][x] = "#FFFFFF";
        }
        assertEquals(1, arena.checkLineCompletition(remover));
    }

    @Test
    public void testGameOver() {
        assertFalse(arena.gameOver());
        for (int x = 0; x < arena.getWidth(); x++) {
            arena.getMatrix()[0][x] = "#FFFFFF";
        }
        assertTrue(arena.gameOver());
    }

    @Test
    public void testUpdate() {
        arena.setModel(piece);
        piece.pos_y = 0; // Configura a peça para uma posição onde ela pode se mover para baixo
        arena.setRunning(true);
        arena.update();
        assertEquals(1, piece.getPos_y()); // Espera-se que a peça tenha se movido para baixo
    }


    @Test
    public void testSetModel() {
        Piece newPiece = new Piece(5);
        arena.setModel(newPiece);
        assertEquals(newPiece, arena.getModel());
    }

    @Test
    public void testSetRunning() {
        arena.setRunning(true);
        assertTrue(arena.isRunning());
        arena.setRunning(false);
        assertFalse(arena.isRunning());
    }
}
