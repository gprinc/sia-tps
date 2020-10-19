import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Kohonen {
    KNode[][] nodes;
    double environment;
    double learningRate;
    int iteration;
    double delta;

    public Kohonen(int k, int length, double environment, double learningRate, double delta, double[][] inputs) {
        this.nodes = new KNode[k][k];
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                int randomNum = rand.nextInt(inputs.length);
                nodes[i][j] = new KNode(length,inputs[randomNum]);
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
        System.out.println("environmentIteration  "+ this.environmentIteration(this.iteration));
        System.out.println("learningRateIteration  "+ this.learningRateIteration(this.iteration));
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
        double aux = this.environment * Math.exp((-iteration)/delta);
        return iteration == 0 ? this.environment : (aux < 1 ? 1 : aux);
    }

    private double learningRateIteration (int iteration) {
        return iteration == 0 ? this.learningRate : this.learningRate * Math.exp((-iteration)/delta);
    }

    public void printHeatMap(double[][] normalizedMatrix, ArrayList<Country> countries) {
        HashMap<Integer, HashMap<Integer,String>> mapa = new HashMap<>();
        HashMap<Integer, String> mapaInterno;
        String auxCountry = "";
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
            mapaInterno = new HashMap<Integer, String>();
            if (mapa.containsKey(aux[0])) {
                mapaInterno = mapa.get(aux[0]);
                auxCountry = mapaInterno.get(aux[1]);
            }
            mapaInterno.put(aux[1], auxCountry + " " + countries.get(i).getCountry());
            mapa.put(aux[0], mapaInterno);
            System.out.println(countries.get(i).getCountry() + " x= " + aux[0] + " y= " + aux[1]);
        }
        System.out.println();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // printing related countries
        for (Integer key: mapa.keySet()){
            for (Integer okey: mapa.get(key).keySet()) {
                System.out.println(" x= " + key + " y= " + okey);
                System.out.println(mapa.get(key).get(okey).replace("null", ""));
            }
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
