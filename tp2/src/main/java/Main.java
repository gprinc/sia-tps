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
            int populationSize = Integer.parseInt((String) data.get("population"));
            int iterations = Integer.parseInt((String) data.get("iterations"));
            Population population = new Population(TSVReader.getItemList("fulldata/botas.tsv"),TSVReader.getItemList("fulldata/armas.tsv"),TSVReader.getItemList("fulldata/cascos.tsv"),TSVReader.getItemList("fulldata/guantes.tsv"),TSVReader.getItemList("fulldata/pecheras.tsv"));

            population.init(populationSize,"warrior",10,0.5, "elite", "universal",100,100000);

            do {
                population.mate();
                population.selection();
                population.mutate();

            } while ( population.hasTerminated() );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP2");
            return;
        }
    }
}
