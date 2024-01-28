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

    public void draw(TextGraphics screenGraphics){
        update();

        screenGraphics.setBackgroundColor(TextColor.Factory.fromString("#3A3A3A"));
        //screenGraphics.putString(45, 2, " _  _ _____  _______   ");
        //screenGraphics.putString(45, 3, "| \\| | __\\ \\/ /_   _|");
        //screenGraphics.putString(45, 4, "| .` | _| >  <  | | ");
        //screenGraphics.putString(45, 5, "|_|\\_|___/_/\\_\\ |_|");
        drawText(screenGraphics,49,5,"NEXT PIECE:","#FFFFFF");
        screenGraphics.setBackgroundColor(TextColor.Factory.fromString("#33FFFF"));
        screenGraphics.fillRectangle(new TerminalPosition(45, 7), new TerminalSize(20, 10), ' ');

        screenGraphics.setBackgroundColor(TextColor.Factory.fromString(state.getColor()));

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length * 2; x += 2) {
                if (matrix[y][x / 2] != "#FFFF33") {
                    screenGraphics.putString(new TerminalPosition(52+x , 11+y)," ");
                    screenGraphics.putString(new TerminalPosition(52+x+1, 11+y), " ");                }
            }
        }
    }

    private void drawText(TextGraphics textGraphics, int col, int row, String text, String color) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        textGraphics.enableModifiers(SGR.BOLD);
        textGraphics.putString(col,row,text);
    }
}