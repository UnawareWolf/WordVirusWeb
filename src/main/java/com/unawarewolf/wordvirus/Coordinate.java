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

//    public int[] toIntArray() {
//        return new int[] {x, y};
//    }

    public boolean equals(Coordinate comparisonCo) {
        return comparisonCo.getX() == x && comparisonCo.getY() == y;
    }
}
