import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
    private double selectionValue2 = 0;
    private String[] selectionType = new String[4];
    private String matingType = "onePoint";
    private double temperature1 = 0;
    private double temperature2 = 0;
    private double temperature3 = 0;
    private double temperature4 = 0;
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
    private int content;
    private double structure;
    private int structureIterations;
    private int contentIteration;
    private double previousMaxFitness;
    private LinkedList<Player> prevPopulation;

    private LinkedList<Double> avgFitness;
    private LinkedList<Double> lowerFitness;
    private LinkedList<Integer> geneticDiversity;
    private LinkedList<Double> maxFitness;

    public Population(ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList) {
        this.bootList = bootList;
        this.weaponsList = weaponsList;
        this.helmetList = helmetList;
        this.glovesList = glovesList;
        this.chestList = chestList;
        this.plt = Plot.create();
        this.avgFitness = new LinkedList<Double>();
        this.lowerFitness = new LinkedList<Double>();
        this.geneticDiversity = new LinkedList<Integer>();
        this.prevPopulation = new LinkedList<Player>();
        this.maxFitness = new LinkedList<Double>();
    }

    public void init (int size, String type, int k, double selectionValue, String selectionType0, String selectionType1, int t0, int tc, String mutation, double pm, int limitm, String
            matingType, String implementation, int m, String cut, int generations, int time, double accepted, double structure, int content, double selectionValue2, String selectionType2, String selectionType3) {
        this.populationSize = size;
        this.type = type;
        this.k = k;
        this.selectionValue = selectionValue;
        this.selectionValue2 = selectionValue2;
        this.parents = new LinkedList<Player>();
        this.selectionType[0] = selectionType0;
        this.selectionType[1] = selectionType1;
        this.selectionType[2] = selectionType2;
        this.selectionType[3] = selectionType3;
        this.t0 = t0;
        this.tc = tc;
        this.mutation = mutation;
        this.pm = pm;
        this.limitm = limitm;
        this.matingType = matingType;
        this.implementation = implementation;
        this.m = m;
        this.cut = cut;
        this.generations = generations;
        this.time = time;
        this.accepted = accepted;
        this.start = System.nanoTime();
        this.content = content;
        this.iterations = 0;
        this.contentIteration = 0;
        this.previousMaxFitness = 0;
        this.structure = structure;
        this.structureIterations = 0;


        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            Player aux = new Player(1.3 + (2.0 - 1.3) * rand.nextDouble(),chestList.get(rand.nextInt((chestList.size()) + 1)),glovesList.get(rand.nextInt((glovesList.size()) + 1)),helmetList.get(rand.nextInt((helmetList.size()) + 1)),weaponsList.get(rand.nextInt((weaponsList.size()) + 1)),bootList.get(rand.nextInt((bootList.size()) + 1)),type);
            this.parents.add(aux);
        }
    }


    public void selection () {
        this.iterations++;
        System.out.println("Iteración:" + this.iterations);

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
                aux = Selection.boltzmann(selectedList,size,this.temperature(1));
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
                aux = Selection.boltzmann(selectedList,k - size, this.temperature(2));
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
                aux = Implementation.fillAll(current, sons, this.temperature(3), this.temperature(4), this.m, this.selectionValue2, this.selectionType[2], this.selectionType[3]);
                break;
            case "fillParent":
                aux = Implementation.fillParent(current, sons, this.temperature(3), this.temperature(4), this.m, this.selectionValue2, this.selectionType[2], this.selectionType[3]);
        }

        for (Player p : aux ){
            newGen.add(p);
        }

        this.parents = newGen;
    }

    private double temperature (int i) {
        switch (i) {
            case 1:
                this.temperature1 = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature1);
                return this.temperature1;
            case 2:
                this.temperature2 = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature2);
                return this.temperature2;
            case 3:
                this.temperature3 = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature3);
                return this.temperature3;
            default:
                this.temperature4 = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature4);
                return this.temperature4;
        }
    }

    public boolean hasTerminated () {
        switch (this.cut) {
            case "time":
                long now = System.nanoTime();
                double elapsedTimeInSecond = (double) (now - this.start) / 1000000000;
                return elapsedTimeInSecond >= this.time;
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
            case "structure":
                if (this.generations == this.structureIterations)
                    return true;
                if (this.prevPopulation.size() == 0)
                    this.prevPopulation = new LinkedList<Player>((LinkedList<Player>) this.parents.clone());
                else {
                    LinkedList<Player> auxStructure = new LinkedList<Player>((LinkedList<Player>)this.parents.clone());
                    auxStructure.removeAll(this.prevPopulation);
                    if (auxStructure.size()/this.prevPopulation.size() < this.structure)
                        this.structureIterations++;
                    else {
                        this.prevPopulation = new LinkedList<Player>((LinkedList<Player>) this.parents.clone());
                        this.structureIterations = 0;
                    }
                }
                return false;
            case "content":
                double auxMax = 0;
                for (Player p: this.parents) {
                    if (auxMax < p.performance())
                        auxMax = p.performance();
                }
                if (this.previousMaxFitness != auxMax)
                    this.contentIteration = 0;
                else if (this.contentIteration == this.content)
                    return true;
                this.contentIteration++;
                this.previousMaxFitness = auxMax;
                return false;
            default:
                return this.generations == this.iterations;
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
        Player aux2 = this.parents.get(0);
        HashSet<Item> boots = new HashSet<Item>();
        HashSet<Item> chest = new HashSet<Item>();
        HashSet<Item> gloves = new HashSet<Item>();
        HashSet<Item> helmet = new HashSet<Item>();
        HashSet<Item> weapon = new HashSet<Item>();
        HashSet<Double> height = new HashSet<Double>();

        double average = 0;
        for (Player p : this.parents ) {
            average += p.performance();
            if (p.performance() < aux.performance())
                aux = p;
            if (p.performance() > aux2.performance())
                aux2 = p;
            boots.add(p.getBoots());
            chest.add(p.getChest());
            gloves.add(p.getGloves());
            helmet.add(p.getHelmet());
            weapon.add(p.getWeapon());
            height.add(new Double(p.getHeight()));
        }

        this.avgFitness.add(average / this.parents.size());
        this.lowerFitness.add(aux.performance());
        this.maxFitness.add(aux2.performance());
        this.geneticDiversity.add(boots.size() + chest.size() + gloves.size() + helmet.size() + weapon.size() + height.size());

    }

    public void plotData() throws IOException, PythonExecutionException {
        Plot pltAvg = Plot.create();
        Plot pltMin = Plot.create();
        Plot pltGenes = Plot.create();

        System.out.print("AVG: ");
        for (Double d: this.avgFitness)
            System.out.print(d + ", ");
        System.out.print('\n');

        System.out.print("MIN: ");
        for (Double d: this.lowerFitness)
            System.out.print(d + ", ");
        System.out.print('\n');

        System.out.print("MAX: ");
        for (Double d: this.maxFitness)
            System.out.print(d + ", ");
        System.out.print('\n');

        System.out.print("Genetic Diversity: ");
        for (Integer i: this.geneticDiversity)
            System.out.print(i + ", ");
        System.out.print('\n');

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

        pltGenes.plot().add(this.geneticDiversity);
        pltGenes.xlabel("Iteration");
        pltGenes.ylabel("Genes amount");
        pltGenes.title("Genetic Diversity");
        pltGenes.legend();
        pltGenes.show();

        Player aux = this.parents.get(0);
        for (Player p : this.parents ) {
            if (p.performance() > aux.performance())
                aux = p;
        }

        System.out.print('\n');
        System.out.print('\n');
        System.out.println("Best player from last generation:");
        System.out.println("Fitness: " + aux.performance());
        System.out.println("Height: " + aux.getHeight());
        System.out.println("Boots: " + aux.getBoots().getId());
        System.out.println("Chest: " + aux.getChest().getId());
        System.out.println("Gloves: " + aux.getGloves().getId());
        System.out.println("Helmet: " + aux.getHelmet().getId());
        System.out.println("Weapon: " + aux.getWeapon().getId());
        System.out.print('\n');
        long now = System.nanoTime();
        double elapsedTimeInSecond = (double) (now - this.start) / 1000000000;
        System.out.println("Execution time: " + elapsedTimeInSecond + " seconds");
    }

}
