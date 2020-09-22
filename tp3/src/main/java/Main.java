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
        ArrayList<Double> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2);

        NonLinealPerceptron linealPer = new NonLinealPerceptron(aux, aux2);
        linealPer.train();

        /*File file3 = new File("TP3-ej3-mapa-de-pixeles-digitos-decimales.txt");
        ArrayList<Integer[]> aux3 = new ArrayList<>();
        aux3 = TxtReader.getIntegerArrayFromTxt(file3, 5);

        System.out.println("\n\n=======\nMultiLayer Perceptron");

        System.out.println("\n********** XOR **********\n");

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

        System.out.println("\n********** Even number **********\n");


        input = new ArrayList<float[]>();
        output = new ArrayList<float[]>();

        // initialization
        for (int i = 0; i < 10; i++) {
            input.add(new float[35]);
            output.add(new float[1]);
        }

        int[] outputAux = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0};

        // fill the examples database
        for (int z = 0; z < 10; z++) {
            //System.out.println("\n**********  " + z + "  **********\n");
            for (int i = (z * 7); i < (z+1) * 7 ; i++) {
                Integer[] auxList = aux3.get(i);
                for (int j = 0; j < auxList.length ; j++) {
                    input.get(z)[j + ((i%7) * 5)] = auxList[j];
                    //System.out.println(input.get(z)[j + ((i%7) * 5)]);
                }
                //System.out.println("\n");
            }
            output.get(z)[0] = outputAux[z];
        }


        int nn_neurons2[] = {
            input.get(0).length,
            input.get(0).length * 2,
            output.get(0).length
        };

        MultiLayerPerceptron mlp1 = new MultiLayerPerceptron(nn_neurons2);

        for (int i = 0; i < 1; ++i) {
            System.out.println("\n********** llegue 1 **********\n");
            mlp1.learn(input, output, 0.5f);
            System.out.println("\n********** llegue 2 **********\n");
            mlp1.evaluateQuadraticError(input, output);
        }

        System.out.println("\n********** llegue 3 **********\n");

        float[] a2 = mlp1.getOutput();
        for (int m = 0; m < output.size(); m++){
            System.out.println("Esperada: " + output.get(m)[0] + ", Calculada: " + a2[m]);
        }*/

        return;

    }
}
