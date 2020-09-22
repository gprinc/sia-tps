import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file3 = new File("TP3-ej3-mapa-de-pixeles-digitos-decimales.txt");
        ArrayList<Integer[]> aux3 = new ArrayList<>();
        aux3 = TxtReader.getIntegerArrayFromTxt(file3, 5);

        System.out.println("\n\n=======\nMultiLayer Perceptron");

        System.out.println("\n********** XOR **********\n");

        // initialization
        ArrayList<float[]> input = new ArrayList<float[]>();
        ArrayList<float[]> output = new ArrayList<float[]>();
        for (int i = 0; i < 4; i++) {
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
                input.get(0).length,
                output.get(0).length
        };

        MultiLayerPerceptron mlp = new MultiLayerPerceptron(nn_neurons);

        for (int i = 0; i < 100; i++) {
            mlp.learn(input, output, 0.3f);
            mlp.evaluateQuadraticError(input, output);
        }

        float[] a = mlp.getOutput();
        for (int m = 0; m < output.size(); m++){
            System.out.println("Esperada: " + output.get(m)[0] + ", Calculada: " + a[m]);
        }

        System.out.println("\n********** Even number **********\n");


        ArrayList<float[]> input1 = new ArrayList<float[]>();
        ArrayList<float[]> input2 = new ArrayList<float[]>();
        ArrayList<float[]> output1 = new ArrayList<float[]>();
        ArrayList<float[]> output2 = new ArrayList<float[]>();

        // initialization
        for (int i = 0; i < 5; i++){
            input1.add(new float[35]);
            input2.add(new float[35]);
            output1.add(new float[1]);
            output2.add(new float[1]);
        }

        int[] outputAux = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};

        // fill the examples database
        for (int z = 0; z < 10; z++) {
            for (int i = (z * 7); i < (z+1) * 7 ; i++) {
                Integer[] auxList = aux3.get(i);
                for (int j = 0; j < auxList.length; j++) {
                    if (z < 5) {
                        input1.get(z)[j + ((i % 7) * 5)] = auxList[j];
                        //System.out.println(input.get(z)[j + ((i%7) * 5)] + "  input(" + z + ")[" + (j + ((i%7) * 5)) + "]");
                    }else {
                        input2.get(z - 5)[j + ((i%7) * 5)] = auxList[j];
                        //System.out.println(input.get(z-5)[j + ((i%7) * 5)] + "  input(" + (z-5) + ")[" + (j + ((i%7) * 5)) + "]");
                    }
                }
                //System.out.println("\n");
            }
            if (z < 5) {
                output1.get(z)[0] = outputAux[z];
            }else {
                output2.get(z - 5)[0] = outputAux[z];
            }
        }


        int nn_neurons2[] = {
            input1.get(0).length,
            input1.get(0).length,
            output1.get(0).length
        };

        MultiLayerPerceptron mlp1 = new MultiLayerPerceptron(nn_neurons2);

        for (int i = 0; i < 5; i++) {
            mlp1.learn(input1, output1, 0.1f);
            mlp1.evaluateQuadraticError(input2, output2);
        }

        float[] a2 = mlp1.getOutput();
        for (int m = 0; m < output2.size(); m++){
            System.out.println("Esperada: " + output2.get(m)[0] + ", Calculada: " + a2[m]);
        }

        return;
    }
}
