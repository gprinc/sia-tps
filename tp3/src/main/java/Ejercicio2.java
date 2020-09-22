import sun.security.util.ArrayUtil;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Ejercicio2 {
    public static void main(String[] args) {
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        File file2 = new File("TP3-ej2-Salida-deseada.txt");
        ArrayList<Double> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2);

        double k = 2;
        aux2 = normalize(aux2);
        int subtask = 1;
        NonLinealPerceptron NoLinealPer;
        switch (subtask) {
            case 0:
                int iterations = 10000;
                double linearError = 0;
                double noLinearError = 0;
                LinealPerceptron linearPer = new LinealPerceptron(aux, aux2);
                linearPer.train(iterations);
                linearError = linearPer.test();
                NoLinealPer = new NonLinealPerceptron(aux, aux2);
                NoLinealPer.train(iterations);
                noLinearError = NoLinealPer.test();
                System.out.print(" => Despues de 10000 iteraciones\n => Error no Lineal " + noLinearError + ", Error Lineal = " + linearError + "\n");
                break;
            case 1:
                noLinearTest(aux,aux2,k);
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

    public static void noLinearTest(ArrayList<Double[]> inputs, ArrayList<Double> outputs,double k) {
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
                Double[] auxValues = new Double[2];
                auxValues[0] = values.get(i).get(j)[0];
                auxValues[1] = values.get(i).get(j)[1];
                inputsAux.add(auxValues);
                outputAux.add(values.get(i).get(j)[2]);
            }

            NonLinealPerceptron NoLinealPer = new NonLinealPerceptron(inputsAux, outputAux);

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
                trainError.add(NoLinealPer.train(10000));
                for (int j = 0; j < values.size(); j++) {
                    if (j!=i) {
                        inputsAux = new ArrayList<>();
                        outputAux = new ArrayList<>();
                        for (int z = 0; z < values.get(j).size(); z++) {
                            Double[] auxValues = new Double[2];
                            auxValues[0] = values.get(j).get(z)[0];
                            auxValues[1] = values.get(j).get(z)[1];
                            inputsAux.add(auxValues);
                            outputAux.add(values.get(j).get(z)[2]);
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
        }
    }
}
