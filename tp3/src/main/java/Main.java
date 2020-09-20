import java.io.File;
import java.util.ArrayList;

public class Main {
    private static final int[][] logicNumbers = {{1,1},{1,-1},{-1,1},{-1,-1}};

    public static void main(String[] args) {
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        for (Double[] i: aux) {
            for (Double j: i){
                System.out.print(j + " ");
            }
            System.out.println();
        }
        return;
    }
}
