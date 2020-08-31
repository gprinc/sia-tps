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

            LinkedList<Player> pop = Population.generatePopulation(bootList,weaponsList,helmetList,glovesList,chestList, population,"warrior");
            Player[] aux = new Player[pop.size()];

            for (int i = 0; i < 7; i++) {
                System.out.println(pop.size());
                switch(i) {
                    case 0:
                        aux = Selection.elite(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                    case 1:
                        aux = Selection.roulette(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                    case 2:
                        aux = Selection.universal(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                    case 3:
                        aux = Selection.ranking(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                    case 4:
                        aux = Selection.boltzmann(pop.toArray(aux),10,Population.temperature(10,0,100000,100));
                        System.out.println(aux.length);
                        break;
                    case 5:
                        aux = Selection.dTournament(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                    case 6:
                        aux = Selection.pTournament(pop.toArray(aux),10);
                        System.out.println(aux.length);
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP2");
            return;
        }
    }
}
