package Background;


import Background.MatrixOperations.RotateMatrix;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private String[][] matrix;
    private int width;
    private int length;
    private boolean isRunning=false;

    public Arena(int width, int length){
        width = width/2;
        this.width = width;
        this.length = length;

        matrix = new String[length][width];

        for (int y = 0; y < length; y++){
            for (int x = 0; x < width; x++){
                matrix[y][x] = "#000000";
            }
        }
    }


    public boolean canMove(int nextPosX, Piece piece) {
        for (int y = 0; y < piece.getMatrix().length; y++) {
            for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                if (piece.getMatrix()[y][x] != "#000000") {
                    if (!matrix[piece.getPos_y()+y][x + nextPosX].equals("#000000")) {
                        System.out.println("Can't move there");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canRotate(Piece piece){
        String[][] tempMatrix = piece.getMatrix().clone();
        tempMatrix = RotateMatrix.execute(tempMatrix);

        for (int y = 0; y < tempMatrix.length; y++) {
            for (int x = 0; x < tempMatrix[y].length; x++) {
                if (tempMatrix[y][x] != "#000000") {
                    try{
                        if (!matrix[piece.getPos_y()+y][piece.getPos_x() + x].equals("#000000")) {
                            System.out.println("Can't rotate there");
                            return false;
                        }
                    }catch(Exception e){
                        return false;
                    }
                }
            }
        }
        return true;


    }

    public boolean hasHitBottom(Piece piece){
        for(int y =0; y<piece.getMatrix().length; y++){
            for (int x = 0; x <piece.getMatrix()[y].length; x++){
                if(piece.getMatrix()[y][x]!="#000000") {
                    if(y+piece.getPos_y()+1 == matrix.length){
                        System.out.println("Chegou ao Fundo");
                        addPiece(piece);
                        return true;
                    }
                    else if(matrix[y+piece.getPos_y()+1][x+ piece.getPos_x()]!="#000000"){
                        System.out.println("Colisão com peça");
                        addPiece(piece);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addPiece(Piece piece){
        for(int y =0; y<piece.getMatrix().length;y++){
            for(int x=0; x<piece.getMatrix()[y].length;x++){
                if(piece.getMatrix()[y][x] != "#000000"){
                    matrix[y+piece.getPos_y()][x+ piece.getPos_x()] = piece.getMatrix()[y][x];
                }
            }
        }

    }

    public int checkLineCompletition(RemoveLine remover){
        int counter = 0;
        for (int y = 0; y < length; y++){
            boolean fullprintedline = true;
            for (int x = 0; x < width; x++){
                if(matrix[y][x] == "#000000"){
                    fullprintedline = false;
                    break;
                }
            }
            if(fullprintedline) {
                counter++;
                matrix = remover.removeLine(y, this.matrix);
                y--;
            }
        }

        return counter;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public void setMatrix(String[][] matrix){
        this.matrix = matrix;
    }

    public boolean gameOver(){
        for (int x = 0; x < width; x++){
            if(matrix[0][x] != "#000000"){
                return true;
            }
        }
        return false;
    }

    public void addTetromino(Tetrominos.Tetromino tetromino) {
        // Adiciona o tetromino na arena
    }

    public void removeTetromino(Tetrominos.Tetromino tetromino) {
        // Remove o tetromino da arena
    }

    public boolean isPositionOccupied(int x, int y) {
        // Verifica se a posição (x, y) está ocupada
        return false;
    }

    public void drawArena(Screen screen) throws IOException {
        TerminalSize size = screen.getTerminalSize();
        int arenaWidth = 60;
        int arenaHeight = 30;

        int startX = (size.getColumns() - arenaWidth) / 2;
        int startY = (size.getRows() - arenaHeight) / 2;

        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);

        // ASCII Art de "TETRIS"
        String[] asciiArt = {
                "   _______   ______   _______   _____    _____    _____ ",
                "   |__   __| |  ____| |__   __| |  __ \\  |_   _|  / ____|",
                "      | |    | |__       | |    | |__) |   | |   | (___    ",
                "      | |    |  __|      | |    |  _  /    | |    \\___ \\ ",
                "      | |    | |____     | |    | | \\ \\   _| |_   ____) |",
                "      |_|    |______|    |_|    |_|  \\_\\ |_____| |_____/"
        };

        for (int i = 0; i < asciiArt.length; i++) {
            textGraphics.putString(startX, startY - asciiArt.length - 1 + i, asciiArt[i]);
        }
        for (int y = 0; y < length; y++){
            for (int x = 0; x < width*2; x+=2){
                screen.setBackgroundColor(TextColor.Factory.fromString(matrix[y][x/2]));
                //TODO REMOVE LINE (on to debug)
                screen.putString(new TerminalPosition(x + GameController.getGameScreenXoffset(), y + GameController.getGameScreenYoffset()), " ");
                //screen.putString(new TerminalPosition(x + ldts.model.Game.getGameScreenXoffset(), y + ldts.model.Game.getGameScreenYoffset()), ' ');
                screen.putString(new TerminalPosition(x+1 + GameController.getGameScreenXoffset(), y + GameController.getGameScreenYoffset()), " ");
            }
        }

        // Desenha a borda da arena
        for (int x = 0; x < arenaWidth; x++) {
            for (int y = 0; y < arenaHeight; y++) {
                if (x == 0 || y == 0 || x == arenaWidth - 1 || y == arenaHeight - 1) {
                    textGraphics.putString(startX + x, startY + y, "||");
                }
            }
        }

        screen.refresh();
    }
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

