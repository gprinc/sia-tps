import java.io.File;
import java.util.ArrayList;

public class Ejercicio1 {
    private static final double[][] logicNumbers = {{1,1,-1},{1,-1,-1},{-1,1,-1},{-1,-1,-1}};
    private static final double[] AND_OUTPUT = {1, -1, -1, -1};
    private static final double[] XOR_OUTPUT = {-1, 1, 1, -1};

    public static void main(String[] args) {
        System.out.println("\n********** AND **********\n");
        PerceptronEscalonado perceptron = new PerceptronEscalonado(logicNumbers, AND_OUTPUT);
        perceptron.train();
        System.out.println("********** Pesos Finales **********");
        perceptron.printWeights();

        System.out.println("\n********** XOR **********\n");
        perceptron = new PerceptronEscalonado(logicNumbers, XOR_OUTPUT);
        perceptron.train(1000);
        System.out.println("********** Pesos Finales **********");
        perceptron.printWeights();
        return;

    }
}
