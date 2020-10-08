import java.io.*;

public class Main {

    public static void main(String[] args) {
        File csvFile = new File("europe.csv");
        BufferedReader csvReader;
        if (csvFile.isFile()) {
            try {
                csvReader = new BufferedReader(new FileReader("europe.csv"));
                String row;
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return;

    }
}
