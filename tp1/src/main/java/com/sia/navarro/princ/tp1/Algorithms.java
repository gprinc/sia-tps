package com.sia.navarro.princ.tp1;

import java.util.*;

public class Algorithms {
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;
    private Heuristic heuristic;

    public Algorithms() {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new LinkedList<Node>();
    }


    public Algorithms(String heuristic) {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
        this.heuristic = new Heuristic(heuristic);
    }

    private void printSolution(Node aux, HashSet<Board> expanded, int frontier) {
        System.out.println("Solution Found:");
        System.out.print('\n');
        aux.printBoards();
        System.out.println("Depth: " + aux.getDepth());
        System.out.print('\n');
        System.out.println("Cost: " + aux.getTotalCost());
        System.out.print('\n');
        System.out.println("Expanded Nodes: " + expanded.size());
        System.out.print('\n');
        System.out.println("Border Nodes: " + frontier);
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
        int frontier = 0;
        Node aux;
        while (!dfsStack.empty() && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated, frontier + dfsStack.size());
                return;
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                }
                for(Node n: auxList){
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
        int frontier = 0;
        Node aux;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated, frontier + bfsQueue.size());
                return;
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                }
                for(Node n: auxList) {
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
        int frontier = 0;
        Node aux;

        while (dfsStack.size() != 0 && !hasWon) {
            aux = dfsStack.pop();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated,frontier + dfsStack.size());
                return;
            } else if (aux.getDepth() <= depth) {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                }
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

    public void aStar(Board board, Heuristic heuristic, double limit) {
        int steps = 0;
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard, 0);
        double h = heuristic.getValue(init);
        if(h >= 1000000000 || (limit != 0 && h >= limit))
            return;

        init.setCost(steps + h);
        Queue<Node> nextNodes = new PriorityQueue<Node>();
        nextNodes.add(init);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        int frontier = 0;

        while (!nextNodes.isEmpty()) {
            Node currentNode = nextNodes.poll();
            steps++;
            if (currentNode.getBoard().hasWon()) {
                printSolution(currentNode, repeated,frontier + nextNodes.size());
                return;
            }
            repeated.add(currentNode.getBoard());
            LinkedList<Node> auxList = currentNode.getNextNodes();
            if (auxList.size() == 0) {
                frontier++;
            }
            for(Node childNode : auxList){
                h = heuristic.getValue(childNode);
                childNode.setCost(steps + h);
                if(!repeated.contains(childNode) && !nextNodes.contains(childNode)){
                    nextNodes.add(childNode);
                } else if(nextNodes.contains(childNode)) {
                    for (Node n : nextNodes) {
                        if (childNode.equals(n)) {
                            if (childNode.getCost() < n.getCost()) {
                                nextNodes.remove(n);
                                nextNodes.add(childNode);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void gg(Board board, Heuristic heuristic) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        double h = heuristic.getValue(init);
        if(h >= 1000000000) {
            return;
        }

        init.setCost(h);
        Queue<Node> nextNodes = new PriorityQueue<Node>();
        nextNodes.add(init);
        HashSet<Board> repeated = new HashSet<Board>();
        repeated.add(board.cloneBoard());
        int frontier = 0;

        while (!nextNodes.isEmpty()) {
            Node currentNode = nextNodes.poll();
            if (currentNode.getBoard().hasWon()) {
                printSolution(currentNode, repeated,frontier + nextNodes.size());
                return;
            }
            repeated.add(currentNode.getBoard());
            LinkedList<Node> auxList = currentNode.getNextNodes();
            if (auxList.size() == 0) {
                frontier++;
            }
            for(Node childNode : auxList){
                h = heuristic.getValue(childNode);
                childNode.setCost(h);
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
