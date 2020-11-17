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
        for (Integer asd: mapa)
            System.out.print(asd);
        System.out.println();

        int j = -1;
        int i = 0;
        int walls[][] = new int[mapSize][mapSize];
        for (int k = 0; k < walls.length; k++)
            Arrays.fill(walls[k], 0);

        JSONObject jsonObject = new JSONObject();
        JSONArray winPointArrayObject = new JSONArray();
        JSONArray boxesArrayObject = new JSONArray();
        JSONArray wallsArrayObject = new JSONArray();
        String[] wallsRow = new String[mapSize];
        for (int k = 0; k < wallsRow.length; k++)
            wallsRow[k] = "1";
        JSONObject winPointObject;
        JSONObject boxesObject;
        jsonObject.put("id", "1");
        jsonObject.put("width", String.valueOf(mapSize));
        jsonObject.put("height", String.valueOf(mapSize));

        while (i < mapa.size()) {
            if (i % (mapSize * 2) == 0) {
                if (i != 0) wallsArrayObject.add(wallsRow);
                j++;
            }

            String auxByte = mapa.get(i).toString() + mapa.get(i + 1).toString();
            System.out.print(auxByte + " ");
            int finalI = Math.floorDiv(i,mapSize * 2);
            switch (auxByte) {
                case winPoint:
                    winPointObject = new JSONObject();
                    winPointObject.put("x", String.valueOf(finalI));
                    winPointObject.put("y", String.valueOf(j));
                    winPointArrayObject.add(winPointObject);
                    break;
                case box:
                    boxesObject = new JSONObject();
                    boxesObject.put("x", String.valueOf(finalI));
                    boxesObject.put("y", String.valueOf(j));
                    boxesArrayObject.add(boxesObject);
                    break;
            }
            switch (auxByte) {
                case wall:
                    wallsRow[finalI] = "1";
                    break;
                default:
                    wallsRow[finalI] = "0";
                    break;
            }
            i += 2;
        }
        System.out.println();

        jsonObject.put("winPoints", winPointArrayObject);
        jsonObject.put("boxes", boxesArrayObject);
        jsonObject.put("walls", wallsArrayObject);

        try {
            FileWriter file = new FileWriter("mapa.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
