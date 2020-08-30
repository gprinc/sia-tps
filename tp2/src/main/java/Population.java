import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Population {
    private LinkedList<Player> parents;
    private LinkedList<Player> sons;

    public Population(LinkedList<Player> parents, LinkedList<Player> sons) {
        this.parents = parents;
        this.sons = sons;
    }

    public static LinkedList<Player> generatePopulation (ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, int size) {
        LinkedList<Player> population = new LinkedList<Player>();

        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            Player aux = new Player(1.3 + (2.0 - 1.3) * rand.nextDouble(),(Chest) chestList.get(rand.nextInt((chestList.size()) + 1)),(Gloves) glovesList.get(rand.nextInt((glovesList.size()) + 1)),(Helmet) helmetList.get(rand.nextInt((helmetList.size()) + 1)),(Weapon) weaponsList.get(rand.nextInt((weaponsList.size()) + 1)),(Boots) bootList.get(rand.nextInt((bootList.size()) + 1)));
            population.add(aux);
        }

        return  population;
    }
}
