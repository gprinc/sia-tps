package com.sia.navarro.princ.tp1;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Node{
    private LinkedList<Board> boards;

    public Node(LinkedList<Board> boards) {
        this.boards = boards;
    }

    public void printBoards() {
        // iterar por el previousboards
        System.out.println("Gane");
    }

    public boolean hasWon() {
        return this.boards.getLast().hasWon();
    }

    public LinkedList<Node> getNextNodesDfs(Stack<Node> dfsStack) {
        LinkedList<Node> aux = new LinkedList<Node>();
        LinkedList<Board> auxBoards = new LinkedList<Board>(this.boards);
        Board auxBoard = this.boards.getLast();
        if (auxBoard.movePlayer(Board.UP)) {
            auxBoards.add(auxBoard);
            dfsStack.push(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();
        if (auxBoard.movePlayer(Board.DOWN)) {
            auxBoards.add(auxBoard);
            dfsStack.push(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();

        if (auxBoard.movePlayer(Board.LEFT)) {
            auxBoards.add(auxBoard);
            dfsStack.push(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();

        if (auxBoard.movePlayer(Board.RIGHT)) {
            auxBoards.add(auxBoard);
            dfsStack.push(new Node(auxBoards));
        }
        return aux;
    }

    public LinkedList<Node> getNextNodesBfs(Queue<Node> bfsQueue) {
        LinkedList<Node> aux = new LinkedList<Node>();
        LinkedList<Board> auxBoards = new LinkedList<Board>(this.boards);
        Board auxBoard = this.boards.getLast();
        if (auxBoard.movePlayer(Board.UP)) {
            auxBoards.add(auxBoard);
            bfsQueue.add(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();
        if (auxBoard.movePlayer(Board.DOWN)) {
            auxBoards.add(auxBoard);
            bfsQueue.add(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();

        if (auxBoard.movePlayer(Board.LEFT)) {
            auxBoards.add(auxBoard);
            bfsQueue.add(new Node(auxBoards));
        }
        auxBoards = new LinkedList<Board>(this.boards);
        auxBoard = this.boards.getLast();

        if (auxBoard.movePlayer(Board.RIGHT)) {
            auxBoards.add(auxBoard);
            bfsQueue.add(new Node(auxBoards));
        }
        return aux;
    }
}
