import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Mate {
    public static LinkedList<Player> onePoint(LinkedList<Player> selected, Population p) {
        Random rn = new Random();
        int random = rn.nextInt(5 - 0 + 1) + 0;
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < selected.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if (j < random) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }
        } else {
            for (int i = 0; i < aux.size() - 3; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if (j < random) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }

            gens0 = selected.get(1).getGens();
            gens1 = selected.get(aux.size()).getGens();

            for (int j = 0; j < 6; j++) {
                if (j < random) {
                    son0[j]= gens0[j];
                    son1[j]= gens1[j];
                }
                else  {
                    son0[j]= gens1[j];
                    son1[j]= gens0[j];
                }
            }
            sons.add(p.generate(son0));
            sons.add(p.generate(son1));
        }

        return sons;
    }

    public static LinkedList<Player> twoPoints(LinkedList<Player> selected, Population p) {

        Random rn = new Random();
        int max = rn.nextInt(5 - 0 + 1) + 0;
        int min = rn.nextInt(max - 0 + 1) + 0;
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < aux.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if (j <= max && j >= min ) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }
        } else {
            for (int i = 0; i < aux.size() - 3; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if (j <= max && j >= min ) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }

            gens0 = selected.get(1).getGens();
            gens1 = selected.get(aux.size()).getGens();

            for (int j = 0; j < 6; j++) {
                if (j <= max && j >= min ) {
                    son0[j]= gens0[j];
                    son1[j]= gens1[j];
                }
                else  {
                    son0[j]= gens1[j];
                    son1[j]= gens0[j];
                }
            }
            sons.add(p.generate(son0));
            sons.add(p.generate(son1));
        }
        return sons;
    }

    public static LinkedList<Player> anular(LinkedList<Player> selected, Population p) {
        Random rn = new Random();
        int pivot = rn.nextInt(5 - 0 + 1) + 0;
        int len = rn.nextInt(3 - 0 + 1) + 0;
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < aux.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                if (pivot + len < 5) {
                    for (int j = 0; j < 6; j++) {
                        if (j <= (pivot + len) && j >= pivot ) {
                            son0[j]= gens0[j];
                            son1[j]= gens1[j];
                        }
                        else  {
                            son0[j]= gens1[j];
                            son1[j]= gens0[j];
                        }
                    }
                } else {
                    for (int j = 0; j < 6; j++) {
                        if (j >= pivot || j <= (pivot + len -5)) {
                            son0[j]= gens0[j];
                            son1[j]= gens1[j];
                        }
                        else  {
                            son0[j]= gens1[j];
                            son1[j]= gens0[j];
                        }
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }
        } else {
            for (int i = 0; i < aux.size() - 3; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                if (pivot + len < 5) {
                    for (int j = 0; j < 6; j++) {
                        if (j <= (pivot + len) && j >= pivot ) {
                            son0[j]= gens0[j];
                            son1[j]= gens1[j];
                        }
                        else  {
                            son0[j]= gens1[j];
                            son1[j]= gens0[j];
                        }
                    }
                } else {
                    for (int j = 0; j < 6; j++) {
                        if (j >= pivot || j <= (pivot + len -5)) {
                            son0[j]= gens0[j];
                            son1[j]= gens1[j];
                        }
                        else  {
                            son0[j]= gens1[j];
                            son1[j]= gens0[j];
                        }
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }

            gens0 = selected.get(1).getGens();
            gens1 = selected.get(aux.size()).getGens();

            if (pivot + len < 5) {
                for (int j = 0; j < 6; j++) {
                    if (j <= (pivot + len) && j >= pivot ) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }
            } else {
                for (int j = 0; j < 6; j++) {
                    if (j >= pivot || j <= (pivot + len -5)) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }
            }
            
            sons.add(p.generate(son0));
            sons.add(p.generate(son1));
        }
        return sons;
    }

    public static LinkedList<Player> uniform(LinkedList<Player> selected, Population p) {
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < selected.size() - 2; i++) {
                System.out.println(selected.get(i+1));
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if ( Math.random() < 0.5) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }
        } else {
            for (int i = 0; i < aux.size() - 3; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(i+1).getGens();

                for (int j = 0; j < 6; j++) {
                    if (Math.random() < 0.5 ) {
                        son0[j]= gens0[j];
                        son1[j]= gens1[j];
                    }
                    else  {
                        son0[j]= gens1[j];
                        son1[j]= gens0[j];
                    }
                }

                sons.add(p.generate(son0));
                sons.add(p.generate(son1));
            }

            gens0 = selected.get(1).getGens();
            gens1 = selected.get(aux.size()).getGens();

            for (int j = 0; j < 6; j++) {
                if (Math.random() < 0.5) {
                    son0[j]= gens0[j];
                    son1[j]= gens1[j];
                }
                else  {
                    son0[j]= gens1[j];
                    son1[j]= gens0[j];
                }
            }
            sons.add(p.generate(son0));
            sons.add(p.generate(son1));
        }

        return sons;
    }
}
