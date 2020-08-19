package com.sia.navarro.princ.tp1;

import java.util.*;

public class Algorithms {
    private Stack<Node> dfsStack;
    private Queue<Node> bfsQueue;

    public Algorithms() {
        this.dfsStack = new Stack<Node>();
        this.bfsQueue = new PriorityQueue<Node>();
    }

    private void printSolution(Node aux, int expanded, int frontier) {
        System.out.println("Solution Found:");
        System.out.print('\n');
        aux.printBoards();
        System.out.println("Depth: " + aux.getDepth());
        System.out.print('\n');
        System.out.println("Cost: " + aux.getPathCost());
        System.out.print('\n');
        System.out.println("Expanded Nodes: " + expanded);
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
                printSolution(aux, repeated.size(), frontier + dfsStack.size());
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
        bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o1.getDepth() - o2.getDepth();
            }
        });
        bfsQueue.add(init);
        int frontier = 0;
        Node aux;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated.size(), frontier + bfsQueue.size());
                return;
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                } else {
                    for(Node n: auxList) {
                        if(!repeated.contains(n.getBoard())) {
                            repeated.add(n.getBoard());
                            bfsQueue.add(n);
                        }
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
                printSolution(aux, repeated.size(),frontier + dfsStack.size());
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

    public void aStar(Board board, Heuristic heuristic) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        init.setTotalCost(0);
        HashSet<Node> repeated = new HashSet<Node>();
        repeated.add(new Node(firstBoard));
        boolean hasWon = false;
        bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return (int) o1.getTotalCost() - (int) o2.getTotalCost();
            }
        });
        bfsQueue.add(init);
        int frontier = 0;
        Node aux;
        int h;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated.size(), frontier + bfsQueue.size());
                return;
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                } else {
                    for(Node n: auxList) {
                        h = (int) heuristic.getValue(n);
                        if (h < 1000000000) {
                            n.setTotalCost(n.getPathCost() + h);
                            if(!repeated.contains(n)) {
                                repeated.add(new Node(n));
                                bfsQueue.add(new Node(n));
                            }
                        }
                    }
                }
            }
        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }

    public void gg(Board board, Heuristic heuristic) {
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        init.setTotalCost(0);
        HashSet<Node> repeated = new HashSet<Node>();
        repeated.add(new Node(firstBoard));
        boolean hasWon = false;
        bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return (int) o1.getTotalCost() - (int) o2.getTotalCost();
            }
        });
        bfsQueue.add(init);
        int frontier = 0;
        Node aux;
        int h;

        while (bfsQueue.size() != 0 && !hasWon) {
            aux = bfsQueue.poll();
            hasWon = aux.hasWon();
            if (hasWon) {
                printSolution(aux, repeated.size(), frontier + bfsQueue.size());
                return;
            } else {
                LinkedList<Node> auxList = aux.getNextNodes();
                if (auxList.size() == 0) {
                    frontier++;
                } else {
                    for(Node n: auxList) {
                        h = (int) heuristic.getValue(n);
                        if (h < 1000000000) {
                            n.setTotalCost(h);
                            if(!repeated.contains(n)) {
                                repeated.add(n);
                                bfsQueue.add(n);
                            }
                        }
                    }
                }
            }
        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }

    public void idaStar(Board board, Heuristic heuristic, int limit, int iterations) {
        int currentIteration = 0;
        LinkedList<Board> firstBoard = new LinkedList<Board>();
        firstBoard.add(board.cloneBoard());
        Node init = new Node(firstBoard);
        int h = (int) heuristic.getValue(init);
        boolean hasWon = false;

        init.setTotalCost(init.getPathCost() + h);

        HashSet<Board> repeated = new HashSet<Board>();
        int currentLimit = limit;
        int auxLimit = limit;

        while(currentIteration < iterations) {
            currentIteration++;
            repeated.add(board.cloneBoard());

            bfsQueue = new PriorityQueue<Node>(11, new Comparator<Node>() {
                public int compare(Node o1, Node o2) {
                    return (int) o1.getTotalCost() - (int) o2.getTotalCost();
                }
            });
            bfsQueue.add(init);
            int frontier = 0;
            Node aux;

            while (bfsQueue.size() != 0 && !hasWon) {
                aux = bfsQueue.poll();
                hasWon = aux.hasWon();
                if (hasWon) {
                    printSolution(aux, repeated.size(), frontier + bfsQueue.size());
                    return;
                } else {
                    LinkedList<Node> auxList = aux.getNextNodes();
                    if (auxList.size() == 0) {
                        frontier++;
                    } else {
                        for(Node n: auxList) {
                            h = (int) heuristic.getValue(n);
                            if (h < 1000000000 && h < currentLimit) {
                                n.setTotalCost(n.getPathCost() + h);
                                if(!repeated.contains(n.getBoard())) {
                                    repeated.add(n.getBoard());
                                    bfsQueue.add(new Node(n));
                                }
                            } else if (h < 1000000000 && auxLimit < (int) (n.getPathCost() + h)) {
                                auxLimit = (int) (n.getPathCost() + h);
                            }
                        }
                    }
                }
            }

            repeated.clear();
            currentLimit = auxLimit;

        }

        if (!hasWon) {
            System.out.println("No solution found");
        }
    }


}
