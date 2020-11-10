import java.util.Random;

public class LayerCreator {

    public static int[] generateLayer(int n) {
        Random r = new Random();
        int layers = r.nextInt(20) + 5;
        int[] aux = new int[3+(layers*2)];
        for (int i = 0; i < aux.length; i++) {
            aux[i]= r.nextInt(n - (int)(n * 0.2)) + (int)(n * 0.2);
            aux[i] = n;
        }
        aux[0]= n;
        aux[aux.length-1] = n;
        aux[layers+1] = n;

        System.out.println("\n********** Layers **********\n");
        for (int i = 0; i < aux.length; i++) {
            System.out.print(aux[i] + " ");
        }
        System.out.println();

        return aux;
    }
}
