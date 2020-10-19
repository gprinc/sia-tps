public class KNode {
    double[] weights;


    public KNode(int length) {
        this.weights = new double[length];
        for (int i = 0; i < length; i++) {
            this.weights[i] = Math.random();
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
