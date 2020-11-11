import java.util.ArrayList;
import java.util.Random;

public class MultiLayerPerceptron {
    private ArrayList<Layer> layers;
    private ArrayList<double[][]> deltaW;
    private ArrayList<double[]> gradEx;
    private double[][] finalOutput;
    private double[][] middleOutput;

    public MultiLayerPerceptron(int nn_neurons[]) {
        Random rand = new Random();

        // create the required layers
        layers = new ArrayList<Layer>();
        for (int i = 0; i < nn_neurons.length; i++){
            layers.add(new Layer(i == 0 ? nn_neurons[i] : nn_neurons[i - 1], nn_neurons[i], rand));
        }

        deltaW = new ArrayList<double[][]>();
        for (int i = 0; i < nn_neurons.length; i++) {
            deltaW.add(new double [layers.get(i).size()] [layers.get(i).getWeights(0).length]);
        }

        gradEx = new ArrayList<double[]>();
        for (int i =  0; i < nn_neurons.length; i++) {
            gradEx.add(new double[layers.get(i).size()]);
        }
    }

    public double[] evaluate(double[] inputs, int j) {
        // propagate the inputs through all neural network and return the outputs
        assert(false);

        double outputs[] = new double[inputs.length];

        for( int i = 0; i < layers.size(); i++) {
            outputs = layers.get(i).evaluate(inputs);
            inputs = outputs;
            if ((i == (layers.size() / 2)) || (i == ((layers.size() + 1) / 2))) {
                middleOutput[j] = outputs;
            }
        }

        return outputs;
    }

    private double evaluateError(double nn_output[], double desiredOutput[]) {
        double d[];

        // add bias to input if necessary
        if (desiredOutput.length != nn_output.length)
            d = Layer.add_bias(desiredOutput);
        else
            d = desiredOutput;

        assert(nn_output.length == d.length);

        double error = 0;
        for (int i = 0; i < nn_output.length; i++)
            error += (nn_output[i] - d[i]) * (nn_output[i] - d[i]);
        //System.out.println(error/(nn_output.length* nn_output.length));
        return error / nn_output.length;
    }

    public double evaluateQuadraticError(ArrayList<double[]> input, ArrayList<double[]> output) {
        // this function calculate the quadratic error for the given inputs/outputs sets
        assert(false);

        double error = 0;

        finalOutput = new double[input.size()][];
        middleOutput = new double[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            double[] j = evaluate(input.get(i), i);
            finalOutput[i] = j;
            error += evaluateError(j, output.get(i));
        }

        return error / input.size();
    }


    public double evaluateAccuracy(ArrayList<double[]> input, ArrayList<double[]> output, double umbral) {
        // this function calculate the Acurracy
        assert(false);
        double accuracy=0;
        finalOutput = new double[input.size()][];
        middleOutput = new double[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            double[] j = evaluate(input.get(i), i);
            // TODO rodri esto esta bien?
            // finalOutput[i] = j[j.length - 1];
            finalOutput[i] = j;
            if (evaluateError(j, output.get(i))/input.size() < umbral) {
                accuracy++;
            }
        }

        return accuracy / input.size();
    }

    private void evaluateGradients(double[] output) {
        // for each neuron in each layer
        for (int c = layers.size()-1; c >= 0; c--) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                // if it's output layer neuron
                if (c == layers.size()-1) {
                    gradEx.get(c)[i] =
                            2 * (layers.get(c).getOutput(i) - output[0])
                                    * layers.get(c).getActivationDerivative(i);
                }
                else { // if it's neuron of the previous layers
                    double sum = 0;
                    for (int k = 1; k < layers.get(c+1).size(); k++)
                        sum += layers.get(c+1).getWeight(k, i) * gradEx.get(c+1)[k];
                    gradEx.get(c)[i] = layers.get(c).getActivationDerivative(i) * sum;
                }
            }
        }
    }

    private void resetWeightsDelta() {
        // reset delta values for each weight
        for (int c = 0; c < layers.size(); c++) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                double weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    deltaW.get(c)[i][j] = 0;
            }
        }
    }

    private void evaluateWeightsDelta() {
        // evaluate delta values for each weight
        for (int c = 1; c < layers.size(); c++) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                double weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    deltaW.get(c)[i][j] += gradEx.get(c)[i]
                            * layers.get(c-1).getOutput(j);
            }
        }
    }

    private void updateWeights(double learningRate) {
        // update values for each weight
        for (int c = 0; c < layers.size(); c++) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                double weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    layers.get(c).setWeight(i, j, layers.get(c).getWeight(i, j)
                            - (learningRate * deltaW.get(c)[i][j]));
            }
        }
    }

    private void batchBackPropagation(ArrayList<double[]> input, ArrayList<double[]> output, double learningRate) {
        resetWeightsDelta();

        for (int l = 0; l < input.size(); l++) {
            evaluate(input.get(l), l);
            evaluateGradients(output.get(l));
            evaluateWeightsDelta();
        }

        updateWeights(learningRate);
    }

    public double learn(ArrayList<double[]> input, ArrayList<double[]> output, double learningRate, int iter, double threshold) {
        assert(false);
        double generalError = 0;
        double error = Double.POSITIVE_INFINITY;
        middleOutput = new double[input.size()][];
        
        int iterations = 0;
        while (error > threshold && iterations < iter) {

            batchBackPropagation(input, output, learningRate);

            error = evaluateQuadraticError(input, output);
            generalError += error;
            iterations++;
        }

        return generalError / input.size();
    }

    public double[][] getOutput(){
        return finalOutput;
    }

    public double[][] getMiddleOutput(){
        return middleOutput;
    }
}