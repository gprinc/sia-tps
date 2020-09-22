import sun.security.util.ArrayUtil;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Ejercicio2 {
    public static void main(String[] args) {
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        File file2 = new File("TP3-ej2-Salida-deseada.txt");
        ArrayList<Double> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2);
        /*
        LinealPerceptron linearPer = new LinealPerceptron(aux, aux2);
        linearPer.train(1000);
        linearPer.train(1);
        +*/

        aux2 = normalize(aux2);
        NonLinealPerceptron NoLinealPer = new NonLinealPerceptron(aux, aux2);
        NoLinealPer.train(1000000000);
        NoLinealPer.train(1);
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
}
