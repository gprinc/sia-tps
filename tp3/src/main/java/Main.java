import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    private static final float DEFAULT_LRATE_XOR = 0.3f;
    private static final float DEFAULT_LRATE_EVEN = 0.1f;
    private static final int DEFAULT_ITER_XOR = 100;
    private static final int DEFAULT_ITER_EVEN = 5;
    private static final int DEFAULT_EVEN_PARTITION = 5;

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        JSONObject data;
        try {
            data = (JSONObject) parser.parse(new FileReader("config.json"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading config in TP2");
            return;
        }
        String auxData = (String) data.get("mlp_lrate_xor");
        float mlp_lrate_xor;
        if (auxData == null)
            mlp_lrate_xor = DEFAULT_LRATE_XOR;
        else
            mlp_lrate_xor = Float.parseFloat(auxData);
        auxData = (String) data.get("mlp_lrate_even");
        float mlp_lrate_even;
        if (auxData == null)
            mlp_lrate_even = DEFAULT_LRATE_EVEN;
        else
            mlp_lrate_even = Float.parseFloat(auxData);
        auxData = (String) data.get("mlp_iter_xor");
        int mlp_iter_xor;
        if (auxData == null)
            mlp_iter_xor = DEFAULT_ITER_XOR;
        else
            mlp_iter_xor = Integer.parseInt(auxData);
        auxData = (String) data.get("mlp_iter_even");
        int mlp_iter_even;
        if (auxData == null)
            mlp_iter_even = DEFAULT_ITER_EVEN;
        else
            mlp_iter_even = Integer.parseInt(auxData);
        auxData = (String) data.get("mlp_even_partition");
        int mlp_even_partition;
        if (auxData == null)
            mlp_even_partition = DEFAULT_EVEN_PARTITION;
        else
            mlp_even_partition = Integer.parseInt(auxData);

        long start = System.nanoTime();

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

        try {
            FileWriter csvWriter = null;
            csvWriter = new FileWriter("Ejercicio3-xor.csv");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long nowSeconds = System.nanoTime();
            double elapsedTimeInSecond = (double) (nowSeconds - start) / 1000000000;
            csvWriter.append("Time: " + dtf.format(now));
            csvWriter.append("\n");
            csvWriter.append("Execution time: " + elapsedTimeInSecond + " seconds");
            csvWriter.append("\n");

            for (int i = 0; i < mlp_iter_xor; i++) {
                mlp.learn(input, output, mlp_lrate_xor, 1000);
                float error = mlp.evaluateQuadraticError(input, output);
                System.out.println(" => Error = " + error);
                csvWriter.append(error + "\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] a = mlp.getOutput();
        for (int m = 0; m < output.size(); m++){
            System.out.println("Esperada: " + output.get(m)[0] + ", Calculada: " + a[m]);
        }

        System.out.println("\n********** Even number **********\n");

        long start2 = System.nanoTime();

        ArrayList<float[]> input1 = new ArrayList<float[]>();
        ArrayList<float[]> input2 = new ArrayList<float[]>();
        ArrayList<float[]> output1 = new ArrayList<float[]>();
        ArrayList<float[]> output2 = new ArrayList<float[]>();

        int[] outputAux = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};

        // initialization
        for (int i = 0; i < mlp_even_partition; i++){
            input1.add(new float[35]);
            output1.add(new float[1]);
        }

        for (int i = 0; i < (10 - mlp_even_partition); i++){
            input2.add(new float[35]);
            output2.add(new float[1]);
        }

        // fill the examples database
        for (int z = 0; z < 10; z++) {
            for (int i = (z * 7); i < (z+1) * 7 ; i++) {
                Integer[] auxList = aux3.get(i);
                for (int j = 0; j < auxList.length; j++) {
                    if (z < mlp_even_partition) {
                        input1.get(z)[j + ((i % 7) * 5)] = auxList[j];
                        //System.out.println(input.get(z)[j + ((i%7) * 5)] + "  input(" + z + ")[" + (j + ((i%7) * 5)) + "]");
                    }else {
                        input2.get(z - mlp_even_partition)[j + ((i%7) * 5)] = auxList[j];
                        //System.out.println(input.get(z-5)[j + ((i%7) * 5)] + "  input(" + (z-5) + ")[" + (j + ((i%7) * 5)) + "]");
                    }
                }
                //System.out.println("\n");
            }
            if (z < mlp_even_partition) {
                output1.get(z)[0] = outputAux[z];
            }else {
                output2.get(z - mlp_even_partition)[0] = outputAux[z];
            }
        }


        int nn_neurons2[] = {
                input1.get(0).length,
                input1.get(0).length,
                output1.get(0).length
        };

        MultiLayerPerceptron mlp1 = new MultiLayerPerceptron(nn_neurons2);

        try {
            FileWriter csvWriter2 = null;
            csvWriter2 = new FileWriter("Ejercicio3-even.csv");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now2 = LocalDateTime.now();
            long nowSeconds2 = System.nanoTime();
            double elapsedTimeInSecond2 = (double) (nowSeconds2 - start2) / 1000000000;
            csvWriter2.append("Time: " + dtf2.format(now2));
            csvWriter2.append("\n");
            csvWriter2.append("Execution time: " + elapsedTimeInSecond2 + " seconds");
            csvWriter2.append("\n");

            csvWriter2.append("Train Error" + "\n");
            for (int i = 0; i < mlp_iter_even; i++) {
                float error3 = mlp1.learn(input1, output1, mlp_lrate_even, 1);
                csvWriter2.append(error3 + "\n");
            }

            csvWriter2.append("Test Error" + "\n");
            for (int i = 0; i < mlp_iter_even; i++) {
                float error2 = mlp1.evaluateQuadraticError(input2, output2);
                System.out.println(" => Error = " + error2);
                csvWriter2.append(error2 + "\n");
            }

            csvWriter2.flush();
            csvWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] a3 = mlp1.getOutput();
        for (int m = 0; m < output2.size(); m++){
            System.out.println("Esperada: " + output2.get(m)[0] + ", Calculada: " + a3[m]);
        }

        return;
    }
}