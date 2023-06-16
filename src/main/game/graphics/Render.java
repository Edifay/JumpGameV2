package main.game.graphics;

import main.game.Game;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    public Render() {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (DrawComponent comp : Game.components)
            comp.draw(g2d);

        Game.ball.draw(g2d);
    }
}
