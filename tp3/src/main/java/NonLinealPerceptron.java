import java.util.ArrayList;

public class NonLinealPerceptron {
    private double[] weights;
    private double[] outputs;
    private double[][] inputs;
    private int inputLength;
    private double bias;
    private double threshold;
    private double rate;
    private double beta;

    public NonLinealPerceptron(double[][] inputs, double[] outputs, double threshold, double rate, double beta) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.inputLength = inputs[0].length;
        this.bias = Math.random();
        this.threshold = threshold;
        this.rate = rate;
        this.beta = beta;
        this.startWeights();
    }

    public NonLinealPerceptron(ArrayList<Double[]> inputs, ArrayList<Double> outputs, double threshold, double rate, double beta) {
        this.threshold = threshold;
        this.rate = rate;
        this.beta = beta;
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
        return 1 / (1 + Math.exp(-2 * this.beta * x));
    }

    private double g_prima(double x) {
        return 2 * this.beta * g(x) * (1 - g(x));
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
            weights[i] = weights[i] + this.rate * (outputs[x_i] - g(y)) * inputs[x_i][i] * g_prima(y);
        }
        this.bias += this.rate * (outputs[x_i] - g(y)) * g_prima(y) ;
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
            if (Math.abs(g(yi) - outputs[index]) < this.threshold) {
                //Correcto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "\t");
                }
                System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + g(yi) + "\n");
            } else {
                //Incorrecto
                for (int i = 0; i < inputLength; i++) {
                    System.out.print(inputs[index][i] + "\t");
                }
                System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + g(yi) + " [Error]\n");
                System.out.println("Corrección de pesos");
                calculateWeight(index, yi);
                printWeights();
                System.out.println("--");
                index = -1;
            }
            index++;
        }
    }

    public double train(int iterations) {
        int index = 0;
        double yi = 0;
        double error = 1;
        while (error > this.threshold && iterations!=0) {
            error = 0;
            while (index < inputs.length) {
                double sum = 0;
                for (int i = 0; i < inputLength; i++) {
                    sum += (weights[i] * inputs[index][i]);
                }
                sum += this.bias;
                yi = sum;
                calculateWeight(index, yi);
                //System.out.print(" => Esperada = " + outputs[index] + ", Calculada = " + g(yi) + "\n");
                error += Math.abs(outputs[index] - g(yi));
                index++;
            }
            error = error/inputs.length;
            //System.out.print(" => Error = " + error + "\n");
            index = 0;
            iterations--;
        }

        return error;
    }

    public void setValues(ArrayList<Double[]> inputs, ArrayList<Double> outputs) {
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
            error += Math.abs(outputs[index] - g(yi));
            index++;
        }

        //System.out.print(" => Error = " + error/inputs.length + "\n");
        return error/inputs.length;
    }
}
