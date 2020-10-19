import java.util.*;

class Hopfield {
    static int patternLength;
    float[][] weights;
    float[] tempStorage;
    Vector<float[]> trainingPattern = new Vector<float[]>();

    public Hopfield(int patternLength) {
        Hopfield.patternLength = patternLength;
        weights = new float[patternLength][patternLength];
        tempStorage = new float[patternLength];
    }

    public void add(float[] pattern) {
        trainingPattern.addElement(pattern);
    }

    static float[] generateOutput(Hopfield run, float[] pattern) {
        float[] node = run.makeNetwork(pattern, patternLength);
        float output = 0;
        for (int i = 0; i < patternLength; i++) {
            if (node[i] > 0.1f) {
                output = 1;
            } else {
                output = -1;
            }
            System.out.print(output + " ");
        }
        System.out.println();
        return node;
    }

    public void learn() {
        for (int i = 1; i < patternLength; i++) {
            for (int j = 0; j < i; j++) {
                for (int n = 0; n < trainingPattern.size(); n++) {
                    float[] data = (float[]) trainingPattern.elementAt(n);
                    float temp = data[i] * data[j] / patternLength + weights[j][i];
                    weights[i][j] = weights[j][i] = temp;
                }
            }
        }

        for (int i = 0; i < patternLength; i++) {
            tempStorage[i] = 0.0f;

            for (int j = 0; j < i; j++) {
                tempStorage[i] += weights[i][j];
            }
        }
    }

    public float[] makeNetwork(float[] pattern, int numIterations) {
        float[] nodes = new float[patternLength];

        for (int i = 0; i < patternLength; i++) {
            nodes[i] = pattern[i];
        }

        for (int i = 0; i < numIterations; i++) {
            for (int j = 0; j < patternLength; j++) {
                if (energy(j, nodes) > 0.0f) {
                    nodes[j] = 1.0f;
                } else {
                    nodes[j] = -1.0f;
                }
                System.out.print(nodes[j] + " ");
            }
            System.out.println();
        }

        return nodes;
    }

    private float energy(int index, float[] inputNodes) {
        float temp = 0.0f;

        for (int i = 0; i < patternLength; i++) {
            temp += weights[index][i] * inputNodes[i];
        }

        return 2.0f * temp - tempStorage[index];
    }

    public float[] test(float[] pattern) {
        boolean stability = false;
        float[] nodes = new float[patternLength];

        for (int i = 0; i < patternLength; i++) {
            nodes[i] = pattern[i];
        }

        while (!stability) {
            float[] auxNodes = new float[patternLength];
            stability = true;
            for (int i = 0; i < patternLength; i++) {
                auxNodes[i] = 0;
            }
            for (int i = 0; i < patternLength; i++) {
                for (int j = 0; j < weights.length; j++) {
                    auxNodes[i] += nodes[j] * weights[j][i];
                }
                System.out.print(Math.signum(auxNodes[i]) + " ");
            }
            System.out.println();

            for (int i = 0; i < patternLength; i++) {
                if (Math.signum(auxNodes[i]) != nodes[i]) {
                    stability=false;
                }
            }

            for (int i = 0; i < patternLength; i++) {
                nodes[i] = Math.signum(auxNodes[i]);
            }
        }
        return nodes;
    }
}