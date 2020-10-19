import java.util.ArrayList;
import java.util.Random;

public class hopf {

    static Hopfield trainingPattern;
    private final static int LETTER_LENGTH = 25;

    public static void startHopfield(ArrayList<ArrayList<Integer>> letters, int hopfieldIterations) {
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
        for (int i = 0; i < hopfieldIterations; i++) {
            float[] incompletePattern = getIncompletePattern(letters);
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
        System.out.println("Index Modfied  " + changedByte);
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
                    break;
                }
            }

            if (aux == true) {
                System.out.println("Found same pattern");
                System.out.println("Pattern:  " + index);
                return aux;
            }
            index++;
        }
        return aux;
    }
}