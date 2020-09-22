import java.io.File;
import java.util.ArrayList;

public class Ejercicio2 {
    public static void main(String[] args) {
        File file = new File("TP3-ej2-Conjunto-entrenamiento.txt");
        ArrayList<Double[]> aux = new ArrayList<>();
        aux = TxtReader.getDoubleArrayFromTxt(file, 3);

        File file2 = new File("TP3-ej2-Salida-deseada.txt");
        ArrayList<Double> aux2 = new ArrayList<>();
        aux2 = TxtReader.getDoubleArrayFromTxt(file2);

        LinealPerceptron linearPer = new LinealPerceptron(aux, aux2);
        linearPer.train();



        NonLinealPerceptron NoLinealPer = new NonLinealPerceptron(aux, aux2);
        NoLinealPer.train();
        return;

    }
}
