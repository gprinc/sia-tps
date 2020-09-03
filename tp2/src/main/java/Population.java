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
    private double temperature = 0;
    private int t0 = 0;
    private int tc = 0;
    private String mutation;
    private double pm = 0.5;
    private int limitm = 6;

    public Population(ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList) {
        this.bootList = bootList;
        this.weaponsList = weaponsList;
        this.helmetList = helmetList;
        this.glovesList = glovesList;
        this.chestList = chestList;
    }

    public void init (int size, String type, int k, double selectionValue, String selectionType0, String selectionType1, int t0, int tc, String mutation, double pm, int limitm) {
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
                System.out.println(aux.length);
                break;
            case "roulette":
                aux = Selection.roulette(selectedList,size);
                System.out.println(aux.length);
                break;
            case "universal":
                aux = Selection.universal(selectedList,size);
                System.out.println(aux.length);
                break;
            case "ranking":
                aux = Selection.ranking(selectedList,size);
                System.out.println(aux.length);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(selectedList,size,this.temperature());
                System.out.println(aux.length);
                break;
            case "dTournament":
                aux = Selection.dTournament(selectedList,size);
                System.out.println(aux.length);
                break;
            case "pTournament":
                aux = Selection.pTournament(selectedList,size);
                break;
        }

        for (Player p : aux ){
            this.selected.add(p);
        }

        aux = new Player[k -size];

        switch(this.selectionType[1]) {
            case "elite":
                aux = Selection.elite(selectedList,k - size);
                System.out.println(aux.length);
                break;
            case "roulette":
                aux = Selection.roulette(selectedList,k - size);
                System.out.println(aux.length);
                break;
            case "universal":
                aux = Selection.universal(selectedList,k - size);
                System.out.println(aux.length);
                break;
            case "ranking":
                System.out.println(selectedList.length);
                aux = Selection.ranking(selectedList,k - size);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(selectedList,k - size,this.temperature());
                System.out.println(aux.length);
                break;
            case "dTournament":
                aux = Selection.dTournament(selectedList,k - size);
                System.out.println(aux.length);
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
        switch(this.selectionType[1]) {
            case "onePoint":
                sons = Mate.onePoint(selected, this);
                break;
            case "twoPoints":
                sons = Mate.twoPoints();
                break;
            case "anular":
                sons = Mate.anular();
                break;
            case "uniform":
                sons = Mate.uniform();
                break;
        }
    }

    public void mutate () {
        LinkedList<Player> sonsAux = new LinkedList<Player>();
        switch (this.mutation) {
            case "gene":
                for (Player p: sons) {
                    sonsAux.add(Mutation.gene(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
                break;
            case "limitedMultigene":
                for (Player p: sons) {
                    sonsAux.add(Mutation.limitedMultigene(p, bootList, weaponsList, helmetList, glovesList, chestList, limitm, pm));
                }
                break;
            case "uniformMultigene":
                for (Player p: sons) {
                    sonsAux.add(Mutation.uniformMultigene(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
                break;
            case "complete":
                for (Player p: sons) {
                    sonsAux.add(Mutation.complete(p, bootList, weaponsList, helmetList, glovesList, chestList, pm));
                }
        }
        sons = sonsAux;
    }

    private double temperature() {
        this.temperature = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature);
        return this.temperature;
    }

    public boolean hasTerminated () {
        return true;
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
}
