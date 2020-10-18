import java.io.IOException;

/**
 * Class Main (hopf)
 * @author Daniel Gynn (DJG44)
 * Stores the input values from the console and runs the Hopfield network with the specified patterns.
 */
public class hopf {
    static Hopfield trainingPattern;

    public static void main(String[] args) {
        // Get pattern file paths from command line input
        //String storedPatternInput = args[0];
        //String incompletePatternInput = args[1];

        // Store patterns, by line, from file reader
        //String[] sp = Reader.readFile(storedPatternInput);
        //String[] ip = Reader.readFile(incompletePatternInput);

        float[] storedPattern1 = {-1,  -1,  1,  1};
        trainingPattern = new Hopfield(4);
        trainingPattern.add(storedPattern1);
        float[] storedPattern2 = {1,  1,  -1,  -1};
        //trainingPattern = new Hopfield(4);
        trainingPattern.add(storedPattern2);
        trainingPattern.learn();
        for (int i = 0; i < 1; i++) {
            // Get the patterns into int array format
            float[] incompletePattern = {-1,  -1,  -1,  1};

            // Generate the network output.
            Hopfield.generateOutput(trainingPattern, incompletePattern);
        }
    }
}