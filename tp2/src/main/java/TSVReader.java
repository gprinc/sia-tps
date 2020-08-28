import java.io.*;
import java.util.ArrayList;

public class TSVReader extends Reader {

    public static ArrayList<Item> getItemList(String fileName) {
        ArrayList<Item> Data = new ArrayList<>();
        Item item;
        double strength;
        double agility;
        double expertise;
        double endurance;
        double life;
        double id;
        boolean isFirstLine = true;
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    String[] lineItems = line.split("\t");
                    strength = Double.parseDouble(lineItems[1]);
                    agility = Double.parseDouble(lineItems[2]);
                    expertise = Double.parseDouble(lineItems[3]);
                    endurance = Double.parseDouble(lineItems[4]);
                    life = Double.parseDouble(lineItems[5]);
                    id = Double.parseDouble(lineItems[0]);
                    item = new Item(strength,agility,expertise,endurance,life,id);
                    Data.add(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
