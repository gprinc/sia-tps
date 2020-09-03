import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    private final static int DEFAULT_ITERATIONS = 100;
    private final static String DEFAULT_DND_CLASS = "warrior";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("config.json"));
            int population = Integer.parseInt((String) data.get("population"));
            int iterations = Integer.parseInt((String) data.get("iterations"));
            String dndClass = (String) data.get("class");
            if (dndClass.equals(null))
                dndClass = DEFAULT_DND_CLASS;


            ArrayList<Item> bootList = TSVReader.getItemList("fulldata/botas.tsv");
            ArrayList<Item> weaponsList = TSVReader.getItemList("fulldata/armas.tsv");
            ArrayList<Item> helmetList = TSVReader.getItemList("fulldata/cascos.tsv");
            ArrayList<Item> glovesList = TSVReader.getItemList("fulldata/guantes.tsv");
            ArrayList<Item> chestList = TSVReader.getItemList("fulldata/pecheras.tsv");

            LinkedList<Player> pop = Population.generatePopulation(bootList,weaponsList,helmetList,glovesList,chestList, population,dndClass);
            Player[] aux = new Player[pop.size()];
            double test = 0;

            for (int i = 0; i < 7; i++) {
                System.out.println(pop.size());
                test = 0;
                switch(i) {
                    case 0:
                        aux = Selection.elite(pop.toArray(aux),10);

                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 1:
                        aux = Selection.roulette(pop.toArray(aux),10);
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 2:
                        aux = Selection.universal(pop.toArray(aux),10);
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 3:
                        aux = Selection.ranking(pop.toArray(aux),10);
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 4:
                        aux = Selection.boltzmann(pop.toArray(aux),10,Population.temperature(10,0,100000,100));
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 5:
                        aux = Selection.dTournament(pop.toArray(aux),10);
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
                        System.out.println(aux.length);
                        System.out.print('\n');
                        break;
                    case 6:
                        aux = Selection.pTournament(pop.toArray(aux),10);
                        for (Player p: aux) {
                            if (p!= null)
                                test += p.performance();
                        }
                        System.out.println(test/aux.length);
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
