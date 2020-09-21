public class LinealPerceptron {
    static double LEARNING_RATE = 0.2;
    static double beta = 0.1;

    private double[] input;
    private int[] inputInt;
    // n for input variables and one for bias
    private double[] weights;
    private double theta;
    private double output;

    public LinealPerceptron(double[] input, double theta, double output) {
        this.input = input;
        this.theta = theta;
        // n for input variables and one for bias
        this.weights = new double[input.length + 1];
        this.output = output;
        this.generateWeights();
    }

    private static double g(double x) {
        return 1 / (1 + Math.exp(-2 * beta * x));
    }

    private static double g_prima(double x) {
        return 2 * beta * g(x) * (1 - g(x));
    }

    private void generateWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 0.01;
        }
    }

    private void updateWeight(double localError) {
        for (int i = 0; i < weights.length - 1; i++) {
            weights[i] -= LEARNING_RATE * localError * input[i];
        }
        weights[weights.length - 1] -= LEARNING_RATE * localError;
    }

    private void updateNoLinealWeight(double localError) {
        for (int i = 0; i < weights.length - 1; i++) {
            weights[i] -= LEARNING_RATE * localError * g_prima(localError) * input[i];
        }
        weights[weights.length - 1] -= LEARNING_RATE * localError;
    }

    public int execute(boolean update) {
        int outputAux = calculateOutput(theta,weights,input);
        double localError = outputAux - output;
        if (update) {
            this.updateWeight(localError);
        }
        return outputAux;
    }

    public void print() {
        System.out.println("\n=======\nWeights:");
        for (int i = 0; i < weights.length - 1; i++) {
            System.out.println("\nWeight " + i + " : " + weights[i]);
        }
        System.out.println("\nBias : " + weights[weights.length - 1]);
    }

    /**
     * returns either 1 or 0 using a threshold function
     * theta is 0range
     * @param theta an integer value for the threshold
     * @param weights the array of weights
     * @param input the array of inputs
     * @return 1 or 0
     */
    static int calculateOutput(double theta, double weights[], double input[])
    {
        double sum = 0;
        for (int i = 0; i < weights.length - 1; i++) {
            sum+= input[i] * weights[i];
        }
        sum+= weights[weights.length - 1];
        //System.out.println("\n=======\n"+ sum +"\n=======\n");
        return (sum >= theta) ? 1 : -1;
    }

    public void newValues(double[] input, double theta, double output) {
        this.output = output;
        this.input = input;
        this.theta = theta;
    }
}
