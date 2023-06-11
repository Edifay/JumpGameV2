package main.game.logic;

import main.game.calculus.Point;

public class Vector {

    public float x;
    public float y;

    public float max_x;
    public float max_y;


    public Vector(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(final float x, final float y, final float max_x, final float max_y) {
        this.x = x;
        this.y = y;
        this.max_x = max_x;
        this.max_y = max_y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector of(final double percentage) {
        return new Vector((float) (percentage * x), (float) (percentage * y));
    }

    public Vector opposite() {
        return new Vector(-x, -y);
    }

    public Vector y() {
        return new Vector(0, y);
    }

    public float angleWith(final Vector vect) {
        return (float) Math.atan2(y * vect.x - x * vect.y, x * vect.x + y * vect.y);
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public Vector rotate(float angle) {
        return Collisions.rotate(this.toPoint(), new Point(), angle).toVector();
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
