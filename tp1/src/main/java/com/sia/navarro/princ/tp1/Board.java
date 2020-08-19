package com.sia.navarro.princ.tp1;

import jdk.nashorn.internal.ir.IfNode;

import java.util.Arrays;
import java.util.Objects;

public class Board implements Cloneable {

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private static final char wallChar = '#';
    private static final char winPointChar = '.';
    private static final char boxChar = '$';
    private static final char playerChar = '@';

    private Player player;
    private Box[] boxes;
    private Position[] winPoints;
    private int[][] walls;
    private Position size;
    private double cost;

    private double totalCost;


    public Board(Board b) {
        this(b.getPlayer(), b.getBoxes(), b.getWinPoints(), b.getWalls(), b.getSize(), b.getCost());
    }

    public Board(Player player, Box[] boxes, Position[] winPoints, int[][] walls, Position size, double cost) {
        this.player = new Player(player.getPos());
        this.boxes = new Box[boxes.length];
        for (int j = 0; j < boxes.length; j++) {
            this.boxes[j] = new Box(boxes[j].getPos());
        }
        this.winPoints = winPoints;
        this.walls = walls;
        this.size = size;
    }

    private boolean isInVictoryPoint(Position pos) {
        for (int j = 0; j < this.winPoints.length; j++) {
            if (this.winPoints[j].getX() == pos.getX() && this.winPoints[j].getY() == pos.getY()) {
                return true;
            }
        }
        return false;
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

    private boolean isThereBox(int x, int y) {
        for (Box b : boxes) {
            if (b.getPos().getY() == y && b.getPos().getX() == x) {
                return true;
            }
        }
        return false;
    }

    private boolean boxesTriangle(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (isThereBox(x+1,y) &&  isThereBox(x,y+1) && isThereBox(x+1,y+1))  {
            return true;
        }
        if (isThereBox(x-1,y) &&  isThereBox(x-1,y-1) && isThereBox(x,y-1))  {
            return true;
        }
        if (isThereBox(x-1,y) &&  isThereBox(x-1,y+1) && isThereBox(x,y+1))  {
            return true;
        }
        if (isThereBox(x,y-1) &&  isThereBox(x+1,y-1) && isThereBox(x+1,y))  {
            return true;
        }
        return false;
    }

    private boolean isBoxStucked(Position pos, Position original, int i) {
        int x = pos.getX();
        int y = pos.getY();
        if (i > 1){
            if(pos.getY() == original.getY() && pos.getX() == original.getX()){
                return true;
            }
        }

        if (isInVictoryPoint(pos)) {
            return false;
        }

        if ( (this.walls[x+1][y] == 1 &&  this.walls[x][y+1] == 1) || ( y>0 && this.walls[x+1][y] == 1 &&  this.walls[x][y-1] == 1)) {
            return true;
        }

        if (boxes.length >= 3) {
            if (boxesTriangle(pos)) {
                return true;
            }
        }

        if (x > 0) {
            if ((this.walls[x-1][y] == 1 &&  this.walls[x][y+1] == 1) || ( y>0 && this.walls[x-1][y] == 1 &&  this.walls[x][y-1] == 1)) {
                return true;
            }

            if (this.walls[x-1][y] == 1) {
                for (Box b : boxes) {
                    if (b.getPos().getX() == x && b.getPos().getY() == y + 1) {
                        return isBoxStucked(b.getPos(), original, i + 1);
                    }
                }
            }

            if (this.walls[x][y+1] == 1) {
                for (Box b : boxes) {
                    if (b.getPos().getX() == x - 1 && b.getPos().getY() == y) {
                        return isBoxStucked(b.getPos(), original, i + 1);
                    }
                }
            }

            if (y>0) {
                if (this.walls[x-1][y] == 1) {
                    for (Box b : boxes) {
                        if (b.getPos().getX() == x && b.getPos().getY() == y-1) {
                            return isBoxStucked(b.getPos(), original, i + 1);
                        }
                    }
                }
                if (this.walls[x][y-1] == 1) {
                    for (Box b : boxes) {
                        if (b.getPos().getX() == x - 1 && b.getPos().getY() == y) {
                            return isBoxStucked(b.getPos(), original, i + 1);
                        }
                    }
                }
            }
        }

        if (this.walls[x][y+1] == 1) {
            for (Box b : boxes) {
                if (b.getPos().getX() == x + 1 && b.getPos().getY() == y) {
                    return isBoxStucked(b.getPos(), original, i + 1);
                }
            }
        }

        if (this.walls[x+1][y] == 1) {
            for (Box b : boxes) {
                if (b.getPos().getX() == x && b.getPos().getY() == y+1) {
                    return isBoxStucked(b.getPos(), original, i + 1);
                }
            }
        }

        if (y>0) {
            if (this.walls[x+1][y] == 1) {
                for (Box b : boxes) {
                    if (b.getPos().getX() == x && b.getPos().getY() == y-1) {
                        return isBoxStucked(b.getPos(), original, i + 1);
                    }
                }
            }
            if (this.walls[x][y-1] == 1) {
                for (Box b : boxes) {
                    if (b.getPos().getX() == x + 1 && b.getPos().getY() == y) {
                        return isBoxStucked(b.getPos(), original, i + 1);
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isStuck() {
        for (int i = 0; i < this.boxes.length; i++) {
            if (isBoxStucked(this.boxes[i].getPos(), this.boxes[i].getPos(), 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean moveBox(int x, int y, int position) {
        Position aux;
        if (this.walls[x][y] == 1){
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

        if (this.walls[x][y] == 1){
            return false;
        }

        for (int i = 0; i < this.boxes.length; i++) {
            aux = this.boxes[i].getPos();
            if (aux.getX() == x && aux.getY() == y) {
                switch (movement) {
                    case UP:
                        hasMoved = moveBox(x, y - 1, i);
                        break;
                    case DOWN:
                        hasMoved = moveBox(x, y + 1, i);
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

        if (isThereBox(x,y)) {
            if (hasMoved) {
                this.player.setPos(new Position(x, y));
            }
        } else {
            this.player.setPos(new Position(x, y));
            hasMoved = true;
        }
        return hasMoved;
    }

    public boolean movePlayer(char movement) {
        Position playerPos = this.player.getPos();
        boolean hasMoved = false;
        switch (movement) {
            case UP:
                hasMoved = move(playerPos.getX(), playerPos.getY() - 1, UP);
                break;
            case DOWN:
                hasMoved = move(playerPos.getX(), playerPos.getY() + 1, DOWN);
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


    public void print() {
        Position aux;
        int rows = this.size.getX();
        int columns = this.size.getY();
        char[][] matrix = new char[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(walls[i][j] == 1) {
                    matrix[i][j] = wallChar;
                } else {
                    matrix[i][j] = ' ';
                }
            }
        }

        for (int i = 0; i < this.winPoints.length; i++) {
            aux = this.winPoints[i];
            matrix[aux.getX()][aux.getY()] = winPointChar;
        }
        for (int i = 0; i < this.boxes.length; i++) {
            aux = this.boxes[i].getPos();
            matrix[aux.getX()][aux.getY()] = boxChar;
        }

        matrix[this.player.getPos().getX()][this.player.getPos().getY()] = playerChar;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                System.out.print(matrix[j][i]);
            }
            System.out.print('\n');
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Box[] getBoxes() {
        return boxes;
    }

    public Position[] getWinPoints() {
        return winPoints;
    }

    public int[][] getWalls() {
        return walls;
    }

    public Position getSize() {
        return size;
    }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public double getTotalCost() { return totalCost; }

    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public Board cloneBoard() {
        Board clone = new Board(this.player, this.boxes, this.winPoints, this.walls, this.size, this.cost);
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        if (player.getPos().getX() == board.player.getPos().getX() && player.getPos().getY() == board.player.getPos().getY()){
            for (Box b : board.boxes) {
                if(!isThereBox(b.getPos().getX(),b.getPos().getY())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        String hash = new String();
        hash.concat(this.player.getPos().toString());
        for (Box b : this.boxes) {
            hash.concat(b.getPos().toString());
        }
        return hash.hashCode();
    }
}
