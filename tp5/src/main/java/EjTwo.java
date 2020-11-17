import BoardGame.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    public EjTwo(int mapSize, boolean shouldGenerate){
        this.map = new ArrayList<>();
        this.mapSize = mapSize;

        if (shouldGenerate) {
            for (int i = 1; i < 6; i++) {
                BoardMain bm = new BoardMain(i);
                generateMap(bm);
            }
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
        int j = -1;
        int k = 0;

        JSONObject jsonObject = new JSONObject();
        JSONArray winPointArrayObject = new JSONArray();
        JSONArray boxesArrayObject = new JSONArray();
        JSONObject winPointObject;
        JSONObject boxesObject;
        jsonObject.put("id", "1");
        jsonObject.put("width", String.valueOf(mapSize));
        jsonObject.put("height", String.valueOf(mapSize));

        ArrayList<int[]> walls = new ArrayList<>();
        ArrayList<int[]> boxes = new ArrayList<>();
        ArrayList<int[]> winPoints = new ArrayList<>();

        int y = -1;
        for (int i = 0; i < mapa.size(); i+=2) {
            String auxByte = mapa.get(i).toString() + mapa.get(i + 1).toString();
            int x = (i / 2) % mapSize;
            if (x == 0) y++;
            if (x == 0) System.out.println();
            switch (auxByte) {
                case winPoint:
                    System.out.print(winPointChar);
                    winPoints.add(new int[]{x, y});
                    break;
                case box:
                    System.out.print(boxChar);
                    boxes.add(new int[]{x, y});
                    break;
                case wall:
                    System.out.print(wallChar);
                    walls.add(new int[]{x, y});
                    break;
                default:
                    System.out.print('0');
                    break;
            }
        }

        char[][] wallsChar = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++)
            for (int l = 0; l < mapSize; l++)
                wallsChar[i][l] = '0';

        for (int[] aux:walls)
            wallsChar[aux[0]][aux[1]] = '1';

        for (int[] aux: winPoints) {
            winPointObject = new JSONObject();
            winPointObject.put("x", String.valueOf(aux[0]));
            winPointObject.put("y", String.valueOf(aux[1]));
            winPointArrayObject.add(winPointObject);
        }

        for (int[] aux: boxes) {
            boxesObject = new JSONObject();
            boxesObject.put("x", String.valueOf(aux[0]));
            boxesObject.put("y", String.valueOf(aux[1]));
            boxesArrayObject.add(boxesObject);
        }


        jsonObject.put("winPoints", winPointArrayObject);
        jsonObject.put("boxes", boxesArrayObject);


        char[] jsonCharArray = jsonObject.toJSONString().toCharArray();
        jsonCharArray[jsonObject.toJSONString().length() - 1] = ',';
        String jsonString = String.valueOf(jsonCharArray) + " walls: [";
        for (int i = 0; i < mapSize; i++) {
            jsonString += "[";
            for (int l = 0; l < mapSize; l++) {
                jsonString += String.valueOf('"' + wallsChar[i][j] + '"');
                if (l != mapSize - 1)
                    jsonString += ",";
            }
            jsonString += "]";
            if (i != mapSize - 1)
                jsonString += ",";
        }
        jsonString += "]}";

        try {
            FileWriter file = new FileWriter("mapa.json");
            file.write(jsonString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
