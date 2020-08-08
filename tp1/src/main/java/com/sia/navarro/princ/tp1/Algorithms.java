package com.sia.navarro.princ.tp1;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Algorithms {
    private Board board;
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;

    public Algorithms(Board board) {
        this.board = board;
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
    }

    public void dfs() {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(this.board);
        Node init = new Node(firstBoard);
        boolean hasWon = false;
        dfsStack.push(init);

        Node aux;
        while (!dfsStack.empty() && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
            } else {
                aux.getNextNodes(dfsStack);
            }
        }
        if (hasWon) {
            // aux.hasWon();
            return; // TODO
        }
    }
}
