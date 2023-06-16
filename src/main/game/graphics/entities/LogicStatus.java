package main.game.graphics.entities;

import main.game.graphics.DrawComponent;
import main.game.logic.MainLogic;

import java.awt.*;

public class LogicStatus extends DrawComponent {
    @Override
    public void draw(Graphics2D g) {
        if (MainLogic.isRunning())
            g.drawString("Running", 30, 30);
        else
            g.drawString("Paused", 30, 30);
    }
}
