public class Implementation {

    public static Player[] fillAll(Player[] current, Player[] sons, double temperature0, double temperature1, int m, double b, String selection0, String selection1){
        Player[] aux = new Player[current.length];
        Player[] all = new Player[current.length + sons.length];

        int size = (int) Math.floor(current.length * b);
        if (current.length % 2 != 0)
            size++;

        int i = 0;
        for (Player p: current) {
            all[i] = p;
            i++;
        }

        for (Player p: sons) {
            all[i] = p;
            i++;
        }

        Player[] aux2 = new Player[size];

        switch(selection0) {
            case "elite":
                aux2 = Selection.elite(all, size);
                break;
            case "roulette":
                aux2 = Selection.roulette(all, size);
                break;
            case "universal":
                aux2 = Selection.universal(all, size);
                break;
            case "ranking":
                aux2 = Selection.ranking(all, size);
                break;
            case "boltzmann":
                aux2 = Selection.boltzmann(all, size, temperature0);
                break;
            case "dTournament":
                aux2 = Selection.dTournament(all, size, m);
                break;
            case "pTournament":
                aux2 = Selection.pTournament(all, size);
        }

        for (int j = 0; j < size; j++) {
            aux[j] =  new Player(aux2[j]);
        }

        aux2 = new Player[current.length - size];

        switch(selection1) {
            case "elite":
                aux2 = Selection.elite(all, current.length - size);
                break;
            case "roulette":
                aux2 = Selection.roulette(all, current.length - size);
                break;
            case "universal":
                aux2 = Selection.universal(all, current.length - size);
                break;
            case "ranking":
                aux2 = Selection.ranking(all, current.length - size);
                break;
            case "boltzmann":
                aux2 = Selection.boltzmann(all, current.length - size, temperature1);
                break;
            case "dTournament":
                aux2 = Selection.dTournament(all, current.length - size, m);
                break;
            case "pTournament":
                aux2 = Selection.pTournament(all, current.length - size);
        }

        for (int j = 0; j < current.length - size; j++) {
            aux[size + j] =  new Player(aux2[j]);
        }

        return aux;
    }

    public static Player[] fillParent(Player[] current, Player[] sons, double temperature0, double temperature1, int m, double b, String selection0, String selection1){
        Player[] aux = new Player[current.length];
        if (sons.length > current.length){
            int size = (int) Math.floor(sons.length * b);
            if (sons.length % 2 != 0)
                size++;
            Player[] aux2 = new Player[size];

            switch(selection0) {
                case "elite":
                    aux2 = Selection.elite(sons, size);
                    break;
                case "roulette":
                    aux2 = Selection.roulette(sons, size);
                    break;
                case "universal":
                    aux2 = Selection.universal(sons, size);
                    break;
                case "ranking":
                    aux2 = Selection.ranking(sons, size);
                    break;
                case "boltzmann":
                    aux2 = Selection.boltzmann(sons, size, temperature0);
                    break;
                case "dTournament":
                    aux2 = Selection.dTournament(sons, size, m);
                    break;
                case "pTournament":
                    aux2 = Selection.pTournament(sons, size);
            }

            for (int j = 0; j < size; j++) {
                aux[j] = new Player(aux2[j]);
            }

            aux2 = new Player[sons.length - size];

            switch(selection1) {
                case "elite":
                    aux2 = Selection.elite(sons, sons.length - size);
                    break;
                case "roulette":
                    aux2 = Selection.roulette(sons, sons.length - size);
                    break;
                case "universal":
                    aux2 = Selection.universal(sons, sons.length - size);
                    break;
                case "ranking":
                    aux2 = Selection.ranking(sons, sons.length - size);
                    break;
                case "boltzmann":
                    aux2 = Selection.boltzmann(sons, sons.length - size, temperature1);
                    break;
                case "dTournament":
                    aux2 = Selection.dTournament(sons, sons.length - size, m);
                    break;
                case "pTournament":
                    aux2 = Selection.pTournament(sons, sons.length - size);
            }
            for (int i = 0; i < sons.length - size; i++) {
                aux[size + i] =  new Player(aux2[i]);
            }
        } else {
            int size = (int) Math.floor((current.length - sons.length) * b);
            if ((current.length - sons.length) % 2 != 0)
                size++;
            Player[] aux2 = new Player[size];

            switch(selection0) {
                case "elite":
                    aux2 = Selection.elite(current, size);
                    break;
                case "roulette":
                    aux2 = Selection.roulette(current, size);
                    break;
                case "universal":
                    aux2 = Selection.universal(current, size);
                    break;
                case "ranking":
                    aux2 = Selection.ranking(current, size);
                    break;
                case "boltzmann":
                    aux2 = Selection.boltzmann(current, size, temperature0);
                    break;
                case "dTournament":
                    aux2 = Selection.dTournament(current, size, m);
                    break;
                case "pTournament":
                    aux2 = Selection.pTournament(current, size);
            }

            for (int j = 0; j < size; j++) {
                aux[j] =  new Player(aux2[j]);
            }

            int auxLength = (current.length - sons.length) - size;
            aux2 = new Player[auxLength];

            switch(selection1) {
                case "elite":
                    aux2 = Selection.elite(current, auxLength);
                    break;
                case "roulette":
                    aux2 = Selection.roulette(current, auxLength);
                    break;
                case "universal":
                    aux2 = Selection.universal(current, auxLength);
                    break;
                case "ranking":
                    aux2 = Selection.ranking(current, auxLength);
                    break;
                case "boltzmann":
                    aux2 = Selection.boltzmann(current, auxLength, temperature1);
                    break;
                case "dTournament":
                    aux2 = Selection.dTournament(current, auxLength, m);
                    break;
                case "pTournament":
                    aux2 = Selection.pTournament(current, auxLength);
            }
            for (int i = 0; i < auxLength; i++) {
                aux[size + i] =  new Player(aux2[i]);
            }

            int j = 0;
            for (Player p: sons) {
                aux[auxLength + size + j] =  new Player(p);
                j++;
            }
        }

        return aux;
    }
}
