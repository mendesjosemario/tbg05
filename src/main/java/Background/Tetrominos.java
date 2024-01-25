package Background;

public class Tetrominos {
    private boolean[][] shape;
    private int x, y; // Posição do Tetromino na arena

    public static class Tetromino {
        private boolean[][] shape;
        private int x, y; // Posição do Tetromino

        public Tetromino(boolean[][] shape) {
            this.shape = shape;
            this.x = 0; // Posição inicial X
            this.y = 0; // Posição inicial Y
        }

        // Métodos de movimento e rotação...
    }


    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void rotate() {
        // Implementar a lógica de rotação aqui
    }

    // Getters e Setters
}
