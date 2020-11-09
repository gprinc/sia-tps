import BoardGame.Board;
import BoardGame.BoardMain;
import java.util.ArrayList;

public class EjTwo {
    private ArrayList<ArrayList<Integer>> map;

    private static final char wallChar = '#';
    private static final char winPointChar = '.';
    private static final char boxChar = '$';
    private static final char playerChar = '@';

    private static final int spaceInt = 0;
    private static final int wallInt = 1;
    private static final int winPointInt = 2;
    private static final int boxInt = 3;
    private static final int playerInt = 4;

    public EjTwo(String mapa){
        this.map = new ArrayList<>();

        BoardMain bm = new BoardMain(mapa);
        generateMap(bm);
    }

    private void generateMap(BoardMain bm){
        Board board = bm.getBoard();
        char[][] charMap = board.print(false);
        ArrayList<Integer> aux;
        for (int i = 0; i < charMap.length; i++) {
            aux = new ArrayList<>();
            for (int j = 0; j < charMap[i].length; j++) {
                switch (charMap[i][j]) {
                    case wallChar:
                        aux.add(wallInt);
                        break;
                    case winPointChar:
                        aux.add(winPointInt);
                        break;
                    case boxChar:
                        aux.add(boxInt);
                        break;
                    case playerChar:
                        aux.add(playerInt);
                        break;
                    default:
                        aux.add(spaceInt);
                        break;
                }
            }
            this.map.add(aux);
        }
    }

    public ArrayList<ArrayList<Integer>> getMap() { return this.map; }
}
