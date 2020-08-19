package com.sia.navarro.princ.tp1;

import java.util.*;

public class  Heuristic {
    private final static String PB = "pb";
    private final static String BG = "bg";
    private final static String BGW = "bgw";
    private String type;

    public Heuristic(String type){
        this.type = type;
    }

    public double getValue(Node node){
        if (PB.equals(type)){
            return this.playerToBoxes(node);
        } else if (BG.equals(type)){
            return this.boxesToGoals(node);
        } else if (BGW.equals(type)){
            return this.boxesToGoalsWalls(node);
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

    private int boxesToGoalsWalls(Node node){
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
                i = movesToGoal(node, b.getPos(), g);
                if (i < haux){
                    haux = i;
                }
            }
            h = h + haux;
        }
        return h / boxes.length;
    }

    private int movesToGoal(Node node, Position box, Position goal){
        Board board = node.getBoard();
        if(board.isStuck())
            return 1000000000;
        Box[] b = new Box[1];
        b[0] = new Box(box);
        Position[] g = new Position[1];
        g[0] = goal;
        Board bb = new Board(board.getPlayer(), b, g, board.getWalls(), board.getSize(), board.getCost());
        return this.bfs(bb);
    }

    private int bfs(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        boolean hasWon = false;
        Queue<Node> bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o1.getDepth() - o2.getDepth();
            }
        });
        bfsQueue.add(init);
        Node aux;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                return aux.getDepth();
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                } else {
                    for(Node n: auxList) {
                        if(!repeated.contains(n.getBoard())) {
                            repeated.add(n.getBoard());
                            bfsQueue.add(n);
                        }
                    }
                }
            }
        }
        if (!hasWon) {
            return 1000000000;
        }
        return 0;
    }

    private int aStar(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        init.setTotalCost(0);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        boolean hasWon = false;
        Queue<Node> bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return (int) o1.getTotalCost() - (int) o2.getTotalCost();
            }
        });
        bfsQueue.add(init);
        int frontier = 0;
        Node aux;
        int h;
        Iterator<Board> it;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                return aux.getDepth();
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                } else {
                    for(Node n: auxList) {
                        h = playerToBoxes(n);
                        if (h < 1000000000) {
                            n.setTotalCost(n.getPathCost() + h);
                            if(!repeated.contains(n.getBoard())) {
                                repeated.add(n.getBoard());
                                bfsQueue.add(new Node(n));
                            } else {
                                it = repeated.iterator();
                                while (it.hasNext()) {
                                    Board auxB = it.next();
                                    if(n.getBoard().equals(auxB) && n.getTotalCost() < auxB.getCost()) {
                                        repeated.remove(auxB);
                                        repeated.add(n.getBoard());
                                        bfsQueue.add(new Node(n));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!hasWon) {
            return 1000000000;
        }
        return 0;
    }
}
