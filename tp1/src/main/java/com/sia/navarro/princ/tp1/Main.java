package com.sia.navarro.princ.tp1;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();


        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("map/mapa1.json"));

            JSONArray boxesJSON = (JSONArray) data.get("boxes");
            JSONArray winPointsJSON = (JSONArray) data.get("winPoints");
            // TODO
            JSONArray wallsJSON = (JSONArray) data.get("walls");
            JSONObject playerJSON = (JSONObject) data.get("player");
            int width = Integer.parseInt((String) data.get("width"));
            int height = Integer.parseInt((String) data.get("height"));

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
                int[][] walls = {{1,1,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,1,1}};
                Board board = new Board(new Player(new Position(Integer.parseInt((String) playerJSON.get("x")) ,Integer.parseInt((String) playerJSON.get("y")))), boxes, winPoints, walls, new Position(width, height));
                
                Algorithms alg = new Algorithms();
                alg.dfs(board.cloneBoard());
                alg.bfs(board.cloneBoard());
                alg.iddfs(board.cloneBoard(),1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}
