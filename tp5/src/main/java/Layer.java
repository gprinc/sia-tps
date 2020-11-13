import java.util.ArrayList;

public class Layer {
    private int _n_neurons, _prev_n_neurons;
    private ArrayList<Neuron> _neurons;
    private double _outputs[];

    public Layer(int prev_n_neurons, int n_neurons, java.util.Random rand) {
        // all the layers/neurons must use the same random number generator
        _n_neurons = n_neurons;
        _prev_n_neurons = prev_n_neurons;

        // allocate everything
        _neurons = new ArrayList<Neuron>();
        _outputs = new double[_n_neurons];

        for (int i = 0; i < _n_neurons; i++)
            _neurons.add(new Neuron(_prev_n_neurons, rand));
    }

    // add 1 in front of the out vector
    public static double[] add_bias(double[] in) {
        double out[] = new double[in.length];
        for (int i = 0; i < in.length; i++)
            out[i] = in[i];
        return out;
    }

    // compute the output of the layer
    public double[] evaluate(double in[]) {
        double inputs[];

        // add an input (bias) if necessary
        if (in.length != getWeights(0).length)
            inputs = add_bias(in);
        else
            inputs = in;

        assert(getWeights(0).length == inputs.length);

        // stimulate each neuron of the layer and get its output
        for (int i = 0; i < _n_neurons; i++)
            _outputs[i] = _neurons.get(i).activate(inputs);

        // bias treatment
        //_outputs[0] = 1.0;

        return _outputs;
    }

    public int size() { return _n_neurons; }
    public double getOutput(int i) { return _outputs[i]; }
    public double getActivationDerivative(int i) { return _neurons.get(i).getActivationDerivative(); }
    public double[] getWeights(int i) { return _neurons.get(i).getSynapticWeights(); }
    public double getWeight(int i, int j) { return _neurons.get(i).getSynapticWeight(j); }
    public void setWeight(int i, int j, double v) { _neurons.get(i).setSynapticWeight(j, v); }
}