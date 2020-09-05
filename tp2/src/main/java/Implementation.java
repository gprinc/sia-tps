public class Implementation {

    public static Player[] fillAll(Player[] current, Player[] sons, String selection, double temperature, int m){
        Player[] aux = new Player[current.length];
        Player[] all = new Player[current.length + sons.length];

        int i = 0;
        for (Player p: current) {
            all[i] = p;
            i++;
        }

        for (Player p: sons) {
            all[i] = p;
            i++;
        }

        switch(selection) {
            case "elite":
                aux = Selection.elite(all, current.length);
                break;
            case "roulette":
                aux = Selection.roulette(all, current.length);
                break;
            case "universal":
                aux = Selection.universal(all, current.length);
                break;
            case "ranking":
                aux = Selection.ranking(all, current.length);
                break;
            case "boltzmann":
                aux = Selection.boltzmann(all, current.length, temperature);
                break;
            case "dTournament":
                aux = Selection.dTournament(all, current.length, m);
                break;
            case "pTournament":
                aux = Selection.pTournament(all, current.length);
        }

        return aux;
    }

    public static Player[] fillParent(Player[] current, Player[] sons, String selection, double temperature, int m){
        Player[] aux = new Player[current.length];
        if (sons.length > current.length){
            switch(selection) {
                case "elite":
                    aux = Selection.elite(sons, current.length);
                    break;
                case "roulette":
                    aux = Selection.roulette(sons, current.length);
                    break;
                case "universal":
                    aux = Selection.universal(sons, current.length);
                    break;
                case "ranking":
                    aux = Selection.ranking(sons, current.length);
                    break;
                case "boltzmann":
                    aux = Selection.boltzmann(sons, current.length, temperature);
                    break;
                case "dTournament":
                    aux = Selection.dTournament(sons, current.length, m);
                    break;
                case "pTournament":
                    aux = Selection.pTournament(sons, current.length);
            }
        }else {
            Player[] aux2 = new Player[current.length - sons.length];
            switch(selection) {
                case "elite":
                    aux2 = Selection.elite(current, current.length - sons.length);
                    break;
                case "roulette":
                    aux2 = Selection.roulette(current, current.length - sons.length);
                    break;
                case "universal":
                    aux2 = Selection.universal(current, current.length - sons.length);
                    break;
                case "ranking":
                    aux2 = Selection.ranking(current, current.length - sons.length);
                    break;
                case "boltzmann":
                    aux2 = Selection.boltzmann(current, current.length - sons.length, temperature);
                    break;
                case "dTournament":
                    aux2 = Selection.dTournament(current, current.length - sons.length, m);
                    break;
                case "pTournament":
                    aux2 = Selection.pTournament(current, current.length - sons.length);
            }

            int i = 0;
            for (Player p: aux2) {
                aux[i] = p;
                i++;
            }

            for (Player p: sons) {
                aux[i] = p;
                i++;
            }
        }

        return aux;
    }
}
