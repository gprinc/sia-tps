package com.sia.navarro.princ.tp1;

import java.util.Arrays;

public class  Heuristic {
    private final static String MANHATTAN = "manhattan";
    private final static String EUCLIDEA = "euclidea";
    private String heuristic;

    public Heuristic(String heuristic) {
        this.heuristic = heuristic;
    }

    public double getHeuristic(Board b) {
        if(MANHATTAN.equals(this.heuristic))
            return manhattan(b);
        else if(EUCLIDEA.equals(this.heuristic))
            return euclidean(b);
        return 0;
    }

    public double getValue(Node node) {
        if (node.isStuck()){
            return 1000000000;
        }else if (node.hasWon()){
            return 0;
        }
        return Math.max(this.manhattan(node.getBoard()), this.euclidean(node.getBoard()));
    }

    private double manhattan(Board board) {
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
        return h;
    }

    private double euclidean(Board board) {
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
        return h;
    }
}
