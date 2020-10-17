import java.util.ArrayList;

public class LinealPerceptronOja {
    private double[] weights;
    private double[][] inputs;
    private int inputLength;
    private double bias;
    private double rate;

    public LinealPerceptronOja(double[][] inputs, double rate) {
        this.inputs = inputs;
        this.inputLength = inputs[0].length;
        this.bias = Math.random();
        this.rate = rate;
        this.startWeights();
    }

    public LinealPerceptronOja(ArrayList<Double[]> inputs, double rate) {
        this.rate = rate;
        int i = 0;
        this.inputs = new double[inputs.size()][inputs.get(0).length];
        for (Double[] d: inputs) {
            for (int j = 0; j < d.length; j++) {
                this.inputs[i][j] = d[j];
            }
            i++;
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
            weights[i] += this.rate * ((y * inputs[x_i][i]) - (Math.pow(y, 2) * weights[i]));
        }
        this.bias += this.rate * y;
    }

    public void train(int iterations) {
        int index = 0;
        double yi = 0;
        while (iterations!=0) {
            while (index < inputs.length) {
                double sum = 0;
                for (int i = 0; i < inputLength; i++) {
                    sum += (weights[i] * inputs[index][i]);
                }
                sum += this.bias;
                yi = sum;
                calculateWeight(index, yi);
                //System.out.print("Output " + index + " = " + weights[index] + "\n");
                index++;
            }
            index = 0;
            iterations--;
        }

        for (int i = 0; i < weights.length; i++) {
            System.out.print("Output " + i + " = " + weights[i] + "\n");
        }
    }
}
