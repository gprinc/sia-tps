package com.sia.navarro.princ.tp1;

public class Box {
    private Position pos;

    public Box(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos.setX(pos.getX());
        this.pos.setY(pos.getY());
    }
}
