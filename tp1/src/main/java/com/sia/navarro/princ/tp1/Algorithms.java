package com.sia.navarro.princ.tp1;

import java.util.*;

public class Algorithms {
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;

    public Algorithms() {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
    }

    private void printSolution(Node aux, HashSet<Board> expanded) {
        System.out.println("Solution Found:");
        System.out.print('\n');
        aux.printBoards();
        System.out.println("Depth: " + aux.getDepth());
        System.out.print('\n');
        System.out.println("Cost: " + aux.getCost());
        System.out.print('\n');
        System.out.println("Expanded Nodes: " + expanded.size());
        System.out.print('\n');
        System.out.println("Border Nodes: " + "ayuda no se como hacerlo");
        System.out.print('\n');
        return;
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
                printSolution(aux, repeated);
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
                printSolution(aux, repeated);
                return;
            } else {
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
                printSolution(aux, repeated);
                return;
            } else if (aux.getDepth() <= depth) {
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
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard, 0);
        double h = heuristic.getValue(init);
        if(h >= 1000000000) {
            return;
        }

        init.setCost(init.getCost() + h);
        Queue<Node> nextNodes = new PriorityQueue<Node>();
        nextNodes.add(init);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());

        while (!nextNodes.isEmpty()) {
            Node currentNode = nextNodes.poll();
            if (currentNode.getBoard().hasWon()) {
                currentNode.printBoards();
            }
            repeated.add(currentNode.getBoard());
            for(Node childNode : currentNode.getNextNodes()){
                h = heuristic.getValue(childNode);
                childNode.setCost(childNode.getCost() + h);
                if(!repeated.contains(childNode) && !nextNodes.contains(childNode)){
                    nextNodes.add(childNode);
                } else if(nextNodes.contains(childNode)){
                    for(Node n : nextNodes) {
                        if(childNode.equals(n)) {
                            if(childNode.getCost() < n.getCost()){
                                nextNodes.remove(n);
                                nextNodes.add(childNode);
                                break;
                            }
                        }
                    }
                } else if(repeated.contains(childNode.getBoard())) {
                    // Me falta ver cuando esta el board en repetidos pero hay menor costo
                }
            }
        }
    }

    public void idaStar(Board board, Heuristic heuristic, int limit) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard, 0);
        double h = heuristic.getValue(init);
        if(h >= 1000000000) {
            return;
        }

        init.setCost(init.getCost() + h);
        Queue<Node> nextNodes = new PriorityQueue<Node>();
        nextNodes.add(init);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());

        while (!nextNodes.isEmpty()) {
            Node currentNode = nextNodes.poll();
            if (currentNode.getBoard().hasWon()) {
                currentNode.printBoards();
            }
            repeated.add(currentNode.getBoard());
            for(Node childNode : currentNode.getNextNodes()){
                h = heuristic.getValue(childNode);
                childNode.setCost(childNode.getCost() + h);
                if(!repeated.contains(childNode) && !nextNodes.contains(childNode)){
                    nextNodes.add(childNode);
                } else if(nextNodes.contains(childNode)){
                    for(Node n : nextNodes) {
                        if(childNode.equals(n)) {
                            if(childNode.getCost() < n.getCost()){
                                nextNodes.remove(n);
                                nextNodes.add(childNode);
                                break;
                            }
                        }
                    }
                } else if(repeated.contains(childNode.getBoard())) {
                    // Me falta ver cuando esta el board en repetidos pero hay menor costo
                }
            }
        }
    }
}
