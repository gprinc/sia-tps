package com.sia.navarro.princ.tp1;

import java.util.Arrays;

public class Heuristic {

    public double getValue(Board board) {
        return this.manhattan(board) + this.euclidean(board);
    }

    private int manhattan(Board board) {
        Box[] boxes = board.getBoxes();
        Position[] goals = board.getWinPoints();
        int h = 0;
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
