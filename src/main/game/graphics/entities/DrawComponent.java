package main.game.graphics.entities;

import main.game.logic.Vector;

import java.awt.*;

public abstract class DrawComponent {

    public Vector velocity = new Vector(0, 0);

    public abstract void draw(Graphics2D g);

}
