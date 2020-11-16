import BoardGame.*;

import java.util.ArrayList;

public class EjTwo {
    private ArrayList<ArrayList<Integer>> map;
    private int mapSize;

    private static final char wallChar = '#';
    private static final char winPointChar = '.';
    private static final char boxChar = '$';
    private static final char playerChar = '@';

    private static final int spaceInt = 0;
    private static final int wallInt = 1;
    private static final int winPointInt = 2;
    private static final int boxInt = 3;
    private static final int playerInt = 4;

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
        }
        this.map.add(aux);
    }

    public ArrayList<ArrayList<Integer>> getMap() { return this.map; }

    public void playMap(ArrayList<Integer> mapa) {
        int j = -1;
        ArrayList<Box> boxes = new ArrayList<>();
        ArrayList<Position> winPoints = new ArrayList<>();
        Position player = new Position(0,0);
        int walls[][] = new int[mapSize][mapSize];
        for (int i = 0; i < mapa.size(); i++) {
            if (i % mapSize == 0) j++;
            switch (mapa.get(i)) {
                case 2:
                    winPoints.add(new Position(Math.floorDiv(i,j), j));
                    break;
                case 3:
                    boxes.add(new Box(new Position(Math.floorDiv(i,j), j)));
                    break;
                case 4:
                    player = new Position(Math.floorDiv(i,j), j);
                    break;
            }
            int wall = 1;
            if (mapa.get(i) != 1) wall = 0;
            walls[Math.floorDiv(i,j)][j] = wall;
        }
        Box[] boxesArray = new Box[boxes.size()];
        for (int i = 0; i < boxes.size(); i++) boxesArray[i] = boxes.get(i);
        Position[] winPointsArray = new Position[winPoints.size()];
        for (int i = 0; i < winPoints.size(); i++) winPointsArray[i] = winPoints.get(i);
        Board board = new Board(new Player(player), boxesArray, winPointsArray, walls, new Position(mapSize, mapSize), 0);
        board.print(false);
    }
}
