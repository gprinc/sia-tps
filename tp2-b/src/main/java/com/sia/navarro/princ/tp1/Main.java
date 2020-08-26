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
    private final static String GG = "GG";
    private final static int DEFAULT_ITERATIONS = 100;
    private final static int DEFAULT_LIMIT = 100;
    private final static int DEFAULT_DEPTH = 100;
    private final static String DEFAULT_HEURISTIC = "bg";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        String fileName;

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("config.json"));
            String mapNumber;
            int limit = DEFAULT_LIMIT;
            int depth = DEFAULT_DEPTH;
            String algorithm = BFS;
            String heuristic = DEFAULT_HEURISTIC;
            int iterations = DEFAULT_ITERATIONS;

            if (data.get("map").toString() != null)
                mapNumber = (String) data.get("map");
            else {
                System.out.println("Map must be a valid string number");
                return;
            }
            if (data.get("algorithm").toString() != null) {
                algorithm = (String) data.get("algorithm");
                if (IDDFS.equals(algorithm)) {
                    if (data.get("depth").toString() != null)
                        depth = Integer.parseInt((String) data.get("depth"));
                    else {
                        System.out.println("If you want to use IDDFS, you must set a depth value");
                        return;
                    }
                } else if(IDA_STAR.equals(algorithm) || A_STAR.equals(algorithm) || GG.equals(algorithm)) {
                    if (data.get("heuristic").toString() != null)
                        heuristic = (String) data.get("heuristic");
                    else {
                        System.out.println("Heuristics must be either pb, bg, bgw");
                        return;
                    }
                    if (IDA_STAR.equals(algorithm)) {
                        if (data.get("limit").toString() != null)
                            limit = Integer.parseInt((String) data.get("limit"));
                        if (data.get("iterations").toString() != null)
                            iterations = Integer.parseInt((String) data.get("iterations"));
                    }
                }
            } else {
                System.out.println("Algorithm must be either BFS, DFS, IDDFS, A*, IDA* or GG");
                return;
            }

            fileName = "map/mapa" + mapNumber + ".json";

            data = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray boxesJSON = (JSONArray) data.get("boxes");
            JSONArray winPointsJSON = (JSONArray) data.get("winPoints");
            JSONArray wallsJSON = (JSONArray) data.get("walls");
            JSONObject playerJSON = (JSONObject) data.get("player");

            int width = Integer.parseInt((String) data.get("width"));
            int height = Integer.parseInt((String) data.get("height"));

            if (boxesJSON.size() != winPointsJSON.size()) {
                System.out.println("There are different amount of boxes than winpoints");
            } else {
                System.out.println("Algorithm: " + algorithm);
                System.out.print('\n');
                if (!IDDFS.equals(algorithm) && !BFS.equals(algorithm) && !DFS.equals(algorithm)){
                    System.out.println("Heuristic: " + heuristic);
                    System.out.print('\n');
                }
                System.out.println("Initial Map: ");
                System.out.print('\n');
                System.out.print('\n');
                long startTime = System.nanoTime();


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
