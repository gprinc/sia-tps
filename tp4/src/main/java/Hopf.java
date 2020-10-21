import java.util.ArrayList;
import java.util.Random;

public class Hopf {

    static Hopfield trainingPattern;
    private final static int LETTER_LENGTH = 25;

    public static void startHopfield(ArrayList<ArrayList<Integer>> letters, int hopfieldIterations, int hopfieldBits) {
        trainingPattern = new Hopfield(LETTER_LENGTH);
        float [] espureo = new float[25];
        ArrayList<float []> patterns = new ArrayList<>();
        int lettersIndex = 0;
        for (ArrayList<Integer> letter: letters) {
            float [] letterPattern = new float[25];
            int index = 0;
            if (lettersIndex != 4) {
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
            float[] incompletePattern = getIncompletePattern(letters, hopfieldBits);
            pattern = trainingPattern.test(incompletePattern);
            System.out.println();
            contains = containsArray(patterns,pattern);
            System.out.println("¿El patron esta en los de entrenamiento?  " + contains);
            System.out.println("---------");
        }

        System.out.println("Espureo");
        int i = 0;
        for (float bit : espureo) {
            if (i % 5 == 0)
                System.out.println();
            System.out.print((bit == -1 ? 0 : 1) + "\t");
            i++;
        }
        System.out.println();
        System.out.println("---------");
        pattern = trainingPattern.test(espureo);
        System.out.println();
        contains = containsArray(patterns,pattern);
        System.out.println("¿El patron esta en los de entrenamiento?  " + contains);
        System.out.println("---------");
    }

    private static float[] getIncompletePattern(ArrayList<ArrayList<Integer>> letters, int hopfieldBits) {
        Random r = new Random();
        int letterIndex = r.nextInt(4 - 1) + 1;
        ArrayList<Integer> letter = (ArrayList<Integer>) letters.get(letterIndex - 1).clone();
        float [] letterPattern = new float[25];
        for (int i = 0; i < letterPattern.length; i++) {
            letterPattern[i] = letter.get(i);
        }
        int[] changedBytes = new int[hopfieldBits];
        for (int j = 0; j < hopfieldBits; j++) {
            int changedByte = r.nextInt(25 - 1) + 1;
            changedBytes[j]  = changedByte;
            int index = 0;
            for (Integer i: letter) {
                if (index + 1 == changedByte) {
                    if (i < 0)
                        letterPattern[index++] = 1;
                    else
                        letterPattern[index++] = -1;
                } else
                    index++;
            }
        }
        System.out.println("Incomplete Pattern  " + letterIndex);
        System.out.print("Modified indexes: ");
        for (int j = 0; j < hopfieldBits; j++) {
            System.out.print(changedBytes[j] + " ");
        }
        System.out.println();
        int i = 0;
        for (float bit : letterPattern) {
            if (i % 5 == 0)
                System.out.println();
            System.out.print((bit == -1 ? 0 : 1) + "\t");
            i++;
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