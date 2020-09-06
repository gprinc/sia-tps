import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    private final static int DEFAULT_ITERATIONS = 100;
    private final static String DEFAULT_DND_CLASS = "warrior";
    private final static String DEFAULT_MUTATION = "gene";
    private final static String DEFAULT_CUT = "generations";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("config.json"));
            int populationSize = InitializerJson.giveRequiredInt((String) data.get("population"));
            if (populationSize == 0) {
                System.out.println("population can't be null or lower/equals to 0");
                return;
            }
            int k = InitializerJson.giveRequiredInt((String) data.get("k"));
            if (k == 0) {
                System.out.println("k can't be null, negative or 0");
                return;
            }
            String dndClass = InitializerJson.giveClass((String) data.get("class"));
            if (dndClass == null) {
                System.out.println("class can't be null or must be either warrio, archer, defender or infiltrate");
                return;
            }
            double a = InitializerJson.givePercentage((String) data.get("a"));
            if (a == -1) {
                System.out.println("a can't be null, negative or bigger than 1");
                return;
            }
            double b = InitializerJson.givePercentage((String) data.get("b"));
            if (a == -1) {
                System.out.println("b can't be null, negative or bigger than 1");
                return;
            }
            int tc = InitializerJson.giveInt((String) data.get("tc"), 2);
            int t0 = InitializerJson.giveInt((String) data.get("t0"), 1);
            if (t0 > tc) {
                System.out.println("t0 must be lower than tc");
                return;
            }
            double pm = InitializerJson.givePercentageWithDef((String) data.get("pm"), 0.5);

            String method1 = InitializerJson.giveSelectionMethod((String) data.get("method1"));
            String method2 = InitializerJson.giveSelectionMethod((String) data.get("method2"));
            String method3 = InitializerJson.giveSelectionMethod((String) data.get("method3"));
            String method4 = InitializerJson.giveSelectionMethod((String) data.get("method4"));
            String matingType = InitializerJson.giveMating((String) data.get("matingType"));
            String implementation = InitializerJson.giveImplementation((String) data.get("implementation"));
            String mutation = InitializerJson.giveMutation((String) data.get("mutation"));
            String cut = InitializerJson.giveCut((String) data.get("cut"));
            int generations = InitializerJson.giveInt((String) data.get("generations"), 10);
            int time = InitializerJson.giveInt((String) data.get("time"), 1000000);
            double accepted = InitializerJson.giveDouble((String) data.get("accepted"), 20.0);
            int content = InitializerJson.giveInt((String) data.get("content"), 5);
            double structure = InitializerJson.giveDouble((String) data.get("structure"), 0.5);
            int m = InitializerJson.giveInt((String) data.get("m"), 1);
            int limitm = InitializerJson.giveInt((String) data.get("limitm"), 1);

            Population population = new Population(TSVReader.getItemList("fulldata/botas.tsv"),TSVReader.getItemList("fulldata/armas.tsv"),TSVReader.getItemList("fulldata/cascos.tsv"),TSVReader.getItemList("fulldata/guantes.tsv"),TSVReader.getItemList("fulldata/pecheras.tsv"));

            System.out.println("Finalización de carga de archivos");

            population.init(populationSize,dndClass,k,a, method1, method2,t0,tc, mutation, pm, limitm, matingType, implementation, m, cut, generations, time, accepted, structure, content, b, method3, method4);

            do {
                population.selection();
                population.mate();
                population.mutate();
                population.implementation();

                population.graphData();
            } while (!population.hasTerminated());

            population.plotData();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TP2");
            return;
        }
    }
}
