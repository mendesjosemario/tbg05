package Background;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TetrominoFactory {
    @Contract(" -> new")
    public static @NotNull Tetrominos.Tetromino createLine() {
        boolean[][] shape = {
                {true, true, true, true}
        };
        return new Tetrominos.Tetromino(shape);
    }

    public static Tetrominos.Tetromino createSquare() {
        boolean[][] shape = {
                {true, true},
                {true, true}
        };
        return new Tetrominos.Tetromino(shape);
    }

    // Adicione métodos para criar outras formas
}