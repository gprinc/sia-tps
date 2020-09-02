import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Population {
    private LinkedList<Player> parents;
    private LinkedList<Player> sons;
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

    public Population(ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList) {
        this.bootList = bootList;
        this.weaponsList = weaponsList;
        this.helmetList = helmetList;
        this.glovesList = glovesList;
        this.chestList = chestList;
    }

    public void init (int size, String type, int k, double selectionValue, String selectionType0,String selectionType1, int t0,int tc) {
        this.populationSize = size;
        this.type = type;
        this.k = k;
        this.selectionValue = selectionValue;
        this.parents = new LinkedList<Player>();
        this.selectionType[0] = selectionType0;
        this.selectionType[1] = selectionType1;
        this.t0= t0;
        this.tc = tc;

        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            Player aux = new Player(1.3 + (2.0 - 1.3) * rand.nextDouble(),chestList.get(rand.nextInt((chestList.size()) + 1)),glovesList.get(rand.nextInt((glovesList.size()) + 1)),helmetList.get(rand.nextInt((helmetList.size()) + 1)),weaponsList.get(rand.nextInt((weaponsList.size()) + 1)),bootList.get(rand.nextInt((bootList.size()) + 1)),type);
            this.parents.add(aux);
        }
    }


    public void selection () {
        this.sons = new LinkedList<Player>();
        int size = (int) Math.floor(k*selectionValue);
        if (k % 2 != 0) {
            size +=1;
        }

        Player[] aux = new Player[size];
        Player[] sonsAux = new Player[this.sons.size()];

        switch(this.selectionType[0]) {
            case "elite":
                aux = Selection.elite(sons.toArray(sonsAux),size);
                System.out.println(aux.length);
                break;
            case "roulette":
                aux = Selection.roulette(sons.toArray(sonsAux),size);
                System.out.println(aux.length);
                break;
            case "universal":
                aux = Selection.universal(sons.toArray(sonsAux),size);
                System.out.println(aux.length);
                break;
            case "ranking":
                aux = Selection.ranking(sons.toArray(sonsAux),size);
                System.out.println(aux.length);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(sons.toArray(sonsAux),size,this.temperature());
                System.out.println(aux.length);
                break;
            case "dTournament":
                aux = Selection.dTournament(sons.toArray(sonsAux),size);
                System.out.println(aux.length);
                break;
            case "pTournament":
                aux = Selection.pTournament(sons.toArray(sonsAux),size);
                break;
        }

        for (Player p : aux ){
            this.sons.add(p);
        }

        aux = new Player[k -size];

        switch(this.selectionType[1]) {
            case "elite":
                aux = Selection.elite(sons.toArray(sonsAux),k - size);
                System.out.println(aux.length);
                break;
            case "roulette":
                aux = Selection.roulette(sons.toArray(sonsAux),k - size);
                System.out.println(aux.length);
                break;
            case "universal":
                aux = Selection.universal(sons.toArray(sonsAux),k - size);
                System.out.println(aux.length);
                break;
            case "ranking":
                aux = Selection.ranking(sons.toArray(sonsAux),k - size);
                System.out.println(aux.length);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(sons.toArray(sonsAux),k - size,this.temperature());
                System.out.println(aux.length);
                break;
            case "dTournament":
                aux = Selection.dTournament(sons.toArray(sonsAux),k - size);
                System.out.println(aux.length);
                break;
            case "pTournament":
                aux = Selection.pTournament(sons.toArray(sonsAux),k - size);
                break;
        }

        for (Player p : aux ){
            this.sons.add(p);
        }
    }

    public void mate () {

    }

    public void mutate () {

    }

    private double temperature() {
        this.temperature = this.tc + (this.t0 - this.tc) * Math.exp((-1 * (1.3806488 * Math.pow(10,-16))) * this.temperature);
        return this.temperature;
    }

    public boolean hasTerminated () {
        return true;
    }
}
