package Background;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private boolean[][] arena;
    private boolean isRunning=false;
    public Arena() {
        this.arena = new boolean[30][20];
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

