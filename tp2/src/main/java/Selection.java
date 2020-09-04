import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class Selection {
    public static Player[] elite(Player[] players, int k){
        Player[] aux = new Player[k];
        double total = 0;

        PriorityQueue<Player> relative = new PriorityQueue<Player>(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.performance() - o2.performance());
            }
        });

        for (Player p : players) {
            relative.add(p);
            total+= p.performance();
        }


        for (int i = 0; i < k; i++) {
            aux[i] = relative.poll();
        }

        if  (k > players.length) {
            for (int i = 0; i < (k - relative.size()); i++) {
                aux[relative.size() + i] = relative.poll();
            }
        }

        return aux;
    }

    public static Player[] roulette(Player[] players, int k){
        Player[] aux = new Player[k];
        double total = 0;
        double[] relative = new double[players.length];
        double[] accumulated = new double[players.length];

        for (Player p : players) {
            total+= p.performance();
        }

        for (int i = 0; i < relative.length; i++) {
            relative[i] = players[i].performance()/total;
        }

        accumulated[0] = relative[0];

        for (int i = 1; i < accumulated.length; i++) {
            accumulated[i] = relative[i] + accumulated[i-1];
        }

        for (int i = 0; i < k; i++) {
            double random = Math.random();
            for (int j = 0; j < accumulated.length; j++) {
                if (accumulated[j] >= random) {
                    aux[i] = players[j];
                }
            }
        }

        return aux;
    }

    public static Player[] universal(Player[] players, int k){
        Player[] aux = new Player[k];
        double total = 0;
        double[] relative = new double[players.length];
        double[] accumulated = new double[players.length];

        for (Player p : players) {
            total+= p.performance();
        }

        for (int i = 0; i < relative.length; i++) {
            relative[i] = players[i].performance()/total;
        }
        accumulated[0] = relative[0];
        for (int i = 1; i < accumulated.length; i++) {
            accumulated[i] = relative[i] + accumulated[i-1];
        }
        double random = Math.random();

        for (int i = 0; i < k; i++) {
            double r = (random + i)/k;
            for (int j = 1; j < accumulated.length; j++) {
                if (accumulated[j] > r) {
                    aux[i] = players[j-1];
                }
            }
        }
        return aux;
    }

    public static Player[] ranking(Player[] players, int k){
        Player[] aux = new Player[k];

        PriorityQueue<Player> listAux = new PriorityQueue<Player>(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.performance() - o2.performance());
            }
        });

        LinkedList<Player> auxList = new LinkedList<Player>();

        for (Player p : players) {
            listAux.add(p);
        }

        for (Player p : listAux) {
            auxList.add(p);
        }

        double[] relative = new double[players.length];
        int total = players.length;

        for (int i = 0; i < relative.length; i++) {
            relative[i] = (double) (total - i)/total;
        }

        Player[] orderPlayer = new Player[listAux.size()];
        listAux.toArray(orderPlayer);

        for (int i = 0; i < k; i++) {
            double random = Math.random();
            for (int j = 0; j < relative.length; j++) {
                if (relative[j] >= random) {
                    aux[i] = auxList.get(j);
                }
            }
        }

        return aux;
    }

    public static Player[] boltzmann(Player[] players, int k, double t){
        Player[] aux = new Player[k];
        double total = 0;
        double[] relative = new double[players.length];
        double[] accumulated = new double[players.length];

        for (Player p : players) {
            total+= Math.exp(p.performance()/t);
        }

        for (int i = 0; i < relative.length; i++) {
            relative[i] = Math.exp(players[i].performance()/t)/total;
        }

        accumulated[0] = relative[0];

        for (int i = 1; i < accumulated.length; i++) {
            accumulated[i] = relative[i] + accumulated[i-1];
        }

        for (int i = 0; i < k; i++) {
            double random = Math.random();
            for (int j = 1; j < accumulated.length; j++) {
                if (accumulated[j] > random) {
                    aux[i] = players[j-1];
                }
            }
        }

        return aux;
    }

    public static Player[] dTournament(Player[] players, int k){
        Player[] aux = new Player[k];
        double total = 0;
        double[] relative = new double[players.length];
        double[] accumulated = new double[players.length];

        for (Player p : players) {
            total+= p.performance();
        }

        for (int i = 0; i < relative.length; i++) {
            relative[i] = players[i].performance()/total;
        }
        accumulated[0] = relative[0];
        for (int i = 1; i < accumulated.length; i++) {
            accumulated[i] = relative[i] + accumulated[i-1];
        }
        double random = Math.random();

        for (int i = 0; i < k; i++) {
            double r = (random + i)/k;
            for (int j = 1; j < accumulated.length; j++) {
                if (accumulated[j] > r) {
                    aux[i] = players[j-1];
                }
            }
        }
        return aux;
    }

    public static Player[] pTournament(Player[] players, int k){
        Player[] aux = new Player[k];
        Random rn = new Random();

        double treshhold = (Math.random()*(1 - 0.5)) + 0.5;

        for (int i = 0; i < k; i++) {
            int max = rn.nextInt(players.length + 1);
            int min = rn.nextInt(max + 1);
            Player auxPlayer = null;
            double r = Math.random();
            for (int j = min; j < max; j++) {
                if (auxPlayer != null ) {
                    if (r <= treshhold) {
                        if (auxPlayer.performance() < players[j].performance())
                            auxPlayer = players[j];
                    } else {
                        if (auxPlayer.performance() > players[j].performance())
                            auxPlayer = players[j];
                    }
                } else {
                    auxPlayer = players[j];
                }
            }
            aux[i] = auxPlayer;
        }
        return aux;
    }
}
