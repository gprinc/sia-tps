package com.sia.navarro.princ.tp1;

import java.util.LinkedList;

public class Node implements Comparable<Node> {
    private LinkedList<Board> boards;
    private int cost;


    public Node(LinkedList<Board> boards) {
        this.cost = 0;
        this.boards = boards;
    }

    public Node(LinkedList<Board> boards, int cost) {
        this.boards = boards;
        this.cost = cost;
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
            aux.add(new Node(auxBoards));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.DOWN)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }

        if (auxBoard.movePlayer(Board.LEFT)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
            auxBoards = new LinkedList<Board>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.RIGHT)) {
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
        }
        return aux;
    }

    public int getDepth() {
        return this.boards.size() - 1;
    }

    public LinkedList<Board> getBoards() { return boards; }

    public int getCost() { return this.cost; }
    public void setCost(int cost) { this.cost = cost; }


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
        Integer ownCost = this.cost;
        Integer nCost = n.getCost();
        return ownCost.compareTo(nCost);
    }
}
