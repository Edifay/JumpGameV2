package main.game;

import main.game.calculus.Point;
import main.game.gameplay.Map1;
import main.game.graphics.GameFrame;
import main.game.graphics.entities.Ball;
import main.game.graphics.DrawComponent;
import main.game.graphics.entities.LogicStatus;
import main.game.logic.MainLogic;

import java.util.ArrayList;

public class Game {

    public static ArrayList<DrawComponent> components = new ArrayList<>();

    public static Ball ball = new Ball(new Point(100f, 50f), 10f);

    public static void initGame() {
        GameFrame.initGameFrame();

        new Map1().setupMap();

        MainLogic.initLogic();
        MainLogic.startLogic();

        Game.components.add(new LogicStatus());
    }

}
