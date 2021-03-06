package BoardGame;

public class Player {
    private Position pos;

    public Player(Position pos) {
        this.pos = new Position(pos.getX(), pos.getY());
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos.setX(pos.getX());
        this.pos.setY(pos.getY());
    }
}
