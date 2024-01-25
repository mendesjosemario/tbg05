package Background;


import java.util.Random;


public class Piece {
    public int pos_x;
    public int pos_y;
    private Background.PieceStates.PieceState state;
    private String[][] matrix;

    public Piece(int pos_x){
        this.pos_x = pos_x;
        pos_y = 0;

        getRandomState();

        matrix = state.getMatrix();
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

}