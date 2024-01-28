package Background;

import Background.PieceStates.PieceState;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class NextPiece {


    private Piece model;
    private String[][] matrix;
    private PieceState state;
    private int pos_x;
    private int pos_y;

    public NextPiece(Piece model) {
        this.model = model;
        this.matrix = model.getMatrix();
        this.state = model.getState();
    }

    public void update() {
        this.matrix = model.getMatrix();
        this.state = model.getState();
    }

    public void draw(TextGraphics screenGraphics) {
        // Desenha o retângulo ao redor da próxima peça
        screenGraphics.setBackgroundColor(TextColor.Factory.fromString("#33FFFF"));
        screenGraphics.fillRectangle(new TerminalPosition(45, 7), new TerminalSize(20, 10), ' ');

        // Desenha o texto "NEXT PIECE"
        drawText(screenGraphics, 49, 5, "NEXT PIECE:", "#FFFFFF");

        // Desenha a próxima peça
        Piece piece = getModel();
        if (piece != null) {
            String[][] matrix = piece.getMatrix();
            PieceState state = piece.getState();

            screenGraphics.setBackgroundColor(TextColor.Factory.fromString(state.getColor()));

            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length * 2; x += 2) {
                    if (!"#FFFF33".equals(matrix[y][x / 2])) {
                        screenGraphics.putString(new TerminalPosition(52 + x, 11 + y), " ");
                        screenGraphics.putString(new TerminalPosition(52 + x + 1, 11 + y), " ");
                    }
                }
            }
        }
    }
    public Piece getModel() {
        return model;
    }

    public void setModel(Piece model) {
        this.model = model;
    }
    private void drawText(TextGraphics textGraphics, int col, int row, String text, String color) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        textGraphics.enableModifiers(SGR.BOLD);
        textGraphics.putString(col,row,text);
    }
}