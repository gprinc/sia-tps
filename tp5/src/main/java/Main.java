import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final double DEFAULT_LRATE_EVEN = 0.1;
    private static final int DEFAULT_ITER_EVEN = 5;
    private static final int DEFAULT_EVEN_PARTITION = 5;
    private static final double DEFAULT_THRESHOLD = 0.1;
    private static final double DEFAULT_ACCURACY = 0.001;

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
        double mlp_lrate_even;
        if (auxData == null)
            mlp_lrate_even = DEFAULT_LRATE_EVEN;
        else
            mlp_lrate_even = Double.parseDouble(auxData);
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
        double threshold;
        auxData = (String) data.get("threshold");
        if (auxData == null)
            threshold = DEFAULT_THRESHOLD;
        else
            threshold = Double.parseDouble(auxData);
        double accuracy;
        auxData = (String) data.get("accuracy");
        if (auxData == null)
            accuracy = DEFAULT_ACCURACY;
        else
            accuracy = Double.parseDouble(auxData);

        int font = InitializerJson.giveInt((String) data.get("font"), DEF_FONT);

        File file3 = new File("TP3-ej3-mapa-de-pixeles-digitos-decimales.txt");
        ArrayList<Integer[]> aux3 = new ArrayList<>();
        aux3 = TxtReader.getIntegerArrayFromTxt(file3, 5);

        System.out.println("\n\n=======\nMultiLayer Perceptron");

        ArrayList<double[]> input1 = new ArrayList<double[]>();
        ArrayList<double[]> input2 = new ArrayList<double[]>();
        ArrayList<double[]> output1 = new ArrayList<double[]>();
        ArrayList<double[]> output2 = new ArrayList<double[]>();

        // initialization
        for (int i = 0; i < mlp_even_partition; i++){
            input1.add(new double[35]);
            output1.add(new double[1]);
        }

        for (int i = 0; i < (10 - mlp_even_partition); i++){
            input2.add(new double[35]);
            output2.add(new double[1]);
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
        input1 = new ArrayList<double[]>();

        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> aux = lettersN.get(i);
            double[] doubleA = new double[aux.size()];
            for (int j = 0; j < aux.size(); j++) {
                doubleA[j] = aux.get(j);
            }
            input1.add(doubleA);
        }

        System.out.println("\n********** Initialized font **********\n");

        long start3 = System.nanoTime();

        MultiLayerPerceptron mlp2;
        int nn_neurons3[];
        double errAvg;
        LayerCreator lc;

        do {
            lc = new LayerCreator(input1.get(0).length);
            nn_neurons3 = lc.getLayer();
            //System.out.println(input1.get(0).length);
            mlp2 = new MultiLayerPerceptron(nn_neurons3);

            DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now3 = LocalDateTime.now();
            long nowSeconds3 = System.nanoTime();
            double elapsedTimeInSecond3 = (double) (nowSeconds3 - start3) / 1000000000;
            ArrayList<Double> trainErrors = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                mlp2.learn(input1, input1, mlp_lrate_even, mlp_iter_even, threshold);
                double[][] middleOutput = mlp2.getMiddleOutput();
                for (int j = 0; j < middleOutput.length; j++) {
                    for (int k = 0; k < middleOutput[0].length; k++) {
                        //System.out.println("middleOutput[" + j + "][" + k + "] : " + middleOutput[j][k]);
                    }
                }
                //double error1 = mlp2.evaluateAccuracy(input1, input1, accuracy);
                //trainErrors.add(error1);
                //System.out.println(i + " -> Error : " + error1);
                double error = mlp2.evaluateQuadraticError(input1, input1);
                trainErrors.add(error);
                System.out.println(" => Error = " + error);
            }

            errAvg = 0 ;

            for (double e: trainErrors) {
                errAvg += e;
            }

            errAvg = errAvg / trainErrors.size();

            double[][] output = mlp2.getOutput();

            System.out.println(output[0].length);

            for (int i = 0; i < input1.size(); i++) {
                for (int j = 0; j < output[0].length; j++) {
                    System.out.print((output[i][j] > 0.5 ? 1 : 0) + " ");
                }
                System.out.println();
                for (int j = 0; j < input1.get(0).length; j++) {
                    System.out.print(((int) input1.get(i)[j]) + " ");
                }
                System.out.println();
            }

            System.out.println(" => Error Average = " + errAvg);

        } while (errAvg > 1.5); // en realidad es la accuracy

        long start4 = System.nanoTime();

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

    static ArrayList<double[]> toArrayList(double[][] a) {
        ArrayList<double[]> b = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            b.add(a[i]);
        }
        return b;
    }
}