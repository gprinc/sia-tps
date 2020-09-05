import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Population {
    private LinkedList<Player> parents;
    private LinkedList<Player> sons;
    private LinkedList<Player> selected;
    private ArrayList<Item> bootList;
    private ArrayList<Item> weaponsList;
    private ArrayList<Item> helmetList;
    private ArrayList<Item> glovesList;
    private ArrayList<Item> chestList;
    private int populationSize = 0;
    private String type = null;
    private int k = 0;
    private double selectionValue = 0;
    private String[] selectionType = new String[2];
    private String matingType = "onePoint";
    private double temperature = 0;
    private int t0 = 0;
    private int tc = 0;
    private String mutation;
    private double pm = 0.5;
    private int limitm = 6;
    private Plot plt;
    private String implementation;
    private int m;
    private String impSel;
    private String cut;
    private int generations;
    private int time;
    private double accepted;
    private int iterations;
    private long start;

    private LinkedList<Double> avgFitness;
    private LinkedList<Double> lowerFitness;

    public Population(ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList) {
        this.bootList = bootList;
        this.weaponsList = weaponsList;
        this.helmetList = helmetList;
        this.glovesList = glovesList;
        this.chestList = chestList;
        this.plt = Plot.create();
        this.avgFitness = new LinkedList<Double>();
        this.lowerFitness = new LinkedList<Double>();
    }

    public void init (int size, String type, int k, double selectionValue, String selectionType0, String selectionType1, int t0, int tc, String mutation, double pm, int limitm, String
            matingType, String implementation, int m, String impSel, String cut, int generations, int time, double accepted) {
        this.populationSize = size;
        this.type = type;
        this.k = k;
        this.selectionValue = selectionValue;
        this.parents = new LinkedList<Player>();
        this.selectionType[0] = selectionType0;
        this.selectionType[1] = selectionType1;
        this.t0 = t0;
        this.tc = tc;
        this.mutation = mutation;
        this.pm = pm;
        this.limitm = limitm;
        this.matingType = matingType;
        this.implementation = implementation;
        this.m = m;
        this.impSel = impSel;
        this.cut = cut;
        this.generations = generations;
        this.time = time;
        this.accepted = accepted;
        this.start = System.nanoTime();

        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            Player aux = new Player(1.3 + (2.0 - 1.3) * rand.nextDouble(),chestList.get(rand.nextInt((chestList.size()) + 1)),glovesList.get(rand.nextInt((glovesList.size()) + 1)),helmetList.get(rand.nextInt((helmetList.size()) + 1)),weaponsList.get(rand.nextInt((weaponsList.size()) + 1)),bootList.get(rand.nextInt((bootList.size()) + 1)),type);
            this.parents.add(aux);
        }
    }


    public void selection () {
        this.selected = new LinkedList<Player>();
        int size = (int) Math.floor(k*selectionValue);
        if (k % 2 != 0) {
            size +=1;
        }

        Player[] aux = new Player[size];
        Player[] selectedList = this.parents.toArray(new Player[this.parents.size()]);

        switch(this.selectionType[0]) {
            case "elite":
                aux = Selection.elite(selectedList,size);
                break;
            case "roulette":
                aux = Selection.roulette(selectedList,size);
                break;
            case "universal":
                aux = Selection.universal(selectedList,size);
                break;
            case "ranking":
                aux = Selection.ranking(selectedList,size);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(selectedList,size,this.temperature());
                break;
            case "dTournament":
                aux = Selection.dTournament(selectedList,size, this.m);
                break;
            case "pTournament":
                aux = Selection.pTournament(selectedList,size);
                break;
        }

        for (Player p : aux ){
            this.selected.add(p);
        }

        aux = new Player[k - size];

        switch(this.selectionType[1]) {
            case "elite":
                aux = Selection.elite(selectedList,k - size);
                break;
            case "roulette":
                aux = Selection.roulette(selectedList,k - size);
                break;
            case "universal":
                aux = Selection.universal(selectedList,k - size);
                break;
            case "ranking":
                aux = Selection.ranking(selectedList,k - size);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(selectedList,k - size, this.temperature());
                break;
            case "dTournament":
                aux = Selection.dTournament(selectedList,k - size, this.m);
                break;
            case "pTournament":
                aux = Selection.pTournament(selectedList,k - size);
                break;
        }

        for (Player p : aux ){
            this.selected.add(p);
        }
    }

    public void mate () {
        switch(this.matingType) {
            case "onePoint":
                this.sons = Mate.onePoint(this.selected, this);
                break;
            case "twoPoints":
                this.sons = Mate.twoPoints(this.selected, this);
                break;
            case "anular":
                this.sons = Mate.anular(this.selected, this);
                break;
            case "uniform":
                this.sons = Mate.uniform(this.selected, this);
                break;
        }
        System.out.println(this.sons);
    }

    public void mutate () {
        LinkedList<Player> sonsAux = new LinkedList<Player>();
        switch (this.mutation) {
            case "gene":
                for (Player p: this.sons) {
                    sonsAux.add(Mutation.gene(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
                break;
            case "limitedMultigene":
                for (Player p: this.sons) {
                    sonsAux.add(Mutation.limitedMultigene(p, bootList, weaponsList, helmetList, glovesList, chestList, limitm, pm));
                }
                break;
            case "uniformMultigene":
                for (Player p: this.sons) {
                    sonsAux.add(Mutation.uniformMultigene(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
                break;
            case "complete":
                for (Player p: this.sons) {
                    sonsAux.add(Mutation.complete(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
        }
        this.sons = sonsAux;
    }

    public void implementation () {
        LinkedList<Player> newGen = new LinkedList<>();
        Player[] aux = new Player[this.parents.size()];
        Player[] current = new Player[this.parents.size()];
        current = this.parents.toArray(current);
        Player[] sons = new Player[this.sons.size()];
        sons = this.sons.toArray(sons);

        switch (this.implementation){
            case "fillAll":
                aux = Implementation.fillAll(current, sons, impSel, this.temperature(), this.m);
                break;
            case "fillParent":
                aux = Implementation.fillParent(current, sons, impSel, this.temperature(), this.m);
        }

        for (Player p : aux ){
            newGen.add(p);
        }

        this.parents = newGen;
    }

    private double temperature () {
        this.temperature = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature);
        return this.temperature;
    }

    public boolean hasTerminated () {
        switch (this.cut) {
            case "generations":
                return this.generations == this.iterations;
            case "time":
                long now = System.nanoTime();
                double elapsedTimeInSecond = (double) (now - this.start) / 1000000000;
                return elapsedTimeInSecond == this.time;
            case "accepted":
                Player aux = null;
                for (Player p: this.parents) {
                    if (aux == null)
                        aux = p;
                    else if (aux.performance() >= this.accepted)
                        return true;
                    else if (aux.performance() < p.performance())
                        aux = p;
                }
                return false;
            default:
                return false;
        }
    }

    private Item getItem(double id, ArrayList<Item> items){
        for (Item i : items) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    public Player generate(double[] gens) {
        Player aux = new Player(gens[0], this.getItem(gens[1], this.chestList), this.getItem(gens[2], this.glovesList), this.getItem(gens[3], this.helmetList), this.getItem(gens[4], this.weaponsList),this.getItem(gens[5], this.bootList), this.type);
        return aux;
    }

    public void graphData() {
        Player aux = this.parents.get(0);

        double average = 0;
        for (Player p : this.parents ) {
            average += p.performance();
            if (p.performance() < aux.performance()) {
                aux = p;
            }
        }
        // System.out.println("Fitness minimo : " + aux.performance());
        // System.out.println("Fitness promedio : " + (average / this.parents.size()));
        this.avgFitness.add(average / this.parents.size());
        this.lowerFitness.add(aux.performance());

        // System.out.println("Genetic Diversity : " + aux.performance());
    }

    public void plotData() throws IOException, PythonExecutionException {
        Plot pltAvg = Plot.create();
        Plot pltMin = Plot.create();
        pltAvg.plot().add(this.avgFitness);
        pltAvg.xlabel("Iteration");
        pltAvg.ylabel("Fitness");
        pltAvg.title("Average Fitness");
        pltAvg.legend();
        pltAvg.show();

        pltMin.plot().add(this.lowerFitness);
        pltMin.xlabel("Iteration");
        pltMin.ylabel("Fitness");
        pltMin.title("Lower Fitness");
        pltMin.legend();
        pltMin.show();
    }

}
