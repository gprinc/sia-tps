import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;

class Perceptron
{
    static double LEARNING_RATE = 0.1;

    private double[] input;
    private int[] inputInt;
    // n for input variables and one for bias
    private double[] weights;
    private double theta;
    private double output;

    public Perceptron(double[] input, double theta, double output) {
        this.input = input;
        this.theta = theta;
        // n for input variables and one for bias
        this.weights = new double[input.length + 1];
        this.output = output;
        this.generateWeights();
    }

    public Perceptron(int[] input, double theta, double output) {
        this.inputInt = input;
        this.theta = theta;
        // n for input variables and one for bias
        this.weights = new double[input.length + 1];
        this.output = output;
        this.generateWeights();
    }

    private void generateWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1/weights.length;
        }
    }

    private void updateWeight(double localError) {
        for (int i = 0; i < weights.length - 1; i++) {
            weights[i] += LEARNING_RATE * localError * input[i];
        }
    }

    public void execute() {
        int outputAux = calculateOutput(theta,weights,input);
        double localError = outputAux - output;
        this.updateWeight(localError);
    }

    public void print() {
        System.out.println("\n=======\nWeights:");
        for (int i = 0; i < weights.length - 1; i++) {
            System.out.println("\n " + i + ": " + weights[i]);
        }
    }

    /**
     * returns either 1 or 0 using a threshold function
     * theta is 0range
     * @param theta an integer value for the threshold
     * @param weights the array of weights
     * @param input the array of weights
     * @return 1 or 0
     */
    static int calculateOutput(double theta, double weights[], double input[])
    {
        double sum = 0;
        for (int i = 0; i < weights.length - 1; i++) {
            sum+= input[i] * weights[i];
        }
        sum+= weights[weights.length - 1];
        return (sum >= theta) ? 1 : 0;
    }

}