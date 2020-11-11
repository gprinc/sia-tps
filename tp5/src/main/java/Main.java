import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final float DEFAULT_LRATE_EVEN = 0.1f;
    private static final int DEFAULT_ITER_EVEN = 5;
    private static final int DEFAULT_EVEN_PARTITION = 5;
    private static final float DEFAULT_THRESHOLD = 0.1f;
    private static final float DEFAULT_ACCURACY = 0.001f;

    private static final int DEF_NOISE_PERCENTAGE = 1;
    private static final int DEF_FONT = 1;

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
        String auxData = (String) data.get("mlp_lrate_even");
        float mlp_lrate_even;
        if (auxData == null)
            mlp_lrate_even = DEFAULT_LRATE_EVEN;
        else
            mlp_lrate_even = Float.parseFloat(auxData);
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
        float threshold;
        auxData = (String) data.get("threshold");
        if (auxData == null)
            threshold = DEFAULT_THRESHOLD;
        else
            threshold = Float.parseFloat(auxData);
        float accuracy;
        auxData = (String) data.get("accuracy");
        if (auxData == null)
            accuracy = DEFAULT_ACCURACY;
        else
            accuracy = Float.parseFloat(auxData);

        int font = InitializerJson.giveInt((String) data.get("font"), DEF_FONT);

        File file3 = new File("TP3-ej3-mapa-de-pixeles-digitos-decimales.txt");
        ArrayList<Integer[]> aux3 = new ArrayList<>();
        aux3 = TxtReader.getIntegerArrayFromTxt(file3, 5);

        System.out.println("\n\n=======\nMultiLayer Perceptron");

        ArrayList<float[]> input1 = new ArrayList<float[]>();
        ArrayList<float[]> input2 = new ArrayList<float[]>();
        ArrayList<float[]> output1 = new ArrayList<float[]>();
        ArrayList<float[]> output2 = new ArrayList<float[]>();

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
                    } else {
                        input2.get(z - mlp_even_partition)[j + ((i%7) * 5)] = auxList[j];
                        //System.out.println(input.get(z-5)[j + ((i%7) * 5)] + "  input(" + (z-5) + ")[" + (j + ((i%7) * 5)) + "]");
                    }
                }
                //System.out.println("\n");
            }
        }

        ArrayList<ArrayList<Integer>> lettersN = getLetters(font);
        input1 = new ArrayList<float[]>();

        for (int i = 0; i < lettersN.size(); i++) {
            ArrayList<Integer> aux = lettersN.get(i);
            float[] floatA = new float[aux.size()];
            for (int j = 0; j < aux.size(); j++) {
                floatA[j] = aux.get(j);
            }
            input1.add(floatA);
        }

        System.out.println("\n********** Initialized font **********\n");

        long start3 = System.nanoTime();

        MultiLayerPerceptron mlp2;
        int nn_neurons3[];
        float errAvg;

        do {
            nn_neurons3 = LayerCreator.generateLayer(input1.get(0).length);
            //System.out.println(input1.get(0).length);
            mlp2 = new MultiLayerPerceptron(nn_neurons3);

            DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now3 = LocalDateTime.now();
            long nowSeconds3 = System.nanoTime();
            double elapsedTimeInSecond3 = (double) (nowSeconds3 - start3) / 1000000000;
            ArrayList<Float> trainErrors = new ArrayList<>();


            for (int i = 0; i < 10; i++) {
                mlp2.learn(input1, input1, mlp_lrate_even, mlp_iter_even, threshold);
                float[][] middleOutput = mlp2.getMiddleOutput();
                for (int j = 0; j < middleOutput.length; j++) {
                    for (int k = 0; k < middleOutput[0].length; k++) {
                        //System.out.println("middleOutput[" + j + "][" + k + "] : " + middleOutput[j][k]);
                    }
                }
                //float error1 = mlp2.evaluateAccuracy(input1, input1, accuracy);
                //trainErrors.add(error1);
                //System.out.println(i + " -> Error : " + error1);
                float error = mlp2.evaluateQuadraticError(input1, input1) / (input1.size() * input1.size());
                trainErrors.add(error);
                System.out.println(" => Error = " + error);
            }

            errAvg = 0 ;

            for (Float e: trainErrors) {
                errAvg += e;
            }

            errAvg = errAvg / trainErrors.size();


            float[][] a2 = mlp2.getOutput();
            for (int m = 0; m < output2.size(); m++){
                //System.out.println("Esperada: " + output2.get(m)[0] + ", Calculada: " + a2[m]);
            }

            System.out.println(" => Error Average = " + errAvg);

        } while (errAvg > 0.2); // en realidad es la accuracy

        return;
    }

    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    static String completeString(String s) {
        String aux = s;
        int amount = 5 - aux.length();
        while (amount > 0) {
            amount--;
            aux = '0' + aux;
        }
        return aux;
    }

    static ArrayList<ArrayList<Integer>> getLetters(int fontNumber){
        int[][] font;
        switch (fontNumber) {
            case 3:
                font = Fonts.font3;
                break;
            case 2:
                font = Fonts.font2;
                break;
            default:
                font = Fonts.font1;
                break;
        }
        String[] letterInBinary = new String[7];
        String aux;
        ArrayList<ArrayList<Integer>> lettersN = new ArrayList<>();
        for (int j = 0; j < 32; j++) {
            for (int i = 0; i < 7; i++) {
                aux = hexToBin(String.valueOf(font[j][i]));
                if (aux.length() > 5)
                    aux = aux.substring(aux.length() - 5);
                aux = completeString(aux);
                letterInBinary[i] = aux;
            }
            ArrayList<Integer> bite = new ArrayList<>();
            for (String s: letterInBinary) {
                char[]c = s.toCharArray();

                for (int i = 0; i < c.length; i++) {
                    bite.add( c[i] - '0');
                }
            }
            lettersN.add(bite);
        }
        return lettersN;
    }

    static ArrayList<Integer> saltAndPepperLetter(ArrayList<Integer> letter) {
        JSONParser parser = new JSONParser();
        JSONObject data;
        try {
            data = (JSONObject) parser.parse(new FileReader("config.json"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading config");
            return null;
        }
        int noisePercentage = InitializerJson.giveInt((String) data.get("noise_percentage"), DEF_NOISE_PERCENTAGE);
        int amountOfBits = letter.size() * (noisePercentage / 100);
        Random r = new Random();
        ArrayList<Integer> aux = new ArrayList<>();
        ArrayList<Integer> indexesToChange = new ArrayList<>();
        for (int i = 0; i < amountOfBits; i++) {
            indexesToChange.add(r.nextInt(letter.size()-1) + 1);
        }
        int index = 0;
        for (Integer i: letter) {
            if (indexesToChange.contains(index)) {
                if (i.equals(1)) aux.add(0);
                else aux.add(1);
            } else {
                aux.add(i);
            }
            index++;
        }
        return aux;
    }
}