import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

import static java.lang.Character.isWhitespace;

public class TxtReader {
    public static ArrayList<Double[]> getDoubleArrayFromTxt(File file, int n) {
        ArrayList<Double[]> aux = new ArrayList<Double[]>();
        Double[] arrayAux;
        String num;
        int i;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                num = "";
                lastChar = ' ';
                i = 0;
                arrayAux = new Double[n];
                for(char s: data.toCharArray()) {
                    if (isWhitespace(s) && !isWhitespace(lastChar)) {
                        arrayAux[i++] = new Double(num);
                        num = "";
                    } else if (!isWhitespace(s)) {
                        num += s;
                    }
                    lastChar = s;
                }
                if (num != "") {
                    arrayAux[i++] = new Double(num);
                }
                aux.add(arrayAux);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return aux;
    }

    public static ArrayList<Double> getDoubleArrayFromTxt(File file) {
        ArrayList<Double> aux = new ArrayList<Double>();
        String num;
        int i;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                num = "";
                lastChar = ' ';
                i = 0;
                for(char s: data.toCharArray()) {
                    if (isWhitespace(s) && !isWhitespace(lastChar)) {
                        aux.add(new Double(num));
                        num = "";
                    } else if (!isWhitespace(s)) {
                        num += s;
                    }
                    lastChar = s;
                }
                if (num != "") {
                    aux.add(new Double(num));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return aux;
    }

    public static ArrayList<Integer[]> getIntegerArrayFromTxt(File file, int n) {
        ArrayList<Integer[]> aux = new ArrayList<Integer[]>();
        Integer[] arrayAux;
        String num;
        int i;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                num = "";
                lastChar = ' ';
                i = 0;
                arrayAux = new Integer[n];
                for(char s: data.toCharArray()) {
                    if (isWhitespace(s) && !isWhitespace(lastChar)) {
                        arrayAux[i++] = new Integer(num);
                        num = "";
                    } else if (!isWhitespace(s)) {
                        num += s;
                    }
                    lastChar = s;
                }
                if (num != "") {
                    arrayAux[i++] = new Integer(num);
                }
                aux.add(arrayAux);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return aux;
    }
}
