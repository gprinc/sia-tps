import java.util.Random;

public class LayerCreator {

    private int[] aux;
    private int[] auxInv;
    private static double error = 0;
    private static int length;
    private static double min;
    private static int middle = 2;

    public LayerCreator(int n, int mid) {
        middle = mid;
        this.generateLayer(n);
        this.generateReverseLayer();
    }

    public static void init() {
        LayerCreator.length = 5;
        LayerCreator.min = 0.6;
    }

    private void generateLayer(int n) {
        Random r = new Random();
        int layers = length;
        aux = new int[3+(layers*2)];
        auxInv = new int[3+(layers*2)];
        for (int i = 0; i < (aux.length - 1) / 2; i++) {
            int auxR = r.nextInt(n - (int)(n * min)) + (int)(n * min);
            aux[i] = auxR;
            aux[aux.length - (i+1)] = auxR;

        }
        aux[0]= n;
        aux[aux.length-1] = n;
        aux[layers+1] = middle;

        /*
        aux= new int[3];
        aux[0] = n;
        aux[1] = n;
        aux[2] = n;
         */
    }

    private void generateReverseLayer() {
        int[] b = aux;
        int j = aux.length;
        for (int i = 0; i < (aux.length-3) / 2; i++) {
            auxInv[j - 1] = b[i];
            j = j - 1;
        }
    }

    public int[] getLayer() {
        System.out.println("\n********** Layers **********\n");
        for (int i = 0; i < aux.length; i++) {
            System.out.print(aux[i] + " ");
        }
        System.out.println();
        return aux;
    }

    public int[] getReverseLayer() {
        System.out.println("\n********** Layers **********\n");
        for (int i = 0; i < auxInv.length; i++) {
            System.out.print(auxInv[i] + " ");
        }
        System.out.println();
        return auxInv;
    }

    public static void update(double err) {
        if (err < LayerCreator.error) {
            LayerCreator.length += 1;
            LayerCreator.min += 0.01;
        } else {
            if (LayerCreator.length > 5)
            LayerCreator.length -= 1;
            if (min > 0.2)
            LayerCreator.min -=  0.01;
        }


        LayerCreator.error = err;
    }
}
