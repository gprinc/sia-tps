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
            int populationSize = Integer.parseInt((String) data.get("population"));
            double a = Double.parseDouble((String) data.get("a"));
            double b = Double.parseDouble((String) data.get("b"));
            String method1 = (String) data.get("method1");
            String method2 = (String) data.get("method2");
            String method3 = (String) data.get("method3");
            String method4 = (String) data.get("method4");
            String matingType = (String) data.get("matingType");
            int k = Integer.parseInt((String) data.get("k"));
            int t0 = Integer.parseInt((String) data.get("t0"));
            int tc = Integer.parseInt((String) data.get("tc"));
            String implementation = (String) data.get("implementation");
            int m = Integer.parseInt((String) data.get("m"));
            String impSel = (String) data.get("impSel");
            int generations = Integer.parseInt((String) data.get("generations"));
            int time = Integer.parseInt((String) data.get("time"));
            double accepted = Double.parseDouble((String) data.get("accepted"));
            int content = Integer.parseInt((String) data.get("content"));

            String cut = (String) data.get("cut");;
            if (cut.equals(null))
                cut = DEFAULT_CUT;

            String dndClass = (String) data.get("class");
            if (dndClass.equals(null))
                dndClass = DEFAULT_DND_CLASS;
            Population population = new Population(TSVReader.getItemList("fulldata/botas.tsv"),TSVReader.getItemList("fulldata/armas.tsv"),TSVReader.getItemList("fulldata/cascos.tsv"),TSVReader.getItemList("fulldata/guantes.tsv"),TSVReader.getItemList("fulldata/pecheras.tsv"));

            System.out.println("Finalización de carga de archivos");

            String mutation = (String) data.get("mutation");
            if (mutation.equals(null))
                mutation = DEFAULT_MUTATION;

            double pm = Double.parseDouble((String) data.get("pm"));
            int limitm = Integer.parseInt((String) data.get("limitm"));

            population.init(populationSize,dndClass,k,a, method1, method2,t0,tc, mutation, pm, limitm, matingType, implementation, m, impSel, cut, generations, time, accepted, content, b, method3, method4);

            do {
                System.out.println("Selection");
                population.selection();

                System.out.println("Mate");
                population.mate();

                System.out.println("Mutate");
                population.mutate();

                System.out.println("Implementation");
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
