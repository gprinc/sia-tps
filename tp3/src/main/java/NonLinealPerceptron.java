public class NonLinealPerceptron {
    private double[] weights;
    private double[] outputs;
    private double[][] inputs;
    private int inputLength;
    private static final double LEARNING_RATE = 0.5d;
    private static final double BETA = 0.1d;

    public NonLinealPerceptron(double[][] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.inputLength = inputs[0].length;
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
            weights[i] = weights[i] + LEARNING_RATE * g(outputs[x_i] - y) * inputs[x_i][i] * g_prima(outputs[x_i] - y);
        }
    }

    public void train() {
        int index = 0;
        double yi = 0;
        while (index < inputs.length) {
            double sum = 0;
            for (int i = 0; i < inputLength; i++) {
                sum += (weights[i] * inputs[index][i]);//&#8721; x[i] * W[i]
            }
            yi = sum >= 0 ? 1 : -1;
            if (yi == outputs[index]) {
                //Correcto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "t");
                }
                System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + yi + "n");
            } else {
                //Incorrecto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "t");
                }
                System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + yi + " [Error]n");
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
