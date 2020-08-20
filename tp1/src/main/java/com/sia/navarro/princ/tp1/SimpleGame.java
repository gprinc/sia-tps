package com.sia.navarro.princ.tp1;

public class SimpleGame implements Cloneable {

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private Player player;
    private Position winPoints;
    private int[][] walls;
    private Position size;
    private double cost;

    public SimpleGame(SimpleGame b) {
        this(b.getPlayer(), b.getWinPoints(), b.getWalls(), b.getSize(), b.getCost());
    }
    public SimpleGame(Player player, Position winPoints, int[][] walls, Position size, double cost) {
        this.player = new Player(player.getPos());
        this.winPoints = winPoints;
        this.walls = walls;
        this.size = size;
        this.cost = 0;
    }

    public boolean isInVictoryPoint() {
        if (player.getPos().getX() == winPoints.getX() && player.getPos().getY() == winPoints.getY()) {
            return true;
        }
        return false;
    }

    public boolean isStucked() {
        int x = player.getPos().getX();
        int y = player.getPos().getY();
        if (isInVictoryPoint()) {
            return false;
        }
        if ( (this.walls[x+1][y] == 1 &&  this.walls[x][y+1] == 1) || ( y>0 && this.walls[x+1][y] == 1 &&  this.walls[x][y-1] == 1)) {
            return true;
        }
        if (x > 0) {
            if ((this.walls[x-1][y] == 1 &&  this.walls[x][y+1] == 1) || ( y>0 && this.walls[x-1][y] == 1 &&  this.walls[x][y-1] == 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean move(int x, int y) {
        if (this.walls[x][y] == 1){
            return false;
        }
        this.player.setPos(new Position(x, y));
        return true;
    }

    public boolean movePlayer(char movement) {
        Position playerPos = this.player.getPos();
        boolean hasMoved = false;
        switch (movement) {
            case UP:
                hasMoved = move(playerPos.getX(), playerPos.getY() - 1);
                break;
            case DOWN:
                hasMoved = move(playerPos.getX(), playerPos.getY() + 1);
                break;
            case LEFT:
                hasMoved = move(playerPos.getX() - 1, playerPos.getY());
                break;
            case RIGHT:
                hasMoved = move(playerPos.getX() + 1, playerPos.getY());
                break;
        }
        return hasMoved;
    }

    public Player getPlayer() {
        return player;
    }


    public int[][] getWalls() {
        return walls;
    }

    public Position getSize() {
        return size;
    }

    public Position getWinPoints() { return winPoints; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public SimpleGame cloneBoard() {
        SimpleGame clone = new SimpleGame(this.player, this.winPoints, this.walls, this.size, this.cost);
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleGame board = (SimpleGame) o;
        if (player.getPos().getX() == board.player.getPos().getX() && player.getPos().getY() == board.player.getPos().getY()){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        String hash = new String();
        hash.concat(this.player.getPos().toString());
        return hash.hashCode();
    }
}
