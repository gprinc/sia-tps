import java.util.ArrayList;

public class Kohonen {
    KNode[][] nodes;
    double environment;
    double learningRate;
    int iteration;
    double delta;

    public Kohonen(int k, int length, double environment, double learningRate, double delta) {
        this.nodes = new KNode[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                nodes[i][j] = new KNode(length);
            }
        }
        this.environment = environment;
        this.learningRate = learningRate;
        this.iteration = 0;
        this.delta = delta;
    }

    private double distance(double[] a, double[] b) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(diff_square_sum);
    }

    private double twoPointDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(2,x1-x2)+Math.pow(2,y1-y2));
    }

    public void learn(double[] input) {
        double distance = 1000000000;
        int x=0;
        int y=0;
        for (int i = 0; i < this.nodes.length; i++) {
            for (int j = 0; j < this.nodes.length; j++) {
                double[] auxVector = this.nodes[i][j].getWeights();
                double auxDistance = distance(input,auxVector);
                if (auxDistance < distance) {
                    distance = auxDistance;
                    x=i;
                    y=j;
                }

            }
        }
        this.updateNeighbours(x,y,input);
        this.iteration++;
    }

    private void updateNeighbours(int x, int y,double[] input) {
        for (int i = 0; i < this.nodes.length; i++) {
            for (int j = 0; j < this.nodes.length ; j++) {
                if (twoPointDistance(x,y,i,j) <= this.environmentIteration(this.iteration)) {
                    this.nodes[i][j].updateWeights(this.learningRateIteration(this.iteration),input);
                }
            }
        }
    }

    private double environmentIteration (int iteration) {
        return iteration == 0 ? this.environment : this.environment * Math.exp((-iteration)/delta);
    }

    private double learningRateIteration (int iteration) {
        return iteration == 0 ? this.learningRate : this.learningRate * Math.exp((-iteration)/delta);
    }

    public void printHeatMap(double[][] normalizedMatrix, ArrayList<Country> countries) {
        System.out.println();
        int[][] matrix = new int[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                matrix[i][j]= 0;
            }
        }
        for (int i = 0; i < normalizedMatrix.length; i++) {
            int[] aux = this.getNode(normalizedMatrix[i]);
            matrix[aux[0]][aux[1]]++;
            System.out.println(countries.get(i).getCountry() + " x= " + aux[0] + " y= " + aux[1]);
        }
        System.out.println();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int[] getNode(double[] input) {
        double distance = 1000000000;
        int x=0;
        int y=0;
        for (int i = 0; i < this.nodes.length; i++) {
            for (int j = 0; j < this.nodes.length; j++) {
                double[] auxVector = this.nodes[i][j].getWeights();
                double auxDistance = distance(input,auxVector);
                if (auxDistance < distance) {
                    distance = auxDistance;
                    x=i;
                    y=j;
                }

            }
        }
        int[] aux = {x,y};
        return aux;
    }
}
