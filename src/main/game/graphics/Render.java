package main.game.graphics;

import main.game.Game;
import main.game.graphics.entities.DrawComponent;
import main.game.logic.Collisions;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    public Render() {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (DrawComponent comp : Game.components)
            comp.draw(g2d);

        Game.ball.draw(g2d);
        g2d.setColor(Color.red);
        Collisions.line.draw(g2d);
    }
}
