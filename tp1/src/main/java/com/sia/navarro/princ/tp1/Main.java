package com.sia.navarro.princ.tp1;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    private final static String DFS = "DFS";
    private final static String BFS = "BFS";
    private final static String IDDFS = "IDDFS";
    private final static String A_STAR = "A*";
    private final static String IDA_STAR = "IDA*";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        String fileName = "map/config.json";
        int mapNumber;
        String algorithm;

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader(fileName));
            algorithm = (String) data.get("algorithm");
            mapNumber = Integer.parseInt((String) data.get("map"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error at config file");
            return;
        }

        fileName = "map/mapa" + mapNumber + ".json";

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray boxesJSON = (JSONArray) data.get("boxes");
            JSONArray winPointsJSON = (JSONArray) data.get("winPoints");
            JSONArray wallsJSON = (JSONArray) data.get("walls");
            JSONObject playerJSON = (JSONObject) data.get("player");

            int width = Integer.parseInt((String) data.get("width"));
            int height = Integer.parseInt((String) data.get("height"));
            int limit = Integer.parseInt((String) data.get("limit"));
            int depth = Integer.parseInt((String) data.get("depth"));

            if (boxesJSON.size() != winPointsJSON.size()) {
                System.out.println("There are different amount of boxes than winpoints");
            } else {
                Box[] boxes = new Box[boxesJSON.size()];
                Iterator<JSONObject> iterator = boxesJSON.iterator();
                int auxId = 0;
                JSONObject aux = null;
                while (iterator.hasNext()) {
                    aux = iterator.next();
                    boxes[auxId] = new Box(new Position(Integer.parseInt((String) aux.get("x")),Integer.parseInt((String) aux.get("y"))));
                    auxId++;
                }
                iterator = winPointsJSON.iterator();
                auxId = 0;
                Position[] winPoints = new Position[winPointsJSON.size()];
                while (iterator.hasNext()) {
                    aux = iterator.next();
                    winPoints[auxId] = new Position(Integer.parseInt((String) aux.get("x")), Integer.parseInt((String) aux.get("y")));
                    auxId++;
                }
                int[][] walls = new int[width][height];
                Iterator<JSONArray> iteratorArray = boxesJSON.iterator();
                iteratorArray = wallsJSON.iterator();
                auxId = 0;
                JSONArray auxArray;
                while (iteratorArray.hasNext()) {
                    auxArray = iteratorArray.next();
                    for (int i = 0; i < auxArray.size(); i++) {
                        walls[auxId][i] = Integer.parseInt((String) auxArray.get(i));
                    }
                    auxId++;
                }

                Board board = new Board(new Player(new Position(Integer.parseInt((String) playerJSON.get("x")) ,Integer.parseInt((String) playerJSON.get("y")))), boxes, winPoints, walls, new Position(width, height));

                Algorithms alg = new Algorithms();

                System.out.println("Algorithm: " + algorithm);
                System.out.print('\n');
                System.out.println("Heuristic: " + algorithm);
                System.out.print('\n');
                System.out.println("Initial Map: ");
                System.out.print('\n');
                board.print();
                System.out.print('\n');
                long startTime = System.nanoTime();

                if (DFS.equals(algorithm))
                    alg.dfs(board.cloneBoard());
                else if (BFS.equals(algorithm))
                    alg.bfs(board.cloneBoard());
                else if (IDDFS.equals(algorithm))
                    alg.iddfs(board.cloneBoard(), depth);
                else if (A_STAR.equals(algorithm))
                    alg.aStar(board.cloneBoard(), null);
                else if (IDA_STAR.equals(algorithm))
                    alg.idaStar(board.cloneBoard(), null, limit);

                long stopTime = System.nanoTime();
                double elapsedTimeInSecond = (double) (stopTime - startTime) / 1000000000;
                System.out.println("Execution Time: " + elapsedTimeInSecond + " Seconds");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error at map");
            return;
        }
    }
}
