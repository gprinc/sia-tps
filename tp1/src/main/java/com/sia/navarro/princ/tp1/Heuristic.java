package com.sia.navarro.princ.tp1;

public class  Heuristic {
    private final static String PB = "pb";
    private final static String BG = "bg";
    private String type;

    public Heuristic(String type){
        this.type = type;
    }

    public double getValue(Node node){
        if (PB.equals(type)){
            return this.playerToBoxes(node);
        } else if (BG.equals(type)){
            return this.boxesToGoals(node);
        }
        return this.combination(node);
    }

    private double combination(Node node) {
        if (node.isStuck()){
            return 1000000000;
        } else {
            return 0;
        }
        // return Math.max(this.playerToBoxes(node), this.boxesToGoals(node));
    }

    private int manhattan(Position pos1, Position pos2){
        return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
    }

    private int playerToBoxes(Node node){
        Board board = node.getBoard();
        if(board.isStuck())
            return 1000000000;
        Box[] boxes = board.getBoxes();
        Player player = board.getPlayer();
        int h = 100000000;
        int aux;
        for (Box b: boxes) {
            aux = this.manhattan(b.getPos(),player.getPos());
            if (h > aux)
                h = aux;
        }
        return h;
    }

    private int boxesToGoals(Node node){
        Board board = node.getBoard();
        if(board.isStuck())
            return 1000000000;
        Box[] boxes = board.getBoxes();
        Position[] goals = board.getWinPoints();
        int haux = 1000000000;
        int i;
        int h = 0;
        for (Box b: boxes) {
            for (Position g : goals) {
                i = manhattan(b.getPos(),g);
                if (i < haux){
                    haux = i;
                }
            }
            h = h + haux;
        }
        return h / boxes.length;
    }
}
