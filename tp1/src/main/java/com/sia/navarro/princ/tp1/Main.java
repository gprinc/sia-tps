package com.sia.navarro.princ.tp1;

public class Main {
    public static void main(String[] args) {
        Box[] boxes = new Box[1];
        boxes[0] = new Box(new Position(2 ,1));
        Position[] winPoints = new Position[1];
        winPoints[0] = new Position(5, 1);
        int[][] walls = {{1,1,1,1,1,1,1}, {1,0,0,0,0,0,1}, {1,1,1,1,1,1,1}};
        Board board = new Board(new Player(new Position(1 ,1)), boxes, winPoints, walls);

        Algorithms alg = new Algorithms(board);
        alg.dfs();
        alg.bfs();
    }
}
