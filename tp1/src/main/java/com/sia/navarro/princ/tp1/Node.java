package com.sia.navarro.princ.tp1;

import java.util.LinkedList;

public class Node implements Comparable<Node> {
    private LinkedList<Board> boards;
    private double cost;
    private double pathCost;
    private double totalCost;


    public Node(LinkedList<Board> boards) {
        this.boards = boards;
        Board aux = boards.getLast();
        if (aux != null)
            this.cost = boards.getLast().getCost();
        else
            this.cost = 0;
    }

    public Node(LinkedList<Board> boards, double cost) {
        this.boards = boards;
        this.cost = cost;
        this.pathCost = cost;
        this.totalCost = 0;
    }

    public void printBoards() {
        for (Board board : this.boards) {
            board.print();
            System.out.print('\n');
        }
    }

    public boolean hasWon() {
        return this.boards.getLast().hasWon();
    }
    public boolean isStuck() {
        return this.boards.getLast().isStuck();
    }
    public Board getBoard() {return this.boards.getLast();}

    public LinkedList<Node> getNextNodes() {
        LinkedList<Node> aux = new LinkedList<Node>();
        LinkedList<Board> auxBoards = new LinkedList<Board>(this.boards);
        Board lastBoard = this.boards.getLast().cloneBoard();
        Board auxBoard = lastBoard.cloneBoard();

        if (auxBoard.movePlayer(Board.UP)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.DOWN)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }

        if (auxBoard.movePlayer(Board.LEFT)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.RIGHT)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards, this.pathCost + 1));
        }
        return aux;
    }

    public int getDepth() {
        return this.boards.size() - 1;
    }

    public LinkedList<Board> getBoards() { return boards; }

    public double getCost() { return this.cost; }

    public void setCost(double cost) {
        this.cost = cost;
        this.boards.getLast().setCost(cost);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        if(node.getBoards().size() != this.boards.size()) return false;
        boolean areEquals = false;
        for (Board b: node.getBoards()) {
            areEquals = this.boards.contains((Board) b);
            if (!areEquals)
                break;
        }
        return areEquals;
    }

    public int compareTo(Node n) {
        Double ownCost = this.totalCost;
        Double nCost = n.getTotalCost();
        return ownCost.compareTo(nCost);
    }


    public double getPathCost() { return pathCost; }

    public void setPathCost(double pathCost) { this.pathCost = pathCost; }

    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public double getTotalCost() {
        return this.totalCost;
        /* double total = 0;
        for (Board b: this.boards)
            total += b.getCost();
        return total; */
    }
}
