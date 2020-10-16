import java.util.*;

class Hopfield {
    static int patternLength;
    float[][] weights;
    float[] tempStorage;
    Vector<float[]> trainingPattern = new Vector<float[]>();

    /**
     * Hopfield class constructor
     * @param patternLength
     */
    public Hopfield(int patternLength) {
        Hopfield.patternLength = patternLength;
        weights = new float[patternLength][patternLength];
        tempStorage = new float[patternLength];
    }

    /**
     * Generates the appropriate output for the given patterns.
     * @param Hopfield run
     * @param float[] pattern
     */
    static void generateOutput(Hopfield run, float[] pattern) {
        float[] data = new float[patternLength];

        for (int i = 0; i < patternLength; i++) {
            data[i] = pattern[i];
        }

        for (int i = 0; i < 1; i++) {
            int index = (int) ((patternLength - 1) * (float) Math.random());
            data[index] = -data[index];

            if (data[index] < 0.0f) {
                data[index] = 1.0f;
            } else {
                data[index] = -1.0f;
            }
        }

        float[] node = run.makeNetwork(data, patternLength);
        int output = 0;

        for (int i = 0; i < patternLength; i++) {
            if (node[i] > 0.1f) {
                output = 1;
            } else {
                output = -1;
            }

            System.out.print(output + " ");
        }
        System.out.println();
    }

    /**
     * Takes the input text from the file as a String and returns as an array of Floats
     * @param String patternString
     * @return float[] patten
     */
    public static float[] getPattern(String patternString) {
        String[] patternNodes = patternString.split(" ");
        float[] pattern = new float[patternNodes.length];

        for(int i = 0; i < patternNodes.length; i++) {
            try {
                pattern[i] = Integer.parseInt(patternNodes[i]);
            } catch (NumberFormatException nfe) {
                //Not an integer
            }
        }

        return pattern;
    }

    /**
     * Takes the stored pattern and runs the training algorithm.
     * @param float[] pattern
     */
    public void learn(float[] pattern) {
        trainingPattern.addElement(pattern);

        for (int i = 1; i < patternLength; i++) {
            for (int j = 0; j < i; j++) {
                for (int n = 0; n < trainingPattern.size(); n++) {
                    float[] data = (float[]) trainingPattern.elementAt(n);
                    float temp = data[i] * data[j] + weights[j][i];

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

    /**
     * Builds the Hopfield network for the given pattern and returns an array of nodes.
     * @param float[] pattern
     * @param int numIterations
     * @return nodes
     */
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
            }
        }

        return nodes;
    }

    /**
     * Delta energy function.
     * @param int index
     * @param float[] inputNodes
     * @return
     */
    private float energy(int index, float[] inputNodes) {
        float temp = 0.0f;

        for (int i = 0; i < patternLength; i++) {
            temp += weights[index][i] * inputNodes[i];
        }

        return 2.0f * temp - tempStorage[index];
    }
}