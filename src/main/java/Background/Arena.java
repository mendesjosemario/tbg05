package Background;


import Background.MatrixOperations.RotateMatrix;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {

    private Piece model;
    private String[][] matrix;
    private int width;
    private int length;
    private boolean isRunning=false;

    public Arena(int width, int length, int gameOffsetX, int gameOfsetY) {
        this.model = new Piece(50);
        this.width = width ;  // Remova a linha "width = width/2;"
        this.length = length;

        matrix = new String[length][width];

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
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

    public boolean hasHitBottom(Piece piece) {
        for (int y = piece.getMatrix().length - 1; y >= 0; y--) {
            for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                if (piece.getMatrix()[y][x] != "#000000") {
                    int posY = piece.getPos_y() + y;
                    if (posY + 1 == matrix.length || matrix[posY + 1][x + piece.getPos_x()] != "#000000") {
                        System.out.println("Chegou ao Fundo");
                        addPiece(piece);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void addPiece(Piece piece){
        for(int y = 0; y < piece.getMatrix().length; y++){
            for(int x = 0; x < piece.getMatrix()[y].length; x++){
                int targetY = y + piece.getPos_y();
                int targetX = x + piece.getPos_x();

                // Verifica se as posições calculadas estão dentro dos limites da matriz
                if(targetY >= 0 && targetY < matrix.length && targetX >= 0 && targetX < matrix[targetY].length){
                    if(piece.getMatrix()[y][x] != "#00000"){
                        matrix[targetY][targetX] = piece.getMatrix()[y][x];
                    }
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



    public boolean isPositionOccupied(int x, int y) {
        // Verifica se a posição (x, y) está ocupada
        return false;
    }
    public void update() {
        if (isRunning && !hasHitBottom(model)) {
            model.forceDown();
        }
    }

    public void drawArena(Screen screen, int gameScreenXoffset, int gameScreenWidth, int gameScreenYoffset, int gameScreenLength) throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();

        // Desenha o contorno da arena
        drawArenaBorder(textGraphics, gameScreenWidth, gameScreenYoffset, gameScreenLength);

        // Atualiza o estado da arena e da peça
        update();

        // Desenha as peças já colocadas na matriz
        drawPlacedPieces(textGraphics, gameScreenXoffset, gameScreenYoffset);

        // Desenha a peça em movimento
        drawMovingPiece(textGraphics, gameScreenWidth ,gameScreenXoffset, gameScreenYoffset);

        // Atualiza a tela
        screen.refresh();
    }
    private void drawArenaBorder(TextGraphics textGraphics, int gameScreenWidth, int gameScreenYoffset, int gameScreenLength) {
        // Calcula a posição do canto superior esquerdo da arena
        int arenaX = (gameScreenWidth - width) / 2;
        int arenaY = gameScreenYoffset;

        // Calcula a largura e a altura da arena considerando o tamanho do border
        int arenaWidth = width;
        int arenaHeight = gameScreenLength;

        // Desenha um retângulo ao redor da área de jogo
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY - 1),
                new TerminalPosition(arenaX + arenaWidth, arenaY - 1), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY),
                new TerminalPosition(arenaX - 1, arenaY + arenaHeight), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX + arenaWidth, arenaY),
                new TerminalPosition(arenaX + arenaWidth, arenaY + arenaHeight), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY + arenaHeight),
                new TerminalPosition(arenaX + arenaWidth, arenaY + arenaHeight), '#');
    }


    private void drawMovingPiece(TextGraphics textGraphics, int gameScreenWidth, int gameScreenXoffset, int gameScreenYoffset) {
        if (model != null) {
            String[][] matrix = model.getMatrix();
            int posX = model.getPos_x();
            int posY = model.getPos_y();

            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    if (!"#000000".equals(matrix[y][x])) {
                        textGraphics.setBackgroundColor(TextColor.Factory.fromString(matrix[y][x]));
                        // Calcular a posição centralizada no eixo X
                        int centerX = gameScreenXoffset + (gameScreenWidth - width) / 2 + posX;
                        textGraphics.putString(new TerminalPosition(centerX + x, gameScreenYoffset + posY + y), " ");
                    }
                }
            }
        }
    }

    private void drawPlacedPieces(TextGraphics textGraphics, int gameScreenXoffset, int gameScreenYoffset) {
        String[][] matrix = getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (!"#000000".equals(matrix[y][x])) {
                    textGraphics.setBackgroundColor(TextColor.Factory.fromString(matrix[y][x]));
                    textGraphics.putString(new TerminalPosition(gameScreenXoffset + x, gameScreenYoffset + y), " ");
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
    public Piece getModel() {
        return model;
    }

    public void setModel(Piece model) {
        this.model = model;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

