package com.sia.navarro.princ.tp1;

import java.util.Arrays;

public class  Heuristic {
    private final static String MANHATTAN = "manhattan";
    private final static String EUCLIDEAN = "euclidean";
    private String type;

    public Heuristic(String type){
        this.type = type;
    }

    public double getValue(Node node){
        /*
        if (MANHATTAN.equals(type)){
            return this.manhattan(node);
        } else if (EUCLIDEAN.equals(type)){
            return this.euclidean(node);
        }
        */
        return this.combination(node);
    }

    private double combination(Node node) {
        if (node.isStuck()){
            return 1000000000;
        } else {
            return 0;
        }
        // return Math.max(this.manhattan(node), this.euclidean(node));
    }

    // SUMA DE LOS CATETOS
    private double manhattan(Node node) {
        Board board = node.getBoard();
        if(board.isStuck())
            return 1000000000;
        Box[] boxes = board.getBoxes();
        Position[] goals = board.getWinPoints();
        double h = 0;
        int[] array = new int[goals.length];
        int i;
        for (Box b: boxes) {
            i = 0;
            for (Position g: goals) {
                array[i] = Math.abs(b.getPos().getX() - g.getX()) + Math.abs(b.getPos().getY() - g.getY());
                i++;
            }
            Arrays.sort(array);
            h = h + array[0];
        }
        return h / boxes.length;
    }

    // DIAGONAL
    private double euclidean(Node node) {
        Board board = node.getBoard();
        Box[] boxes = board.getBoxes();
        Position[] goals = board.getWinPoints();
        double h = 0;
        double[] array = new double[goals.length];
        int i;
        for (Box b: boxes) {
            i = 0;
            for (Position g: goals) {
                array[i] = Math.sqrt(Math.pow(b.getPos().getX() - g.getX(), 2) + Math.pow(b.getPos().getY() - g.getY(), 2));
                i++;
            }
            Arrays.sort(array);
            h = h + array[0];
        }
        return h / boxes.length;
    }
}
