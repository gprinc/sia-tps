public class Neuron {
    private float _activation;
    private float[] _synapticWeights;

    // parameter of the sigmoid
    static final float lambda = 1.5f;

    public Neuron(int prev_n_neurons, java.util.Random rand) {
        // each neuron knows the weights of each connection with neurons of the previous layer
        _synapticWeights = new float[prev_n_neurons];

        // set default weights
        for (int i = 0; i < prev_n_neurons; i++)
            _synapticWeights[i] = rand.nextFloat() - 0.5f;
    }

    // activate the neuron with given inputs, return the output
    public float activate(float inputs[]) {
        _activation = 0.0f;
        assert(inputs.length == _synapticWeights.length);

        for (int i = 0; i < inputs.length; i++)
            _activation += inputs[i] * _synapticWeights[i];

        // activation function (tanh(x))
        //return 2.0f / (1.0f + (float) Math.exp((-_activation) * lambda)) - 1.0f;
        return (float) Math.tanh((_activation) * lambda);
    }

    // dphi(_activation)
    public float getActivationDerivative() {
        float expmlx = (float) Math.exp(lambda * _activation);
        return 2 * lambda * expmlx / ((1 + expmlx) * (1 + expmlx));
    }

    public float[] getSynapticWeights() { return _synapticWeights; }
    public float getSynapticWeight(int i) { return _synapticWeights[i]; }
    public void setSynapticWeight(int i, float v) { _synapticWeights[i] = v; }
}
