import java.io.File;
import java.util.ArrayList;

public class Main {
    private static final int[][] logicNumbers = {{1,1},{1,-1},{-1,1},{-1,-1}};

    public static void main(String[] args) {
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        File file2 = new File("TP3-ej2-Salida-deseada.txt");
        ArrayList<Double[]> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2, 1);

        System.out.println("\n=======\nAND:");
        double[][] inputs = {{-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
        double[] outputs = {-1, -1, -1, 1};
        int aux1 = 0;
        Perceptron p = new Perceptron(inputs[0],0, outputs[0]);
        p.execute(true);
        int j = 4;
        while ( --j != 0) {
            for (int i = 1; i < outputs.length; i++) {
                p.newValues(inputs[i],0, outputs[i]);
                p.execute(true);
            }
        }

        for (int i = 0; i < outputs.length; i++) {
            p.newValues(inputs[i],0, outputs[i]);
            aux1 = p.execute(false);
            p.print();
            System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[i] + "\n");
        }


        System.out.println("\n\n=======\nXOR:");
        inputs = new double[][] {{-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
        outputs = new double[] {1, 1, -1, -1};

        p = new Perceptron(inputs[0],0, outputs[0]);
        aux1 = p.execute(true);
        p.print();
        System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[0] + "\n");

        j = 3;
        while ( --j != 0) {
            for (int i = 1; i < outputs.length; i++) {
                p.newValues(inputs[i],0, outputs[i]);
                p.execute(true);
            }
        }

        for (int i = 1; i < outputs.length; i++) {
            p.newValues(inputs[i],0, outputs[i]);
            aux1 = p.execute(true);
            p.print();
            System.out.println("\n"+ "Output " + aux1 + " Expected "+  outputs[i] + "\n");
        }

        return;

    }
}
