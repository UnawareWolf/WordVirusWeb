package com.unawarewolf.wordvirus;

public class Coordinate {

    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Coordinate coordinatesToCompare) {
        return coordinatesToCompare.getX() == x && coordinatesToCompare.getY() == y;
    }
}
