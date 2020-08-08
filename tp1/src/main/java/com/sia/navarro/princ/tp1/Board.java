package com.sia.navarro.princ.tp1;

public class Board {

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private Player player;
    private Box[] boxes;
    private Position[] winPoints;
    private int[][] walls;

    public Board(Player player, Box[] boxes, Position[] winPoints, int[][] walls) {
        this.player = player;
        this.boxes = boxes;
        this.winPoints = winPoints;
        this.walls = walls;
    }

    public boolean hasWon() {
        Position aux = null;
        boolean boxPositioned = false;
        for (int i = 0; i < this.boxes.length; i++) {
            boxPositioned = false;
            aux = this.boxes[i].getPos();
            for (int j = 0; j < this.winPoints.length; j++) {
                if (this.winPoints[j].getX() == aux.getX() && this.winPoints[j].getY() == aux.getY()) {
                    boxPositioned = true;
                    break;
                }
            }
            if (!boxPositioned)
                return false;
        }
        return boxPositioned;
    }

    public boolean moveBox(int x, int y, int position) {
        Position aux;
        if (this.walls[y][x] == 1){
            return false;
        }
        for (int i = 0; i < this.boxes.length; i++) {
            aux = this.boxes[i].getPos();
            if (aux.getX() == x && aux.getY() == y) {
                return false;
            }
        }
        this.boxes[position].setPos(new Position(x, y));
        return true;
    }

    private boolean move(int x, int y, char movement) {
        Position aux;

        boolean hasMoved = false;

        if (this.walls[y][x] == 1){
            return false;
        }
        for (int i = 0; i < this.boxes.length; i++) {
            aux = this.boxes[i].getPos();
            if (aux.getX() == x && aux.getY() == y) {
                switch (movement) {
                    case UP:
                        hasMoved = moveBox(x, y + 1, i);
                        break;
                    case DOWN:
                        hasMoved = moveBox(x, y - 1, i);
                        break;
                    case LEFT:
                        hasMoved = moveBox(x - 1, y, i);
                        break;
                    case RIGHT:
                        hasMoved = moveBox(x + 1, y, i);
                        break;
                }
            }
        }
        if (hasMoved) {
            this.player.setPos(new Position(x, y));
        }
        return hasMoved;
    }

    public boolean movePlayer(char movement) {
        Position playerPos = this.player.getPos();
        boolean hasMoved = false;
        switch (movement) {
            case UP:
                hasMoved = move(playerPos.getX(), playerPos.getY() + 1, UP);
                break;
            case DOWN:
                hasMoved = move(playerPos.getX(), playerPos.getY() - 1, DOWN);
                break;
            case LEFT:
                hasMoved = move(playerPos.getX() - 1, playerPos.getY(), LEFT);
                break;
            case RIGHT:
                hasMoved = move(playerPos.getX() + 1, playerPos.getY(), RIGHT);
                break;
        }
        return hasMoved;
    }
}
