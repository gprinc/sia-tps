import java.util.Random;

public class LayerCreator {

    private int[] aux;
    private int[] auxInv;

    public LayerCreator(int n){
        this.generateLayer(n);
        this.generateReverseLayer();
    }

    private void generateLayer(int n) {
        Random r = new Random();
        int layers = r.nextInt(20) + 5;
        aux = new int[3+(layers*2)];
        auxInv = new int[3+(layers*2)];
        for (int i = 0; i < (aux.length - 1) / 2; i++) {
            int auxR = r.nextInt(n - (int)(n * 0.6)) + (int)(n * 0.6);
            aux[i] = n;
            aux[aux.length - (i+1)] = n;

        }
        aux[0]= n;
        aux[aux.length-1] = n;
        aux[layers+1] = n;
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
}
