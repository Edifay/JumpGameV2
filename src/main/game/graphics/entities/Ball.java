package main.game.graphics.entities;


import main.game.calculus.Point;
import main.game.logic.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends DrawComponent {

    public Point point;
    public float radius;

    public ArrayList<Vector> appliedForce = new ArrayList<>();

    public Ball(final Point point, final float radius) {
        this.point = point;
        this.radius = radius;
    }


    @Override
    public void draw(Graphics2D g) {
        g.fillOval(Math.round(this.point.x-radius), Math.round(this.point.y-radius), Math.round(2*radius), Math.round(2*radius));
    }
}
