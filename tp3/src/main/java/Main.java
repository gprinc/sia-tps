import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    private static final double[][] logicNumbers = {{1,1,-1},{1,-1,-1},{-1,1,-1},{-1,-1,-1}};
    private static final double[] AND_OUTPUT = {1, -1, -1, -1};
    private static final double[] XOR_OUTPUT = {-1, 1, 1, -1};

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
        LinealPerceptron perceptron = new LinealPerceptron(logicNumbers, AND_OUTPUT);
        perceptron.train();
        System.out.println("********** Pesos Finales **********");
        perceptron.printWeights();


        System.out.println("\n\n=======\nMultiLayer Perceptron");

        // initialization
        ArrayList<float[]> input = new ArrayList<float[]>();
        ArrayList<float[]> output = new ArrayList<float[]>();
        for (int i = 0; i < 4; ++i) {
            input.add(new float[2]);
            output.add(new float[1]);
        }

        // fill the examples database
        input.get(0)[0] = -1; input.get(0)[1] = 1;  output.get(0)[0] = 1;
        input.get(1)[0] = 1;  input.get(1)[1] = 1;  output.get(1)[0] = -1;
        input.get(2)[0] = 1;  input.get(2)[1] = -1; output.get(2)[0] = 1;
        input.get(3)[0] = -1; input.get(3)[1] = -1; output.get(3)[0] = -1;

        int nn_neurons[] = {
                input.get(0).length,
                input.get(0).length * 3,
                output.get(0).length
        };

        MultiLayerPerceptron mlp = new MultiLayerPerceptron(nn_neurons);

        for (int i = 0; i < 100; ++i) {
            mlp.learn(input, output, 0.3f);
            mlp.evaluateQuadraticError(input, output);
        }

        float[] a = mlp.getOutput();
        for (int m = 0; m < output.size(); m++){
            System.out.println("Esperada: " + output.get(m)[0] + ", Calculada: " + a[m]);
        }

        return;

    }
}
