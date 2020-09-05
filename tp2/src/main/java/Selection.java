import java.util.*;

public class Selection {

    public static Player[] elite(Player[] players, int k){
        Player[] aux = new Player[k];
        int aux2 = 0;

        PriorityQueue<Player> relative = new PriorityQueue<Player>(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.performance() - o2.performance());
            }
        });

        while (aux2 <= k) {
            for (Player p : players) {
                if (aux2 == k)
                    break;
                relative.add(p);
                aux2++;
            }
        }

        for (int i = 0; i < k; i++)
            aux[i] = relative.poll();

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
                    break;
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
                    break;
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
        double[] accumulated = new double[players.length];

        double total = (players.length * (players.length + 1)) / 2.0; // la formula para tener el total de al sumatoria de los numeros de 0 a N

        for (int i = 0; i < relative.length; i++) {
            relative[i] = (double) ( i + 1 ) / total;
        }

        accumulated[0] = relative[0];

        for (int i = 1; i < accumulated.length; i++) {
            accumulated[i] = relative[i] + accumulated[i-1];
        }

        for (int i = 0; i < k; i++) {
            double random = Math.random();
            for (int j = 0; j < accumulated.length; j++) {
                if (accumulated[j] >= random) {
                    aux[i] = auxList.get(j);
                    break;
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
            relative[i] = Math.exp(players[i].performance()/t)/(total / players.length);
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
                    break;
                }
            }
        }

        return aux;
    }


    public static Player[] dTournament(Player[] players, int k, int m){
        Player[] aux = new Player[k];
        LinkedList<Player> auxPlayers = new LinkedList<>();
        for (Player p: players)
            auxPlayers.add(p);
        Collections.shuffle(auxPlayers);
        Player auxP = null;
        for (int i = 0; i < k; i++) {
            auxP = auxPlayers.get(0);
            for (int j = 1; j < m; j++) {
                if(auxP.performance() < auxPlayers.get(j).performance())
                    auxP = auxPlayers.get(j);
            }
            aux[i] = auxP;
        }

        return aux;
    }

    public static Player[] pTournament(Player[] players, int k){
        Player[] aux = new Player[k];
        Random rn = new Random();
        int player1;
        int player2;

        double treshhold = (Math.random()*(1 - 0.5)) + 0.5;

        for (int i = 0; i < k; i++) {
            player1 = rn.nextInt(players.length);
            while((player2 = rn.nextInt(players.length)) == player1);

            Player auxPlayer = null;
            double r = Math.random();
            if (r < treshhold) {
                if (players[player1].performance() < players[player2].performance())
                    auxPlayer = players[player2];
                else
                    auxPlayer = players[player1];
            } else {
                if (players[player1].performance() < players[player2].performance())
                    auxPlayer = players[player1];
                else
                    auxPlayer = players[player2];
            }
            aux[i] = auxPlayer;
        }
        return aux;
    }
}
