public class Selection {

    public Player[] roulette(Player[] players, int k){
        Player[] aux = new Player[k];
        double total = 0;
        double[] relative = new double[aux.length];
        double[] accumulated = new double[aux.length];

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
            for (int j = 1; j < accumulated.length; j++) {
                if (accumulated[j] > random) {
                    aux[i] = players[j-1];
                }
            }
        }

        return aux;
    }
}
