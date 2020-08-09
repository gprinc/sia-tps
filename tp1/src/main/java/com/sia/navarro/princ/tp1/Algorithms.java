package com.sia.navarro.princ.tp1;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Algorithms {
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;

    public Algorithms() {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
    }

    public void dfs(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(new Board(board));
        Node init = new Node(firstBoard);
        boolean hasWon = false;
        dfsStack.push(init);

        Node aux;
        while (!dfsStack.empty() && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
                return;
            } else if (!aux.isStuck()) {
                for(Node n: aux.getNextNodes())
                    dfsStack.push(n);
            }
        }
    }

    public void bfs(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(new Board(board));
        Node init = new Node(firstBoard);
        boolean hasWon = false;
        bfsQueue.add(init);

        Node aux;
        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
            } else if (!aux.isStuck()) {
                for(Node n: aux.getNextNodes())
                    bfsQueue.add(n);
            }
        }
    }
}
