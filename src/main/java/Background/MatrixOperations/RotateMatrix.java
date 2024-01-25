package Background.MatrixOperations;

public class RotateMatrix {
    public static String[][] execute(String[][] matrix) {
        String[][] rotatedMatrix = new String[matrix[0].length][matrix.length];
        int column = matrix.length - 1;
        for (String[] line : matrix) {
            for (int i = 0; i < line.length; i++) {
                rotatedMatrix[i][column] = line[i];
            }
            column--;
        }
        return rotatedMatrix;
    }
}
