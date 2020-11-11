public class Neuron {
    private double _activation;
    private double[] _synapticWeights;
    private int auxM = 0;

    // parameter of the sigmoid
    static final double lambda = 1.5;

    public Neuron(int prev_n_neurons, java.util.Random rand) {
        // each neuron knows the weights of each connection with neurons of the previous layer
        _synapticWeights = new double[prev_n_neurons];

        // set default weights
        for (int i = 0; i < prev_n_neurons; i++)
            _synapticWeights[i] = rand.nextDouble() - 0.5;
    }

    // activate the neuron with given inputs, return the output
    public double activate(double inputs[]) {
        _activation = 0.0;
        assert(inputs.length == _synapticWeights.length);

        for (int i = 0; i < inputs.length; i++)
            _activation += inputs[i] * _synapticWeights[i];

        //return 2.0 / (1.0 + Math.exp((-_activation) * lambda)) - 1.0;
        switch (auxM) {
            case 0:
                return Math.tanh((_activation) * lambda);
            case 1:
                return reLu(_activation);
            case 2:
                return leaky_relu(_activation);
            default:
                return soft_plus(_activation);
        }
    }

    // dphi(_activation)
    public double getActivationDerivative() {
        switch (auxM) {
            case 0:
                double expmlx = Math.exp(lambda * _activation);
                return 2 * lambda * expmlx / ((1 + expmlx) * (1 + expmlx));
            case 1:
                return reLuDerivate(_activation);
            case 2:
                return leaky_relu_derivate(_activation);
            default:
                return soft_plus_derivate(_activation);
        }
    }

    public double[] getSynapticWeights() { return _synapticWeights; }
    public double getSynapticWeight(int i) { return _synapticWeights[i]; }
    public void setSynapticWeight(int i, double v) { _synapticWeights[i] = v; }

    private double reLu(double _activation) {
        return _activation > 0 ? _activation : 0;
    }

    private double reLuDerivate(double _activation) {
        return _activation > 0 ? 1 : 0;
    }

    private double leaky_relu(double _activation)
    {
        if (_activation >= 0)
            return _activation;
        else
            return _activation * 0.01;
    }

    private double leaky_relu_derivate(double _activation)
    {
        if (_activation >= 0)
            return 1;
        else
            return 0.01;
    }

    private double soft_plus(double _activation)
    {
        // f(x) = ln(1+exp x)
        return Math.log((1+ Math.exp(_activation)));
    }

    private double soft_plus_derivate(double _activation)
    {
        // exp(x) / ( 1+exp(x) )
        return (1 / (1 + Math.exp((_activation * -1))));
    }
}
