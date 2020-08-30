import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    private final static int DEFAULT_ITERATIONS = 100;

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("config.json"));
            int population = Integer.parseInt((String) data.get("population"));
            int iterations = Integer.parseInt((String) data.get("iterations"));


            ArrayList<Item> bootList = TSVReader.getItemList("fulldata/botas.tsv");
            ArrayList<Item> weaponsList = TSVReader.getItemList("fulldata/armas.tsv");
            ArrayList<Item> helmetList = TSVReader.getItemList("fulldata/cascos.tsv");
            ArrayList<Item> glovesList = TSVReader.getItemList("fulldata/guantes.tsv");
            ArrayList<Item> chestList = TSVReader.getItemList("fulldata/pecheras.tsv");

            LinkedList<Player> pop = Population.generatePopulation(bootList,weaponsList,helmetList,glovesList,chestList, population);

            Player[] aux = Selection.elite((Player[]) pop.toArray(),100);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP2");
            return;
        }
    }
}
