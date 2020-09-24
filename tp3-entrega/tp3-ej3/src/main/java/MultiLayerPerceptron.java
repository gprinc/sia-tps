import java.util.ArrayList;
import java.util.Random;

public class MultiLayerPerceptron {
    private ArrayList<Layer> layers;
    private ArrayList<float[][]> deltaW;
    private ArrayList<float[]> gradEx;
    private float[] finalOutput;

    public MultiLayerPerceptron(int nn_neurons[]) {
        Random rand = new Random();

        // create the required layers
        layers = new ArrayList<Layer>();
        for (int i = 0; i < nn_neurons.length; i++){
            layers.add(new Layer(i == 0 ? nn_neurons[i] : nn_neurons[i - 1], nn_neurons[i], rand));
        }

        deltaW = new ArrayList<float[][]>();
        for (int i = 0; i < nn_neurons.length; i++) {
            deltaW.add(new float [layers.get(i).size()] [layers.get(i).getWeights(0).length]);
        }

        gradEx = new ArrayList<float[]>();
        for (int i =  0; i < nn_neurons.length; i++) {
            gradEx.add(new float[layers.get(i).size()]);
        }
    }

    public float[] evaluate(float[] inputs) {
        // propagate the inputs through all neural network and return the outputs
        assert(false);

        float outputs[] = new float[inputs.length];

        for( int i = 0; i < layers.size(); i++) {
            outputs = layers.get(i).evaluate(inputs);
            inputs = outputs;
        }

        return outputs;
    }

    private float evaluateError(float nn_output[], float desiredOutput[]) {
        float d[];

        // add bias to input if necessary
        if (desiredOutput.length != nn_output.length)
            d = Layer.add_bias(desiredOutput);
        else
            d = desiredOutput;

        assert(nn_output.length == d.length);

        float error = 0;
        for (int i = 0; i < nn_output.length; i++)
            error += (nn_output[i] - d[i]) * (nn_output[i] - d[i]);

        return error;
    }

    public float evaluateQuadraticError(ArrayList<float[]> input, ArrayList<float[]> output) {
        // this function calculate the quadratic error for the given inputs/outputs sets
        assert(false);

        float error = 0;

        finalOutput = new float[input.size()];
        for (int i = 0; i < input.size(); i++) {
            float[] j = evaluate(input.get(i));
            finalOutput[i] = j[j.length - 1];
            error += evaluateError(j, output.get(i));
        }

        return error;
    }


    public float evaluateAccuracy(ArrayList<float[]> input, ArrayList<float[]> output, float umbral) {
        // this function calculate the Acurracy
        assert(false);
        float accuracy=0;
        finalOutput = new float[input.size()];
        for (int i = 0; i < input.size(); i++) {
            float[] j = evaluate(input.get(i));
            finalOutput[i] = j[j.length - 1];
            if (evaluateError(j, output.get(i)) < umbral) {
                accuracy++;
            }

        }

        return accuracy / input.size();
    }

    private void evaluateGradients(float[] output) {
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
                    float sum = 0;
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
                float weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    deltaW.get(c)[i][j] = 0;
            }
        }
    }

    private void evaluateWeightsDelta() {
        // evaluate delta values for each weight
        for (int c = 1; c < layers.size(); c++) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                float weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    deltaW.get(c)[i][j] += gradEx.get(c)[i]
                            * layers.get(c-1).getOutput(j);
            }
        }
    }

    private void updateWeights(float learningRate) {
        // update values for each weight
        for (int c = 0; c < layers.size(); c++) {
            for (int i = 0; i < layers.get(c).size(); i++) {
                float weights[] = layers.get(c).getWeights(i);
                for (int j = 0; j < weights.length; j++)
                    layers.get(c).setWeight(i, j, layers.get(c).getWeight(i, j)
                            - (learningRate * deltaW.get(c)[i][j]));
            }
        }
    }

    private void batchBackPropagation(ArrayList<float[]> input, ArrayList<float[]> output, float learningRate) {
        resetWeightsDelta();

        for (int l = 0; l < input.size(); l++) {
            evaluate(input.get(l));
            evaluateGradients(output.get(l));
            evaluateWeightsDelta();
        }

        updateWeights(learningRate);
    }

    public float learn(ArrayList<float[]> input, ArrayList<float[]> output, float learningRate, int iter) {
        assert(false);
        float generalError = 0;
        float error = Float.POSITIVE_INFINITY;

        int iterations = 0;
        while (error > 0.1f && iterations < iter) {

            batchBackPropagation(input, output, learningRate);

            error = evaluateQuadraticError(input, output);
            generalError+= error;
            iterations++;
        }

        return generalError / input.size();
    }

    public float[] getOutput(){
        return finalOutput;
    }
}