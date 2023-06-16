package main.game.gameplay;

import main.game.Game;
import main.game.calculus.Point;
import main.game.graphics.GameFrame;
import main.game.graphics.entities.OrientedRectangle;

public class Map1 extends Map {
    public static OrientedRectangle rect1;

    @Override
    public void setupMap() {
        rect1 = new OrientedRectangle(new Point(150f, 300f), (float) -(Math.PI / 4), 1300, 15f);
        OrientedRectangle rect2 = new OrientedRectangle(new Point(0f, 300f), (float) 0, 150f, 15f);
        OrientedRectangle rect3 = new OrientedRectangle(new Point(0, GameFrame.render.getHeight()), (float) (Math.PI / 2), GameFrame.render.getHeight(), 2f);
        OrientedRectangle rect4 = new OrientedRectangle(new Point(GameFrame.render.getWidth(), GameFrame.render.getHeight()), (float) (Math.PI / 2), GameFrame.render.getHeight(), 2f);
        OrientedRectangle rect5 = new OrientedRectangle(new Point(0, GameFrame.render.getHeight()), 0, GameFrame.render.getWidth(), 30f);
        OrientedRectangle rect6 = new OrientedRectangle(new Point(600f, 250f), 0, 200f, 15f);
        OrientedRectangle rect7 = new OrientedRectangle(new Point(900f, 250f), 0, 200f, 15f);
        OrientedRectangle rect8 = new OrientedRectangle(new Point(430f, 580f), 0, 600f, 15f);
        OrientedRectangle rect9 = new OrientedRectangle(new Point(800f, 500f), (float) (Math.PI / 8), 450f, 15f);

        Game.components.add(rect1);
        Game.components.add(rect2);
        Game.components.add(rect3);
        Game.components.add(rect4);
        Game.components.add(rect5);
        Game.components.add(rect6);
        Game.components.add(rect7);
        Game.components.add(rect8);
        Game.components.add(rect9);
    }
}
