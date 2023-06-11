package main.game.graphics;


import main.game.calculus.Point;

import java.awt.*;

public class Utils {

    public static void drawLine(Graphics2D g, Point a, Point b) {
        g.drawLine(Math.round(a.x), Math.round(a.y), Math.round(b.x), Math.round(b.y));
    }
}
