import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Mate {

    public static LinkedList<Player> onePoint(LinkedList<Player> selected, Population p) {
        Random rn = new Random();
        int random = rn.nextInt(6);
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < selected.size() - 1; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

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
            for (int i = 0; i < selected.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

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

            gens0 = selected.get(0).getGens();
            gens1 = selected.get(selected.size() - 1).getGens();

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
        int r1 = rn.nextInt(6);
        int r2 = rn.nextInt(6);
        int max;
        int min;
        if (r1 >= r2) {
            max = r1;
            min = r2;
        } else {
            max = r2;
            min = r1;
        }
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < selected.size() - 1; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

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
            for (int i = 0; i < aux.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

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

            gens0 = selected.get(0).getGens();
            gens1 = selected.get(selected.size() - 1).getGens();

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
        int pivot = rn.nextInt(6) ;
        int len = rn.nextInt(3);
        LinkedList<Player> sons = new LinkedList<Player>();
        LinkedList<Player> aux = new LinkedList<Player>(selected);
        Collections.shuffle(aux);

        double[] gens0 = null;
        double[] gens1 = null;
        double[] son0 = new double[6];
        double[] son1 = new double[6];

        if (selected.size() % 2 == 0) {
            for (int i = 0; i < aux.size() - 1; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

                if (pivot + len < 6) {
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
                        if (j >= pivot || j <= (pivot + len - 5)) {
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
            for (int i = 0; i < aux.size() - 2; i++) {
                gens0 = selected.get(i).getGens();
                gens1 = selected.get(++i).getGens();

                if (pivot + len < 6) {
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
                        if (j >= pivot || j <= (pivot + len - 5)) {
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
            gens1 = selected.get(aux.size() - 1).getGens();

            if (pivot + len < 6) {
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
                    if (j >= pivot || j <= (pivot + len - 5)) {
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

        if (aux.size() % 2 == 0) {
            for (int i = 0; i < aux.size() - 1; i++) {
                gens0 = aux.get(i).getGens();
                gens1 = aux.get(i+1).getGens();
                i++;

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
            for (int i = 0; i < aux.size() - 2; i++) {
                gens0 = aux.get(i).getGens();
                gens1 = aux.get(i+1).getGens();
                i++;

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

            gens0 = aux.get(1).getGens();
            gens1 = aux.get(aux.size() - 1).getGens();

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
