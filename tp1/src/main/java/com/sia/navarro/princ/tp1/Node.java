package com.sia.navarro.princ.tp1;

import java.util.LinkedList;

public class Node{
    private LinkedList<Board> boards;


    public Node(LinkedList<Board> boards) {
        this.boards = boards;
    }

    public void printBoards() {
        for (Board board : this.boards) {
            board.print();
            System.out.print('\n');
        }
        System.out.println("Gane");
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
            System.out.println("UP");
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.DOWN)) {
            System.out.println("DOWN");
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
            auxBoard = lastBoard.cloneBoard();
        }

        if (auxBoard.movePlayer(Board.LEFT)) {
            System.out.println("LEFT");
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
            auxBoard = lastBoard.cloneBoard();
        }
        if (auxBoard.movePlayer(Board.RIGHT)) {
            System.out.println("RIGHT");
            auxBoards.add(new Board(auxBoard));
            aux.add(new Node(auxBoards));
        }
        return aux;
    }

    public int getDepth() {
        return this.boards.size() - 1;
    }

    /* System.out.print('\n');
            for (Board b: auxBoards) {
        b.print();
        System.out.print('\n');
    }
    System.out.println("Saliiiiii 222222"); */
}
