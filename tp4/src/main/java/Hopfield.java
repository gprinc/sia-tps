import java.util.*;

class Hopfield {
    static int patternLength;
    float[][] weights;
    Vector<float[]> trainingPattern = new Vector<float[]>();

    public Hopfield(int patternLength) {
        Hopfield.patternLength = patternLength;
        weights = new float[patternLength][patternLength];
    }

    public void add(float[] pattern) {
        trainingPattern.addElement(pattern);
    }


    public void learn() {
        for (int i = 1; i < patternLength; i++) {
            for (int j = 0; j < i; j++) {
                for (int n = 0; n < trainingPattern.size(); n++) {
                    float[] data = (float[]) trainingPattern.elementAt(n);
                    float temp = ((data[i] * data[j]) / patternLength) + weights[j][i];
                    weights[i][j] = weights[j][i] = temp;
                }
            }
        }
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