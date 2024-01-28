package Background;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveLineTest {

    @Test
    public void testRemoveLine() {
        String[][] matrix = {
                {"#FFFFFF", "#FFFFFF", "#FFFFFF"}, // Linha 0
                {"#FF0000", "#00FF00", "#0000FF"}, // Linha 1
                {"#FFFF00", "#FF00FF", "#00FFFF"}  // Linha 2
        };

        RemoveLine removeLine = new RemoveLine();
        matrix = removeLine.removeLine(2, matrix);

        // Verifica se a linha 0 foi definida para preto
        assertArrayEquals(new String[]{"#000000", "#000000", "#000000"}, matrix[0], "A linha superior deveria ser preta");

        // Verifica se a linha 1 agora contém o que era a linha 0
        assertArrayEquals(new String[]{"#FFFFFF", "#FFFFFF", "#FFFFFF"}, matrix[1], "A linha 1 deveria ter as cores da linha 0 anterior");

        // Verifica se a linha 2 agora contém o que era a linha 1
        assertArrayEquals(new String[]{"#FF0000", "#00FF00", "#0000FF"}, matrix[2], "A linha 2 deveria ter as cores da linha 1 anterior");
    }
}
