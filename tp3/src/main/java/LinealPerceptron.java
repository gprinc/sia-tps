import java.util.ArrayList;

public class LinealPerceptron {
    private double[] weights;
    private double[] outputs;
    private double[][] inputs;
    private int inputLength;
    private static final double LEARNING_RATE = 0.01d;
    private static final double THRESHOLD = 0.01d;
    private double bias;

    public LinealPerceptron(double[][] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.inputLength = inputs[0].length;
        this.bias = Math.random();
        this.startWeights();
    }

    public LinealPerceptron(ArrayList<Double[]> inputs, ArrayList<Double> outputs) {
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
            weights[i] += LEARNING_RATE * (outputs[x_i] - y) * inputs[x_i][i];
        }
        this.bias += LEARNING_RATE * (outputs[x_i] - y);
    }

    public void train() {
        int index = 0;
        double yi = 0;
        boolean gotError = true;
        while(gotError) {
            gotError = false;
            while (index < inputs.length) {
                double sum = 0;
                for (int i = 0; i < inputLength; i++) {
                    sum += (weights[i] * inputs[index][i]);
                }
                sum += this.bias;
                yi = sum;
                if (Math.abs(yi - outputs[index]) < THRESHOLD) {
                    for (int i = 0; i < inputLength; i++) {
                        System.out.print(inputs[index][i] + "\t");
                    }
                    System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + yi + "\n");
                } else {
                    gotError = true;
                    for (int i = 0; i < inputLength; i++) {
                        System.out.print(inputs[index][i] + "\t");
                    }
                    System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + yi + " [Error]\n");
                    System.out.println("Corrección de pesos");
                    calculateWeight(index, yi);
                    printWeights();
                    System.out.println("--");
                }
                index++;
            }
            index = 0;
        }
    }

    public void train(int iterations) {
        int index = 0;
        double yi = 0;
        double error = 100000;
        while (error > THRESHOLD && iterations!=0) {
            error = 0;
            while (index < inputs.length) {
                double sum = 0;
                for (int i = 0; i < inputLength; i++) {
                    sum += (weights[i] * inputs[index][i]);
                }
                sum += this.bias;
                yi = sum;
                calculateWeight(index, yi);
                System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + yi + "\n");
                error += Math.abs(outputs[index] - yi);
                index++;
            }
            error = error/inputs.length;
            index = 0;
            iterations--;
        }
    }

    public double test() {
        int index = 0;
        double yi = 0;
        double error = 0;
        while (index < inputs.length) {
            double sum = 0;
            for (int i = 0; i < inputLength; i++) {
                sum += (weights[i] * inputs[index][i]);
            }
            sum += this.bias;
            yi = sum;
            //System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + g(yi) + "\n");
            error += Math.abs(outputs[index] - yi);
            index++;
        }

        //System.out.print(" => Error = " + error/inputs.length + "\n");
        return error;
    }
}
