import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        File csvFile = new File("../europe.csv");
        BufferedReader csvReader;
        String[] data;
        ArrayList<Country> countries = new ArrayList<>();
        if (csvFile.isFile()) {
            try {
                csvReader = new BufferedReader(new FileReader("../europe.csv"));
                String row;
                boolean isFirstLine = true;
                while ((row = csvReader.readLine()) != null) {
                    data = row.split(",");
                    if (isFirstLine) {
                        isFirstLine = false;
                    } else {
                        data = row.split(",");
                        String country = data[0];
                        String area = data[1];
                        String gdp = data[2];
                        String inflation = data[3];
                        String lifeExpect = data[4];
                        String military = data[5];
                        String popGrowth = data[6];
                        String unemployment = data[7];
                        countries.add(new Country(country, area, gdp, inflation, lifeExpect, military, popGrowth, unemployment));
                    }
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Country i = countries.get(0);
            System.out.println(i.getCountry());
            System.out.println(i.getArea());
            System.out.println(i.getGdp());
            System.out.println(i.getInflation());
            System.out.println(i.getLifeExpect());
            System.out.println(i.getMilitary());
            System.out.println(i.getPopGrowth());
            System.out.println(i.getUnemployment());
        }

        return;

    }
}
