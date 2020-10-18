import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Main (hopf)
 * @author Daniel Gynn (DJG44)
 * Stores the input values from the console and runs the Hopfield network with the specified patterns.
 */
public class hopf {

    static Hopfield trainingPattern;
    private final static int LETTER_LENGTH = 25;

    public static void startHopfield(ArrayList<ArrayList<Integer>> letters) {
        // Get pattern file paths from command line input
        //String storedPatternInput = args[0];
        //String incompletePatternInput = args[1];

        // Store patterns, by line, from file reader
        //String[] sp = Reader.readFile(storedPatternInput);
        //String[] ip = Reader.readFile(incompletePatternInput);
        trainingPattern = new Hopfield(LETTER_LENGTH);
        float [] espureo = new float[25];
        ArrayList<float []> patterns = new ArrayList<>();
        int lettersIndex = 0;
        for (ArrayList<Integer> letter: letters) {
            float [] letterPattern = new float[25];
            int index = 0;
            if (lettersIndex != 3) {
                for (Integer i: letter) {
                    letterPattern[index++] = i;
                }
                patterns.add(letterPattern);
            } else {
                for (Integer i: letter) {
                    espureo[index++] = i;
                }
            }
            lettersIndex++;
        }

        for (float []letterPattern : patterns) {
            trainingPattern.add(letterPattern);
        }
        trainingPattern.learn();
        float[] pattern;
        boolean contains;
        for (int i = 0; i < 1; i++) {
            // Get the patterns into int array format
            float[] incompletePattern = getIncompletePattern(letters);
            // Generate the network output.
            //Hopfield.generateOutput(trainingPattern, incompletePattern);
            pattern = trainingPattern.test(incompletePattern);
            System.out.println();
            contains = containsArray(patterns,pattern);
            System.out.println("¿El patron esta en los de entrenamiento?  " + contains);
            System.out.println("---------");
        }
        System.out.println("Espureo");
        pattern = trainingPattern.test(espureo);
        System.out.println();
        contains = containsArray(patterns,pattern);
        System.out.println("¿El patron esta en los de entrenamiento?  " + contains);
        System.out.println("---------");
    }

    private static float[] getIncompletePattern(ArrayList<ArrayList<Integer>> letters) {
        Random r = new Random();
        int letterIndex = r.nextInt(4 - 1) + 1;
        ArrayList<Integer> letter = (ArrayList<Integer>) letters.get(letterIndex - 1).clone();
        int changedByte = r.nextInt(25 - 1) + 1;
        float [] letterPattern = new float[25];
        int index = 0;
        for (Integer i: letter) {
            if (index + 1 == changedByte) {
                if (i < 0)
                    letterPattern[index++] = 1;
                else
                    letterPattern[index++] = -1;
            } else
                letterPattern[index++] = i;
        }
        System.out.println("Incomplete Pattern  " + letterIndex);
        for (float bit : letterPattern) {
            System.out.print(bit + " ");
        }
        System.out.println();
        System.out.println("---------");
        return letterPattern;
    }

    private static boolean containsArray(ArrayList<float []> patterns, float [] pattern) {
        boolean aux = true;
        int index = 1;
        for (float [] pattern1: patterns) {
            aux = true;
            for (int i = 0; i < pattern.length; i++) {
                if (pattern[i] != pattern1[i]){
                    aux = false;
                }
            }

            if (aux == true) {
                System.out.println("Pattern:  " + index);
                return aux;
            }
            index++;
        }
        return aux;
    }
}