package com.sia.navarro.princ.tp1;

import java.util.LinkedList;

public class SimpleGameNode {
    private LinkedList<SimpleGame> boards;
    private double cost;
    private double pathCost;
    private double totalCost;

    public SimpleGameNode(SimpleGameNode n) {
        this((LinkedList<SimpleGame>) n.getBoards().clone(), n.getPathCost());
        totalCost = n.getTotalCost();
    }

    public SimpleGameNode(LinkedList<SimpleGame> boards) {
        this.boards = boards;
        SimpleGame aux = boards.getLast();
        if (aux != null)
            this.cost = boards.getLast().getCost();
        else
            this.cost = 0;
    }

    public SimpleGameNode(LinkedList<SimpleGame> boards, double cost) {
        this.boards = boards;
        this.cost = cost;
        this.pathCost = cost;
        this.totalCost = 0;
    }


    public boolean hasWon() {
        return this.boards.getLast().isInVictoryPoint();
    }
    public boolean isStuck() {
        return this.boards.getLast().isStucked();
    }
    public SimpleGame getBoard() {return this.boards.getLast();}

    public LinkedList<SimpleGameNode> getNextNodes() {
        LinkedList<SimpleGameNode> aux = new LinkedList<SimpleGameNode>();
        LinkedList<SimpleGame> auxBoards = new LinkedList<SimpleGame>(this.boards);
        SimpleGame lastBoard = this.boards.getLast().cloneBoard();
        SimpleGame auxBoard = lastBoard.cloneBoard();

        if (auxBoard.movePlayer(Board.UP)) {
            auxBoards.add(new SimpleGame(auxBoard));
            aux.add(new SimpleGameNode(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<SimpleGame>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.DOWN)) {
            auxBoards.add(new SimpleGame(auxBoard));
            aux.add(new SimpleGameNode(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<SimpleGame>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }

        if (auxBoard.movePlayer(Board.LEFT)) {
            auxBoards.add(new SimpleGame(auxBoard));
            aux.add(new SimpleGameNode(auxBoards, this.pathCost + 1));
            auxBoards = new LinkedList<SimpleGame>(this.boards);
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.RIGHT)) {
            auxBoards.add(new SimpleGame(auxBoard));
            aux.add(new SimpleGameNode(auxBoards, this.pathCost + 1));
        }
        return aux;
    }

    public int getDepth() {
        return this.boards.size() - 1;
    }

    public LinkedList<SimpleGame> getBoards() { return boards; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleGameNode node = (SimpleGameNode) o;
        return this.boards.getLast().equals(node.getBoard()) && this.getPathCost() == node.getPathCost();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = (int) Math.pow(prime, this.totalCost);
        return (int) Math.pow(2, getBoard().hashCode()) * hash;
    }

    public double getPathCost() { return pathCost; }

    public double getTotalCost() {
        return this.totalCost;
    }
}
