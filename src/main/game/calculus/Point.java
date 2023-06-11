package main.game.calculus;

import main.game.logic.Vector;

public class Point {

    public float x;
    public float y;


    public Point() {
        this.x = 0f;
        this.y = 0f;
    }

    public Point(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Point(final double x, final double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector toVector() {
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
