package BoardGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

public class BoardMain {
    Board board;

    public BoardMain(String mapa) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("maps/mapa" + mapa + ".json"));
            JSONArray boxesJSON = (JSONArray) data.get("boxes");
            JSONArray winPointsJSON = (JSONArray) data.get("winPoints");
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

                this.board = new Board(new Player(new Position(Integer.parseInt((String) playerJSON.get("x")) ,Integer.parseInt((String) playerJSON.get("y")))), boxes, winPoints, walls, new Position(width, height), 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error at map");
            return;
        }
    }

    public Board getBoard() { return this.board; }
}
