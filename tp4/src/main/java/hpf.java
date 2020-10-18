public class hpf {
    double[][] weights;
    double[] y;

    public hpf(int patternLength) {
        this.weights = new double[patternLength][patternLength];
    }

    public void startWeights() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights.length; j++) {
                this.weights[i][j] = Math.random();
            }
        }
    }


}
