package com.sia.navarro.princ.tp1;

public class Main {
    public static void main(String[] args) {
        Box[] boxes = new Box[7];
        boxes[0] = new Box(new Position(2 ,3));
        boxes[1] = new Box(new Position(3 ,4));
        boxes[2] = new Box(new Position(4 ,4));
        boxes[3] = new Box(new Position(6 ,1));
        boxes[4] = new Box(new Position(6 ,3));
        boxes[5] = new Box(new Position(6 ,4));
        boxes[6] = new Box(new Position(6 ,5));
        Position[] winPoints = new Position[7];
        winPoints[0] = new Position(2, 1);
        winPoints[1] = new Position(4, 1);
        winPoints[2] = new Position(3, 5);
        winPoints[3] = new Position(5, 4);
        winPoints[4] = new Position(6, 3);
        winPoints[5] = new Position(6, 6);
        winPoints[6] = new Position(7, 4);
        int[][] walls = {{1,1,1,1,1,1,1,1,1},{1,1,0,1,0,0,0,0,1},{1,1,0,1,1,1,0,0,1},{1,0,0,0,1,0,0,0,1},{1,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,1},{1,1,1,1,1,1,0,0,1},{1,1,1,1,1,1,1,1,1}};
        Board board = new Board(new Player(new Position(2 ,2)), boxes, winPoints, walls);

        Algorithms alg = new Algorithms(board);
        alg.dfs();
        alg.bfs();
    }
}
