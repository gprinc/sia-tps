import java.util.Random;

public class LayerCreator {

    public static int[] generateLayer(int n) {
        Random r = new Random();
        int layers = r.nextInt(20) + 1;
        int[] aux = new int[3+(layers*2)];
        for (int i = 0; i < aux.length; i++) {
            aux[i]= r.nextInt(n-1) + (int)(n*0.2);
        }
        aux[0]= n;
        aux[aux.length-1] = n;
        aux[layers+1]= 2;

        return aux;
    }
}
