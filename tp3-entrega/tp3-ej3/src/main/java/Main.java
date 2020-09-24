import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
            csvWriter = new FileWriter("results.csv");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long nowSeconds = System.nanoTime();
            double elapsedTimeInSecond = (double) (nowSeconds - start) / 1000000000;
            csvWriter.append("Time: " + dtf.format(now));
            csvWriter.append("\n");
            csvWriter.append("Execution time: " + elapsedTimeInSecond + " seconds");
            csvWriter.append("\n");

            for (int i = 0; i < 100; i++) {
				mlp.learn(input, output, 0.3f);
				float error = mlp.evaluateQuadraticError(input, output);
				System.out.println(i + " -> error : " + error);
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

        System.out.println("\n********** Even number 5-5 **********\n");

        long start2 = System.nanoTime();

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
                    } else {
                        input2.get(z - 5)[j + ((i%7) * 5)] = auxList[j];
                        //System.out.println(input.get(z-5)[j + ((i%7) * 5)] + "  input(" + (z-5) + ")[" + (j + ((i%7) * 5)) + "]");
                    }
                }
                //System.out.println("\n");
            }
            if (z < 5) {
                output1.get(z)[0] = outputAux[z];
            } else {
                output2.get(z - 5)[0] = outputAux[z];
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
            csvWriter2 = new FileWriter("results2.csv");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now2 = LocalDateTime.now();
            long nowSeconds2 = System.nanoTime();
            double elapsedTimeInSecond2 = (double) (nowSeconds2 - start2) / 1000000000;
            csvWriter2.append("Time: " + dtf2.format(now2));
            csvWriter2.append("\n");
            csvWriter2.append("Execution time: " + elapsedTimeInSecond2 + " seconds");
            csvWriter2.append("\n");

            ArrayList<Float> trainErrors = new ArrayList<>();
            ArrayList<Float> testErrors = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                mlp1.learn(input1, output1, 0.1f);
                float error1 = mlp1.evaluateAccuracy(input1, output1, 0.001f);
                float error2 = mlp1.evaluateAccuracy(input2, output2,0.001f);
                trainErrors.add(error1);
                testErrors.add(error2);
                System.out.println(i + " -> error : " + error2);
            }

            csvWriter2.append("\nTrain Error\n");

            for (float e: trainErrors) {
                csvWriter2.append(e + "\n");
            }

            csvWriter2.append("\nTest Error\n");

            for (float e: testErrors) {
                csvWriter2.append(e + "\n");
            }

            csvWriter2.flush();
            csvWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] a2 = mlp1.getOutput();
        for (int m = 0; m < output2.size(); m++){
            System.out.println("Esperada: " + output2.get(m)[0] + ", Calculada: " + a2[m]);
        }

        System.out.println("\n********** Even number 7-3 **********\n");

        long start3 = System.nanoTime();

        input1 = new ArrayList<float[]>();
        input2 = new ArrayList<float[]>();
        output1 = new ArrayList<float[]>();
        output2 = new ArrayList<float[]>();

        // initialization
        for (int i = 0; i < 7; i++){
            input1.add(new float[35]);
            output1.add(new float[1]);
        }

        for (int i = 0; i < 3; i++){
            input2.add(new float[35]);
            output2.add(new float[1]);
        }

        // fill the examples database
        for (int z = 0; z < 10; z++) {
            for (int i = (z * 7); i < (z+1) * 7 ; i++) {
                Integer[] auxList = aux3.get(i);
                for (int j = 0; j < auxList.length; j++) {
                    if (z < 7) {
                        input1.get(z)[j + ((i % 7) * 5)] = auxList[j];
                        //System.out.println(input.get(z)[j + ((i%7) * 5)] + "  input(" + z + ")[" + (j + ((i%7) * 5)) + "]");
                    }else {
                        input2.get(z - 7)[j + ((i%7) * 5)] = auxList[j];
                        //System.out.println(input.get(z-5)[j + ((i%7) * 5)] + "  input(" + (z-5) + ")[" + (j + ((i%7) * 5)) + "]");
                    }
                }
                //System.out.println("\n");
            }
            if (z < 7) {
                output1.get(z)[0] = outputAux[z];
            } else {
                output2.get(z - 7)[0] = outputAux[z];
            }
        }


        int nn_neurons3[] = {
                input1.get(0).length,
                input1.get(0).length,
                output1.get(0).length
        };

        MultiLayerPerceptron mlp2 = new MultiLayerPerceptron(nn_neurons3);

        try {
            FileWriter csvWriter3 = null;
            csvWriter3 = new FileWriter("results3.csv");
            DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now3 = LocalDateTime.now();
            long nowSeconds3 = System.nanoTime();
            double elapsedTimeInSecond3 = (double) (nowSeconds3 - start3) / 1000000000;
            csvWriter3.append("Time: " + dtf3.format(now3));
            csvWriter3.append("\n");
            csvWriter3.append("Execution time: " + elapsedTimeInSecond3 + " seconds");
            csvWriter3.append("\n");

            ArrayList<Float> trainErrors = new ArrayList<>();
            ArrayList<Float> testErrors = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                mlp2.learn(input1, output1, 0.1f);
                float error1 = mlp2.evaluateAccuracy(input1, output1, 0.1f);
                float error2 = mlp2.evaluateAccuracy(input2, output2,0.1f);
                trainErrors.add(error1);
                testErrors.add(error2);
                System.out.println(i + " -> Accuracy : " + error2);
            }

            csvWriter3.append("\nTrain Error\n");

            for (float e: trainErrors) {
                csvWriter3.append(e + "\n");
            }

            csvWriter3.append("\nTest Error\n");

            for (float e: testErrors) {
                csvWriter3.append(e + "\n");
            }


            csvWriter3.flush();
            csvWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] a3 = mlp2.getOutput();
        for (int m = 0; m < output2.size(); m++){
            System.out.println("Esperada: " + output2.get(m)[0] + ", Calculada: " + a3[m]);
        }

        return;
    }
}
