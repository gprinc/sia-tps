import BoardGame.*;

import java.util.ArrayList;

public class EjTwo {
    private ArrayList<ArrayList<Integer>> map;
    private int mapSize;

    private static final char wallChar = '#';
    private static final char winPointChar = '.';
    private static final char boxChar = '$';

    private static final String space = "00";
    private static final String wall = "01";
    private static final String winPoint = "10";
    private static final String box = "11";

    public EjTwo(int mapSize){
        this.map = new ArrayList<>();
        this.mapSize = mapSize;

        for (int i = 1; i < 6; i++) {
            BoardMain bm = new BoardMain(i);
            generateMap(bm);
        }
    }

    private void generateMap(BoardMain bm){
        Board board = bm.getBoard();
        char[][] charMap = board.print(false);
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < charMap.length; i++) {
            for (int j = 0; j < charMap[i].length; j++) {
                String item;
                switch (charMap[i][j]) {
                    case wallChar:
                        item = wall;
                        break;
                    case winPointChar:
                        item = winPoint;
                        break;
                    case boxChar:
                        item = box;
                        break;
                    default:
                        item = space;
                        break;
                }
                char[]c = item.toCharArray();
                for (int k = 0; k < c.length; k++) {
                    aux.add( c[k] - '0');
                }
            }
        }
        this.map.add(aux);
    }

    public ArrayList<ArrayList<Integer>> getMap() { return this.map; }

    public void playMap(ArrayList<Integer> mapa) {
        /*if (Math.sqrt(mapa.size()/2) != mapSize) {
            System.out.println("not a valid map");
            return;
        }*/
        int j = -1;
        ArrayList<Box> boxes = new ArrayList<>();
        ArrayList<Position> winPoints = new ArrayList<>();
        Position player = new Position(0,0);
        int walls[][] = new int[mapSize][mapSize];
        for (int i = 0; i < mapa.size(); i = i + 2) {
            if (i % (mapSize * 2) == 0) j++;
            String auxByte = mapa.get(i).toString() + mapa.get(i + 1).toString();
            int finalI = Math.floorDiv(i/2,mapSize);
            switch (auxByte) {
                case winPoint:
                    winPoints.add(new Position(finalI, j));
                    break;
                case box:
                    boxes.add(new Box(new Position(finalI, j)));
                    break;
            }
            int wall = 1;
            if (!auxByte.equals(wall)) wall = 0;
            System.out.println("[" + finalI + "]" +"[" + j + "]");
            walls[finalI][j] = wall;
        }
        Box[] boxesArray = new Box[boxes.size()];
        for (int i = 0; i < boxes.size(); i++) boxesArray[i] = boxes.get(i);
        Position[] winPointsArray = new Position[winPoints.size()];
        for (int i = 0; i < winPoints.size(); i++) winPointsArray[i] = winPoints.get(i);
        Board board = new Board(new Player(player), boxesArray, winPointsArray, walls, new Position(mapSize, mapSize), 0);
        board.print(false);
    }
}
