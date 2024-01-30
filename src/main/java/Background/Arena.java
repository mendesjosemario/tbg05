package Background;


import Background.MatrixOperations.RotateMatrix;
import com.googlecode.lanterna.TerminalPosition;
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

    public Arena(int width, int length) {
        this.model = new Piece(width/2);
        this.width = width ;  // Remova a linha "width = width/2;"
        this.length = length;

        matrix = new String[length][width];

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = "#000000";
            }
        }
    }


    public boolean hasHitTop(Piece piece) {
        if (model!=null && hasHitBottom(model)){
            for (int y = 0; y < piece.getMatrix().length; y++) {
                for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                    if (!piece.getMatrix()[y][x].equals("#000000")) {
                        int posY = piece.getPos_y() + y;
                        // Se a posição Y da parte da peça estiver na linha superior (y = 0)
                        if (posY <= 2) {
                            System.out.println("Tocou no topo");
                            isRunning=false;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }



    public boolean canMove(int nextPosX, Piece piece) {
        for (int y = 0; y < piece.getMatrix().length; y++) {
            for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                if (piece.getMatrix()[y][x] != "#000000") {
                    int targetX = x + nextPosX;
                    int targetY = piece.getPos_y() + y;
                    if (targetX < 4 || targetX >= width -1 || targetY < 2 || targetY >= length -1 || matrix[targetY][targetX].equals("#")) {
                        System.out.println("Can't move there");
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean canRotate(Piece piece){
        String[][] tempMatrix = RotateMatrix.execute(piece.getMatrix());

        for (int y = 0; y < tempMatrix.length; y++) {
            for (int x = 0; x < tempMatrix[y].length; x++) {
                if (tempMatrix[y][x] != "#000000") {
                    int targetX = piece.getPos_x() + x;
                    int targetY = piece.getPos_y() + y;
                    if (targetX < 0 || targetX >= width || targetY < 0 || targetY >= length || matrix[targetY][targetX].equals("#")) {
                        System.out.println("Can't rotate there");
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean hasHitBottom(Piece piece) {
        if (piece !=null){
            for (int y = piece.getMatrix().length - 1; y >= 0; y--) {
                for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                    if (piece.getMatrix()[y][x] != "#000000") {
                        int targetY = piece.getPos_y() + y + 1;
                        if (targetY >= length -1 || (!matrix[targetY][x + piece.getPos_x()].equals("#000000") && !matrix[targetY][x + piece.getPos_x()].equals("#"))) {
                            System.out.println("Chegou ao Fundo");
                            addPiece(piece);
                            model = null;
                            return true;
                        }
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
        if (hasHitTop(model)){
            return true;
        }
        return false;
    }



    public boolean isPositionOccupied(int x, int y) {
        // Verifica se a posição (x, y) está ocupada
        return false;
    }
    public void update() {
        if (isRunning && !hasHitBottom(model) && model!=null) {
            model.forceDown();
        }
    }

    public void drawArena(Screen screen) throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();

        // Desenha o contorno da arena
        drawArenaBorder(textGraphics);

        // Atualiza o estado da arena e da peça
        update();

        // Desenha as peças já colocadas na matriz
        drawPlacedPieces(textGraphics);

        // Desenha a peça em movimento
        drawMovingPiece(textGraphics);

        // Atualiza a tela
        screen.refresh();
    }

    private void drawArenaBorder(TextGraphics textGraphics) {
        // Aplica um offset de 1 para os delimitadores
        int offsetX = 4;
        int offsetY = 2;

        // Calcula a posição do canto superior esquerdo da arena com o offset
        int arenaX = offsetX;
        int arenaY = offsetY;

        // Desenha um retângulo ao redor da área de jogo com o offset aplicado
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY - 1),
                new TerminalPosition(arenaX + width - offsetX - 1, arenaY - 1), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY),
                new TerminalPosition(arenaX - 1, arenaY + length - offsetY - 1), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX + width - offsetX - 1, arenaY),
                new TerminalPosition(arenaX + width - offsetX - 1, arenaY + length - offsetY - 1), '#');
        textGraphics.drawLine(new TerminalPosition(arenaX - 1, arenaY + length - offsetY - 1),
                new TerminalPosition(arenaX + width - offsetX - 1, arenaY + length - offsetY - 1), '#');
    }





    private void drawMovingPiece(TextGraphics textGraphics) {
        if (model != null) {
            String[][] matrix = model.getMatrix();
            int posX = model.getPos_x();
            int posY = model.getPos_y();

            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    if (!"#000000".equals(matrix[y][x])) {
                        textGraphics.setBackgroundColor(TextColor.Factory.fromString(matrix[y][x]));
                        textGraphics.putString(new TerminalPosition(posX + x, posY + y), " ");
                    }
                }
            }
        }
    }


    private void drawPlacedPieces(TextGraphics textGraphics) {
        String[][] matrix = getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (!"#000000".equals(matrix[y][x])) {
                    textGraphics.setBackgroundColor(TextColor.Factory.fromString(matrix[y][x]));
                    textGraphics.putString(new TerminalPosition(x, y), " ");
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

