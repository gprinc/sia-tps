import BoardGame.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
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
        int printMap[][] = new int[mapSize][mapSize];
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
                    printMap[finalI][j] = 2;
                    winPoints.add(new Position(finalI, j));
                    break;
                case box:
                    printMap[finalI][j] = 3;
                    boxes.add(new Box(new Position(finalI, j)));
                    break;
                case wall:
                    printMap[finalI][j] = 1;
                    break;
                default:
                    printMap[finalI][j] = 0;
                    break;
            }
            int wall = 1;
            if (!auxByte.equals(wall)) wall = 0;
            walls[finalI][j] = wall;
        }
        /*Box[] boxesArray = new Box[boxes.size()];
        for (int i = 0; i < boxes.size(); i++) boxesArray[i] = boxes.get(i);
        Position[] winPointsArray = new Position[winPoints.size()];
        for (int i = 0; i < winPoints.size(); i++) winPointsArray[i] = winPoints.get(i);
        Board board = new Board(new Player(player), boxesArray, winPointsArray, walls, new Position(mapSize, mapSize), 0);*/

        System.out.println();
        for (int i = 0; i < printMap.length; i++) {
            for (int k = 0; k < printMap[i].length; k++) {
                System.out.print(printMap[i][k] + " ");
            }
            System.out.println();
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", "1");
        jsonObject.put("width", String.valueOf(mapSize));
        jsonObject.put("height", String.valueOf(mapSize));

        JSONObject auxObject = new JSONObject();
        auxObject.put("x", "0");
        auxObject.put("y", "0");
        jsonObject.put("player", auxObject);

        JSONArray auxArrayObject = new JSONArray();
        for (Position p: winPoints) {
            auxObject = new JSONObject();
            auxObject.put("x", String.valueOf(p.getX()));
            auxObject.put("y", String.valueOf(p.getY()));
            auxArrayObject.add(auxObject);
        }
        jsonObject.put("winPoints", auxArrayObject);

        auxArrayObject = new JSONArray();
        for (Box b: boxes) {
            auxObject = new JSONObject();
            auxObject.put("x", String.valueOf(b.getPos().getX()));
            auxObject.put("y", String.valueOf(b.getPos().getY()));
            auxArrayObject.add(auxObject);
        }
        jsonObject.put("boxes", auxArrayObject);

        auxArrayObject = new JSONArray();
        for (int i = 0; i < walls.length; i++) {
            String[] auxStringArr = new String[walls[i].length];
            for (int k = 0; k < walls[i].length; k++) {
                auxStringArr[i] = String.valueOf(walls[i][j]);
            }
            auxArrayObject.add(auxStringArr);
        }
        jsonObject.put("walls", auxArrayObject);

        try {
            FileWriter file = new FileWriter("mapa.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
