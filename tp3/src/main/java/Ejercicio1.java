import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Ejercicio1 {
    private static final double[][] logicNumbers = {{1,1,-1},{1,-1,-1},{-1,1,-1},{-1,-1,-1}};
    private static final double[] AND_OUTPUT = {1, -1, -1, -1};
    private static final double[] XOR_OUTPUT = {-1, 1, 1, -1};
    private static final String DEFAULT_OPERATOR = "AND";
    private static final int DEFAULT_ITERATIONS = 1000;

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("config.json"));
            String logicOperator = (String) data.get("logicOperator");
            if (logicOperator == null) {
                logicOperator = DEFAULT_OPERATOR;
                return;
            }
            int iterations;
            String iterationsString = (String) data.get("iterations");
            if (iterationsString == null) {
                iterations = DEFAULT_ITERATIONS;
                return;
            } else {
                iterations = Integer.parseInt(iterationsString);
            }
            PerceptronEscalonado perceptron;

            if (logicOperator.equals("XOR")) {
                System.out.println("\n********** XOR **********\n");
                perceptron = new PerceptronEscalonado(logicNumbers, XOR_OUTPUT);
                perceptron.train(iterations);
                System.out.println("********** Pesos Finales **********");
                perceptron.printWeights();
            } else {
                System.out.println("\n********** AND **********\n");
                perceptron = new PerceptronEscalonado(logicNumbers, AND_OUTPUT);
                perceptron.train();
                System.out.println("********** Pesos Finales **********");
                perceptron.printWeights();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP3 EJ1");
            return;
        }
        return;

    }
}