import java.util.ArrayList;

public class NonLinealPerceptron {
    private double[] weights;
    private double[] outputs;
    private double[][] inputs;
    private int inputLength;
    private static final double LEARNING_RATE = 0.0001d;
    private static final double THRESHOLD = 0.01d;
    private static final double BETA = 1.0d;
    private double bias;

    public NonLinealPerceptron(double[][] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.inputLength = inputs[0].length;
        this.bias = Math.random();
        this.startWeights();
    }

    public NonLinealPerceptron(ArrayList<Double[]> inputs, ArrayList<Double> outputs) {
        int i = 0;
        this.inputs = new double[inputs.size()][inputs.get(0).length];
        for (Double[] d: inputs) {
            for (int j = 0; j < d.length; j++) {
                this.inputs[i][j] = d[j];
            }
            i++;
        }
        this.outputs = new double[outputs.size()];
        i = 0;
        for (Double d: outputs) {
            this.outputs[i++] = d;
        }
        this.inputLength = inputs.get(0).length;
        this.bias = Math.random();
        this.startWeights();
    }

    private double g(double x) {
        return 1 / (1 + Math.exp(-2 * BETA * x));
    }

    private double g_prima(double x) {
        return 2 * BETA * g(x) * (1 - g(x));
    }

    public void startWeights() {
        this.weights = new double[inputLength];
        for (int i = 0; i < inputLength; i++) {
            this.weights[i] = Math.random();
        }
    }

    public void printWeights() {
        for (int i = 0; i < inputLength; i++) {
            System.out.println("W[" + i + "] = " + weights[i]);
        }
    }

    public void calculateWeight(int x_i, double y) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + LEARNING_RATE * (g(outputs[x_i])-g(y)) * inputs[x_i][i] * g_prima(y);
        }
        this.bias += LEARNING_RATE * (g(outputs[x_i])-g(y)) * g_prima(y) ;
    }

    public void train() {
        int index = 0;
        double yi = 0;
        while (index < inputs.length) {
            double sum = 0;
            for (int i = 0; i < inputLength; i++) {
                sum += (weights[i] * inputs[index][i]);
            }
            sum += this.bias;
            yi = sum;
            if (Math.abs(g(yi) - g(outputs[index])) < THRESHOLD) {
                //Correcto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "\t");
                }
                System.out.print(" => Esperada = " + g(outputs[index]) + ", Calculada = " + g(yi) + "\n");
            } else {
                //Incorrecto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "\t");
                }
                System.out.print(" => Esperada = " + g(outputs[index]) + ", Calculada = " + g(yi) + " [Error]\n");
                System.out.println("Corrección de pesos");
                calculateWeight(index, yi);
                printWeights();
                System.out.println("--");
                index = -1;
            }
            index++;
        }
    }
}
