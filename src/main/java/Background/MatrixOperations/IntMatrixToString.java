package Background.MatrixOperations;

public class IntMatrixToString {
    public static String[][] convert(int[][] matrix, String color){
        String[][] pieceMatrix = new String[matrix.length][matrix[0].length];

        for (int y = 0; y < matrix.length; y++){
            for (int x = 0; x < matrix[y].length; x++){
                if(matrix[y][x]==1) {
                    pieceMatrix[y][x] = color;
                }else{
                    pieceMatrix[y][x] = "#000000";
                }

            }
        }
        return pieceMatrix;
    }
}
