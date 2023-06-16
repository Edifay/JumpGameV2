package main.game.graphics.entities;

import main.game.calculus.Point;
import main.game.calculus.RectangleCorner;
import main.game.graphics.DrawComponent;
import main.game.logic.Collisions;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;


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
        Shape shape = new RoundRectangle2D.Float(point.x, point.y - thickness, length, thickness * 2, 10, 10);
        drawRotatedShape(g, shape, -angle, point.x, point.y);
    }

    public static void drawRotatedShape(final Graphics2D g2, final Shape shape, final double angle, final float x, final float y) {
        final AffineTransform saved = g2.getTransform();
        final AffineTransform rotate = AffineTransform.getRotateInstance(
                angle, x, y);
        g2.transform(rotate);
        g2.fill(shape);
        g2.setTransform(saved);
    }
}
