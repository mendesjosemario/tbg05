package Background;

import Background.PieceStates.PieceState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NextPieceTest {

    private NextPiece nextPiece;
    private Piece model;

    @BeforeEach
    public void setUp() {
        // Inicialize a peça modelo com um estado específico para o teste
        model = new Piece(0); // Use um construtor adequado para Piece
        // Defina um estado específico, se necessário
        nextPiece = new NextPiece(model);
    }

    @Test
    public void testModelManipulation() {
        // Teste setModel e getModel
        Piece newModel = new Piece(1); // Novo modelo para teste
        nextPiece.setModel(newModel);
        assertEquals(newModel, nextPiece.getModel(), "setModel deve atualizar o modelo da peça");
    }

    @Test
    public void testUpdate() {
        // Atualize o estado do modelo e verifique se o NextPiece reflete as mudanças
        PieceState newState = new Background.PieceStates.LinePiece(); // Novo estado para teste
        model.setState(newState);
        nextPiece.update();
        assertEquals(newState, nextPiece.getModel().getState(), "update deve refletir o novo estado do modelo");
    }
}
