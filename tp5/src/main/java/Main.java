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
    private static final String DEF_EJ_NOISE = "1-noise";
    public static final int DEF_ACTIVATON_METHOD = 0;
    private static final String EJ_TWO = "2";
    private static final int DEFAULT_MAP_SIZE = 1;
    private static final int DEFAULT_LETTERS = 5;
    private static final int DEFAULT_NOICEP = 2;
    private static final int DEFAULT_MIDDLE_LAYER = 2;
    private static final int DEFAULT_KOHONEN_K = 3;
    private static final double DEFAULT_KOHONEN_LRATE = 0.01;
    private static final int DEFAULT_KOHONEN_DELTA = 2;

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
        double mlp_lrate_even = InitializerJson.giveDouble((String) data.get("mlp_lrate_even"), DEFAULT_LRATE_EVEN);
        int mlp_iter_even = InitializerJson.giveInt((String) data.get("mlp_iter_even"), DEFAULT_ITER_EVEN);
        int mlp_even_partition = InitializerJson.giveInt((String) data.get("mlp_even_partition"), DEFAULT_EVEN_PARTITION);
        double threshold = InitializerJson.giveDouble((String) data.get("threshold"), DEFAULT_THRESHOLD);
        double accuracy = InitializerJson.giveDouble((String) data.get("accuracy"), DEFAULT_ACCURACY);
        int letters = InitializerJson.giveInt((String) data.get("letters"), DEFAULT_LETTERS);
        int noice_percentage = InitializerJson.giveInt((String) data.get("noice_percentage"), DEFAULT_NOICEP);
        int middleLayer = InitializerJson.giveInt((String) data.get("middleLayer"), DEFAULT_MIDDLE_LAYER);
        int kohonen_k = InitializerJson.giveInt((String) data.get("kohonen_k"), DEFAULT_KOHONEN_K);
        double kohonen_lr = InitializerJson.giveDouble((String) data.get("kohonen_lr"), DEFAULT_KOHONEN_LRATE);
        int kohonen_delta = InitializerJson.giveInt((String) data.get("kohonen_delta"), DEFAULT_KOHONEN_DELTA);

        int font = InitializerJson.giveInt((String) data.get("font"), DEF_FONT);
        String ej = InitializerJson.giveEj((String) data.get("ej"));
        int activationMethod = InitializerJson.giveInt((String) data.get("activation_method"), DEF_ACTIVATON_METHOD);

        File file3 = new File("TP3-ej3-mapa-de-pixeles-digitos-decimales.txt");
        ArrayList<Integer[]> aux3 = new ArrayList<>();
        aux3 = TxtReader.getIntegerArrayFromTxt(file3, 5);

        System.out.println("\n\n=======\nMultiLayer Perceptron");

        ArrayList<double[]> input1 = new ArrayList();
        ArrayList<double[]> input2 = new ArrayList();
        ArrayList<double[]> input3 = new ArrayList();
        ArrayList<double[]> input4 = new ArrayList();
        ArrayList<double[]> output1 = new ArrayList();
        ArrayList<double[]> output2 = new ArrayList();

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
                    } else {
                        input2.get(z - mlp_even_partition)[j + ((i%7) * 5)] = auxList[j];
                    }
                }
            }
        }

        ArrayList<ArrayList<Integer>> mlpData;
        ArrayList<ArrayList<Integer>> mlpData2;
        ArrayList<ArrayList<Integer>> mlpData3;
        ArrayList<ArrayList<Integer>> auxData;
        boolean withNoise = false;
        if (ej.equals(EJ_TWO)) {
            int mapSize = InitializerJson.giveInt((String) data.get("mapSize"), DEFAULT_MAP_SIZE);
            EjTwo ejTwo = new EjTwo(mapSize);
            mlpData = ejTwo.getMap();
            // TODO ver parametros correctos y setear valores adecuados para realizar el aprendizaje de mapas
        } else {
            withNoise = ej.equals(DEF_EJ_NOISE);
            mlpData = getLetters(font, withNoise);
            mlpData2 = getLetters(font, withNoise);
            mlpData3 = getLetters(font, withNoise);
            auxData = getLetters(font,false);
            // TODO hacer que se creen varios arrays con ruido, asi los entrenamos a todos y capas aprende mejor
            input2 = new ArrayList();
            for (int i = 0; i < letters; i++) {
                ArrayList<Integer> aux = auxData.get(i);
                double[] doubleA = new double[aux.size()];
                for (int j = 0; j < aux.size(); j++) {
                    doubleA[j] = aux.get(j);
                }
                input2.add(doubleA);
            }

            input3 = new ArrayList();
            for (int i = 0; i < letters; i++) {
                ArrayList<Integer> aux = mlpData2.get(i);
                double[] doubleA = new double[aux.size()];
                for (int j = 0; j < aux.size(); j++) {
                    doubleA[j] = aux.get(j);
                }
                input3.add(doubleA);
            }

            input4 = new ArrayList();
            for (int i = 0; i < letters; i++) {
                ArrayList<Integer> aux = mlpData3.get(i);
                double[] doubleA = new double[aux.size()];
                for (int j = 0; j < aux.size(); j++) {
                    doubleA[j] = aux.get(j);
                }
                input4.add(doubleA);
            }

        }
        input1 = new ArrayList();
        for (int i = 0; i < letters; i++) {
            ArrayList<Integer> aux = mlpData.get(i);
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
        LayerCreator.init();

        do {
            // TODO AGREGAR QUE EL TAMAÑO DE LA CAPA DEL MEDIO SEA PARAMETRIZABLE
            lc = new LayerCreator(input1.get(0).length, middleLayer);
            nn_neurons3 = lc.getLayer();
            mlp2 = new MultiLayerPerceptron(nn_neurons3, mlp_lrate_even, activationMethod);

            ArrayList<Double> trainErrors = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                mlp2.learn(input1, input1, mlp_iter_even, threshold);

                double error;
                if (withNoise)  {
                    // TODO relacionado al del principio hacer que entrene con varios
                    mlp2.learn(input1, input2, mlp_iter_even, threshold);
                    mlp2.learn(input3, input2, mlp_iter_even, threshold);
                    mlp2.learn(input4, input2, mlp_iter_even, threshold);
                    error = mlp2.evaluateQuadraticError(input1, input2);
                } else {
                    error = mlp2.evaluateQuadraticError(input1, input1);
                    mlp2.learn(input1, input1, mlp_iter_even, threshold);
                }

                trainErrors.add(error);
                mlp2.evaluateAccuracy(input1, input1,0.5);
                System.out.println(" => Error = " + error);
            }

            errAvg = 0 ;

            for (double e: trainErrors) {
                errAvg += e;
            }

            errAvg = errAvg / trainErrors.size();

            double[][] output = mlp2.getOutput();

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

            System.out.println();
            System.out.println(" => Error Average = " + errAvg);
            System.out.println();

            LayerCreator.update(errAvg);

            errAvg = trainErrors.get(trainErrors.size()-1);

        } while (errAvg > 1); // en realidad es la accuracy

        if (withNoise)  {
            ArrayList<double[]> noiseArray = new ArrayList<>();
            ArrayList<double[]> arrayNotNoise = new ArrayList<>();
            Random r = new Random();
            int rand = r.nextInt(input2.size());
            arrayNotNoise.add(input2.get(rand));
            // TODO hacer que los parametros de entrada tambien cambien la misma cantidad de bits
            noiseArray.add(noise(input2.get(rand),noice_percentage));
            double err;
            System.out.println("\n********** Noise err **********\n");
            err = mlp2.evaluateQuadraticError(noiseArray, arrayNotNoise);
            System.out.println("Err " + err);
            System.out.println();

            double[][] output = mlp2.getOutput();

            for (int i = 0; i < noiseArray.size(); i++) {
                for (int j = 0; j < noiseArray.get(0).length; j++) {
                    System.out.print(((int) noiseArray.get(i)[j]) + " ");
                }
                System.out.println();
                for (int j = 0; j < output[0].length; j++) {
                    System.out.print((output[i][j] > 0.5 ? 1 : 0) + " ");
                }
                System.out.println();
                for (int j = 0; j < noiseArray.get(0).length; j++) {
                    System.out.print(((int) arrayNotNoise.get(i)[j]) + " ");
                }
                System.out.println();
            }
        }

        if (ej.equals(EJ_TWO)) {
            double[][] output = mlp2.getOutput();
            for (int i = 0; i < output.length; i++) {

            }
        }

        double[][] middleOutput = mlp2.getMiddleOutput();

        ArrayList<double[]> middleOutputP = new ArrayList<>();

        for (double[] m: middleOutput) {
            middleOutputP.add(m);
        }

        Kohonen kohonen;
        kohonen = new Kohonen(kohonen_k, middleLayer, Math.sqrt(3*3 + 3*3),kohonen_lr,kohonen_delta);

        for (int i = 0; i < 3 * 500; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt(middleOutputP.size());
            kohonen.learn(middleOutputP.get(randomNum));
        }
        double[] weights = kohonen.getNodeWeight(middleOutputP.get(0));

        System.out.println();
        System.out.println("********** Kohonen weights of first input **********");
        System.out.println();

        for (int i = 0; i < weights.length ; i++) {
            System.out.println( "Weight["+i+"]  "+weights[i]);
        }

        ArrayList<double[]> decode = new ArrayList<>();
        decode.add(weights);

        double[][] output = mlp2.decode(decode);

        System.out.println();
        System.out.println("********** New output generated via kohonen weights **********");

        for (int i = 0; i < output.length; i++) {
            System.out.println();
            for (int j = 0; j < output[0].length; j++) {
                System.out.print((output[i][j] > 0.5 ? 1 : 0) + " ");
            }
            System.out.println();
        }

        JFreeDraw draw = new JFreeDraw(mlp2.getMiddleOutput());
        draw.setVisible(true);

        return;
    }

    static double[] noise(double[] i, int noice_percentage) {
        double[] aux = new double[i.length];
        Random r = new Random();
        int randomr = r.nextInt(i.length);
        for (int j = 0; j < i.length; j++) {
            aux[j] = i[j] - 0;
        }
        for (int j = 0; j < noice_percentage; j++) {
            randomr = r.nextInt(i.length);
            aux[randomr] = (i[randomr] == 0 ? 1:0);
        }
        return aux;
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

    static ArrayList<ArrayList<Integer>> getLetters(int fontNumber, boolean withNoise){
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
            if (withNoise) {
                ArrayList<Integer> auxBite = saltAndPepperLetter(bite);
                lettersN.add(auxBite);
            } else
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