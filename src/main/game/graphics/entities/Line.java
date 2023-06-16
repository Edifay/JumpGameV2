package main.game.graphics.entities;


import main.game.calculus.Point;
import main.game.graphics.DrawComponent;

import java.awt.*;

import static main.game.graphics.Utils.drawLine;

public class Line extends DrawComponent {

    public Point a;
    public Point b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void draw(Graphics2D g) {
        drawLine(g, a, b);
    }
}
