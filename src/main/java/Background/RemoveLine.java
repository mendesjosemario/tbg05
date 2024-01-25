package Background;

public class RemoveLine{
    public String[][] removeLine(int y, String[][] matrix) {
        for(int line = y; line > 0; line--){
            matrix[line]= matrix[line-1].clone();
        }
        for(int column=0; column<matrix[0].length;column++){
            matrix[0][column] = "#000000";
        }
        return matrix;
    }
}
