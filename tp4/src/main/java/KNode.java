public class KNode {
    double[] weights;


    public KNode(int length,double[] input) {
        this.weights = new double[length];
        for (int i = 0; i < length; i++) {
            this.weights[i] = input[i];
        }
    }

    public double[] getWeights() {
        return weights;
    }

    public void updateWeights(double learningRate, double[] input) {
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] += learningRate * (input[i] - this.weights[i]);
        }
    }
}
