package Background;


import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;


public class Piece {
    public int pos_x;
    public int pos_y;
    private Background.PieceStates.PieceState state;
    private String[][] matrix;

    public Piece(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;

        getRandomState();

        matrix = state.getMatrix();
    }

    public void update() {
        this.matrix = getMatrix();
        this.state = getState();
        this.pos_x = getPos_x();
        this.pos_y = getPos_y();
    }

    public void draw(TextGraphics screen, GameController gameController){
        update();
        screen.setBackgroundColor(TextColor.Factory.fromString(state.getColor()));

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length * 2; x += 2) {
                if (!"#000000".equals(matrix[y][x / 2])){
                    screen.putString(new TerminalPosition(pos_x * 2 + x + gameController.getGameScreenXoffset(), pos_y + y + gameController.getGameScreenYoffset()), " ");
                    screen.putString(new TerminalPosition(pos_x * 2 + x + 1 + gameController.getGameScreenXoffset(), pos_y + y + gameController.getGameScreenYoffset()), " ");
                }
            }
        }
    }
    private void getRandomState(){
        Random random = new Random();
        int x = random.nextInt(7);
        Background.PieceStates.PieceState[] states = {new Background.PieceStates.JPiece(), new Background.PieceStates.LinePiece(), new Background.PieceStates.LPiece(), new Background.PieceStates.SPiece(), new Background.PieceStates.SquarePiece(), new Background.PieceStates.TPiece(), new Background.PieceStates.ZPiece()};
        state = states[x];
    }

    public void rotate(){
        matrix = Background.MatrixOperations.RotateMatrix.execute(matrix);
    }

    public void moveLeft(){ pos_x-=1; }
    public void moveRight(){ pos_x+=1; }
    public void forceDown(){ pos_y++; }

    public int getRightPos(){
        return pos_x + matrix[0].length -1;
    }
    public int getPos_x() {
        return pos_x;
    }
    public int getPos_y() {
        return pos_y;
    }
    public String[][] getMatrix() {
        return matrix;
    }
    public void setState(Background.PieceStates.PieceState state) {
        this.state = state;
        matrix = state.getMatrix();
    }
    public Background.PieceStates.PieceState getState() {
        return state;
    }

    public int getLength() {
        return matrix.length;
    }
    public boolean isBlockAt(int x, int y) {
        if (x >= 0 && x < matrix[0].length && y >= 0 && y < matrix.length) {
            // Verifica se há um bloco na posição (x, y) da matriz da peça
            return !"#000000".equals(matrix[y][x]);
        }
        return false; // Fora dos limites da matriz da peça
    }


}