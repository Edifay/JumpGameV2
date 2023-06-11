package main.game.graphics.entities;

import main.game.calculus.Point;
import main.game.calculus.RectangleCorner;
import main.game.logic.Collisions;

import java.awt.*;

import static main.game.graphics.Utils.drawLine;


public class OrientedRectangle extends DrawComponent {

    public Point point;
    public float angle;
    public float length;
    public float thickness;

    public boolean A = true;
    public boolean B = true;
    public boolean C = true;
    public boolean D = true;

    public OrientedRectangle(final Point point, final float angle, final float length, final float thickness) {
        this.point = point;
        this.angle = angle;
        this.length = length;
        this.thickness = thickness;
    }

    public RectangleCorner projectedRectangle() {
        RectangleCorner rect = new RectangleCorner();

        RectangleCorner def = defaultRectangle();

        rect.A = Collisions.rotate(def.A, point, -angle);
        rect.B = Collisions.rotate(def.B, point, -angle);
        rect.C = Collisions.rotate(def.C, point, -angle);
        rect.D = Collisions.rotate(def.D, point, -angle);

        return rect;
    }

    public RectangleCorner defaultRectangle() {
        RectangleCorner rect = new RectangleCorner();

        rect.A = new Point(point.x, point.y - thickness);
        rect.B = new Point(point.x + length, point.y - thickness);
        rect.C = new Point(point.x + length, point.y + thickness);
        rect.D = new Point(point.x, point.y + thickness);

        return rect;
    }

    public Point projectedCenter() {
        return new Point(point.x + (Math.cos(angle) * length) / 2, point.y - (Math.sin(angle) * length) / 2);
    }

    public Point defaultCenter() {
        return new Point(point.x + length / 2, point.y);
    }

    @Override
    public void draw(Graphics2D g) {
        RectangleCorner rect = this.projectedRectangle();
        if (A)
            drawLine(g, rect.A, rect.B);
        if (B)
            drawLine(g, rect.B, rect.C);
        if (C)
            drawLine(g, rect.C, rect.D);
        if (D)
            drawLine(g, rect.D, rect.A);
    }
}
