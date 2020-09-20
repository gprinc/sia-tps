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

        System.out.println("\n=======\nAND:");
        double[][] inputs = {{(1.0 * -1), 1}, {1, (1.0 * -1)}, {(1.0 * -1), (1.0 * -1)}, {1, 1}};
        double[] outputs = {(1.0 * -1), (1.0 * -1), (1.0 * -1), 1};
        int aux1 = 0;
        Perceptron p = new Perceptron(inputs[0],0, outputs[0]);
        aux1 = p.execute();
        p.print();
        System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[0] + "\n");

        for (int i = 1; i < outputs.length; i++) {
            p.newValues(inputs[i],0, outputs[i]);
            aux1 = p.execute();
            p.print();
            System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[i] + "\n");

        }

        System.out.println("\n\n=======\nXOR:");
        inputs = new double[][] {{(1.0 * -1), 1}, {1, (1.0 * -1)}, {(1.0 * -1), (1.0 * -1)}, {1, 1}};
        outputs = new double[] {1, 1, (1.0 * -1), (1.0 * -1)};

        p = new Perceptron(inputs[0],0, outputs[0]);
        aux1 = p.execute();
        p.print();
        System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[0] + "\n");

        for (int i = 1; i < outputs.length; i++) {
            p.newValues(inputs[i],0, outputs[i]);
            aux1 = p.execute();
            p.print();
            System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[i] + "\n");
        }

        return;
    }
}
