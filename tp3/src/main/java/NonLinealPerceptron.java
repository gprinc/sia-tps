public class NonLinealPerceptron {
    private double[] weights;
    private double[] outputs;
    private double[][] inputs;
    private int inputLength;
    private static final double LEARNING_VALUE = 0.5d;

    public double[][] getInputs() {
        return inputs;
    }

    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
        this.inputLength = inputs[0].length;
    }
    public double[] getOutputs() {
        return outputs;
    }

    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
    }
    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    /**
     * Inicializar los pesos sinápticos con números aleatorios del intervalo [-1, 1]
     */
    public void startWeights() {
        weights = new double[inputLength];
        for (int i = 0; i < inputLength; i++) {
            weights[i] = Math.random();
        }
    }

    public void printWeights() {
        for (int i = 0; i < inputLength; i++) {
            System.out.println("W[" + i + "] = " + weights[i]);
        }
    }

    /**
     * wj(k+1)=wj(k)+&#951;[z(k)&#8722;y(k)]xj(k), j =1,2,...,n+1
     */
    public void calculateWeight(int posicionEntrada, double y) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + LEARNING_VALUE * (outputs[posicionEntrada] - y) * inputs[posicionEntrada][i];
        }
    }

    public void train() {
        int index = 0;
        double yi = 0;
        while (index < inputs.length) {
            double suma = 0;
            for (int i = 0; i < inputLength; i++) {
                suma += (weights[i] * inputs[index][i]);//&#8721; x[i] * W[i]
            }
            yi = suma >= 0 ? 1 : -1;
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
