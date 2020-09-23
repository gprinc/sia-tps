import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Ejercicio2 {
    private static final int DEFAULT_SUBTASK = 0;
    private static final double DEFAULT_THRESHOLD = 0.001d;
    private static final double DEFAULT_RATE = 0.001d;
    private static final double DEFAULT_BETA = 0.5;

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
        String auxData = (String) data.get("subtask");
        int subtask;
        if (auxData == null)
            subtask = DEFAULT_SUBTASK;
        else
            subtask = Integer.parseInt(auxData) - 1;
        auxData = (String) data.get("threshold");
        double threshold;
        if (auxData == null)
            threshold = DEFAULT_THRESHOLD;
        else
            threshold = Double.parseDouble(auxData);
        auxData = (String) data.get("rate");
        double rate;
        if (auxData == null)
            rate = DEFAULT_RATE;
        else
            rate = Double.parseDouble(auxData);
        auxData = (String) data.get("beta");
        double beta;
        if (auxData == null)
            beta = DEFAULT_BETA;
        else
            beta = Double.parseDouble(auxData);

        long start = System.nanoTime();
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        File file2 = new File("TP3-ej2-Salida-deseada.txt");
        ArrayList<Double> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2);

        double k = 0.8;
        aux2 = normalize(aux2);
        NonLinealPerceptron NoLinealPer;
        switch (subtask) {
            case 0:
                int iterations = 10000;
                double linearError = 0;
                double noLinearError = 0;
                LinealPerceptron linearPer = new LinealPerceptron(aux, aux2, threshold, rate);
                linearPer.train(iterations);
                linearError = linearPer.test();
                NoLinealPer = new NonLinealPerceptron(aux, aux2, threshold, rate, beta);
                NoLinealPer.train(iterations);
                noLinearError = NoLinealPer.test();
                System.out.print(" => Despues de 10000 iteraciones\n => Error no Lineal " + noLinearError + ", Error Lineal = " + linearError + "\n");
                break;
            case 1:
                noLinearTest(aux,aux2,k,threshold, rate, beta);
                break;
        }

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
            // rest of data

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;

    }

    public static ArrayList<Double> normalize(ArrayList<Double> arr){

        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;

        for (int i = 0; i < arr.size(); i++) {
            max = Math.max(arr.get(i), max);
            min = Math.min(arr.get(i),min);
        }

        for (int i = 0; i < arr.size(); i++) {
            arr.set(i, (arr.get(i) - min)/(max-min));
        }

        return arr;
    }

    public static void noLinearTest(ArrayList<Double[]> inputs, ArrayList<Double> outputs,double k, double threshold, double rate, double beta) {
        ArrayList<Double[]> aux = new ArrayList<>();
        for (int i = 0; i < outputs.size(); i++) {
            Double[] value = new Double[4];
            for (int j = 0; j < 3; j++) {
                value[j] = inputs.get(i)[j];
            }
            value[3] = outputs.get(i);
            aux.add(value);
        }

        Collections.shuffle(aux);
        ArrayList<ArrayList<Double[]>> values = new ArrayList<>();
        if (k < 1) {
            ArrayList<Double[]> aux2 = new ArrayList<>();
            for (Double [] a: aux.subList(0,(int)((k)* aux.size()))) {
                aux2.add(a);
            }
            values.add(aux2);
            aux2 = new ArrayList<>();
            for (Double [] a: aux.subList((int)((k)* aux.size()), aux.size())) {
                aux2.add(a);
            }
            values.add(aux2);
        } else {
            for (int i = 0; i < k; i++) {
                ArrayList<Double[]> aux2 = new ArrayList<>();
                for (Double [] a: aux.subList( (int) (i * aux.size()/k), (int) ((1 + i)* aux.size()/k))) {
                    aux2.add(a);
                }
                values.add(aux2);
            }
        }


        for (int i = 0; i < values.size(); i++) {
            ArrayList<Double[]> inputsAux = new ArrayList<>();
            ArrayList<Double> outputAux = new ArrayList<>();
            for (int j = 0; j < values.get(i).size(); j++) {
                Double[] auxValues = new Double[3];
                auxValues[0] = values.get(i).get(j)[0];
                auxValues[1] = values.get(i).get(j)[1];
                auxValues[2] = values.get(i).get(j)[2];
                inputsAux.add(auxValues);
                outputAux.add(values.get(i).get(j)[3]);
            }

            NonLinealPerceptron NoLinealPer = new NonLinealPerceptron(inputsAux, outputAux, threshold, rate, beta);

            ArrayList<Double> trainError = new ArrayList<>();
            ArrayList<Double> testError = new ArrayList<>();
            ArrayList<Double[]> inputsTrain = new ArrayList<>();
            ArrayList<Double> outputTrain= new ArrayList<>();

            for (Double[] a :inputsAux) {
                inputsTrain.add(a);
            }

            for (Double a :outputAux) {
                outputTrain.add(a);
            }

            for (int w = 0; w < 10; w++) {
                trainError.add(NoLinealPer.train(1000));
                for (int j = 0; j < values.size(); j++) {
                    if (j!=i) {
                        inputsAux = new ArrayList<>();
                        outputAux = new ArrayList<>();
                        for (int z = 0; z < values.get(j).size(); z++) {
                            Double[] auxValues = new Double[3];
                            auxValues[0] = values.get(i).get(j)[0];
                            auxValues[1] = values.get(i).get(j)[1];
                            auxValues[2] = values.get(i).get(j)[2];
                            inputsAux.add(auxValues);
                            outputAux.add(values.get(i).get(j)[3]);
                        }
                        NoLinealPer.setValues(inputsAux,outputAux);
                        testError.add(NoLinealPer.test());
                    }
                }
                NoLinealPer.setValues(inputsTrain,outputTrain);
            }

            for (double e :trainError) {
                System.out.print(" => Error = " + e + " ");
            }
            System.out.println("\n");

            for (double e :testError) {
                System.out.print(" => Error = " + e + " ");
            }

            System.out.println("\n");

        }
    }
}
