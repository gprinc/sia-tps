package com.sia.navarro.princ.tp1;

import java.util.*;

public class Algorithms {
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;

    public Algorithms() {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
    }

    public void dfs(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        boolean hasWon = false;
        dfsStack.push(init);

        Node aux;
        while (!dfsStack.empty() && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
                return;
            } else {
                for(Node n: aux.getNextNodes()){
                    if(!repeated.contains(n.getBoard())) {
                        repeated.add(n.getBoard());
                        dfsStack.push(n);
                    }
                }
            }
        }

        for (Board n : repeated) {
            n.print();
        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }

    public void bfs(Board board) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        boolean hasWon = false;
        bfsQueue.add(init);
        board.print();

        Node aux;
        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
            } else if (!aux.isStuck()) {
                for(Node n: aux.getNextNodes()) {
                    if(!repeated.contains(n.getBoard())) {
                        repeated.add(n.getBoard());
                        bfsQueue.add(n);
                    }
                }
            }
        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }

    public void iddfs(Board board, int depth) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        boolean hasWon = false;
        dfsStack.add(init);

        Node aux;
        while (dfsStack.size() != 0 && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                aux.printBoards();
            } else if (!aux.isStuck() && (aux.getDepth() <= depth)) {
                for(Node n: aux.getNextNodes()){
                    if(!repeated.contains(n.getBoard())) {
                        repeated.add(n.getBoard());
                        dfsStack.push(n);
                    }
                }
            }
        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }

    public void aStar(Board board, Heuristic heuristic) {

    }
}
