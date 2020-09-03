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
            int populationSize = Integer.parseInt((String) data.get("population"));
            int iterations = Integer.parseInt((String) data.get("iterations"));

            String dndClass = (String) data.get("class");
            if (dndClass.equals(null))
                dndClass = DEFAULT_DND_CLASS;
            Population population = new Population(TSVReader.getItemList("fulldata/botas.tsv"),TSVReader.getItemList("fulldata/armas.tsv"),TSVReader.getItemList("fulldata/cascos.tsv"),TSVReader.getItemList("fulldata/guantes.tsv"),TSVReader.getItemList("fulldata/pecheras.tsv"));

            population.init(populationSize,"warrior",10,0.5, "elite", "ranking",100,100000);

            do {
                population.selection();
                population.mate();
                population.mutate();

            } while ( population.hasTerminated() );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP2");
            return;
        }
    }
}
