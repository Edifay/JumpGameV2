package main.game.logic;

import main.game.Game;
import main.game.graphics.entities.DrawComponent;
import main.game.graphics.entities.OrientedRectangle;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.max;
import static main.game.logic.Collisions.getCollisionAngle;

public class MainLogic {

    protected static Thread loop;

    protected static Vector gravity = new Vector(0f, 0.03f);

    public static Vector right = new Vector(0.022f, 0f);
    public static Vector left = new Vector(-0.022f, 0f);

    protected final static long normalTickDuration = 2000000L;

    protected final static int pxHideBoxCheck = 1;

    private static final Vector y_vector = new Vector(0, 1);

    public static Queue<Runnable> runOnLogicThread = new LinkedList<>();

    public static void initLogic() {
        loop = new Thread(() -> {
            System.out.println("Logic started !");
            try {
                long time = System.nanoTime();
                Runnable run;
                while (true) {
                    Thread.sleep(1);
                    long elasped = System.nanoTime() - time;
                    time = System.nanoTime();
                    tickLogic(elasped);
                    while ((run = runOnLogicThread.poll()) != null)
                        run.run();
                }
            } catch (InterruptedException e) {
                System.out.println("Logic stopped !");
            }
        });

        Game.ball.appliedForce.add(gravity);
    }

    private static double currentTickPart = 0.0d;

    private static void tickLogic(final long timeElapsed) {
        long tick_number = (long) ((timeElapsed + currentTickPart) / (double) normalTickDuration);
        currentTickPart = (timeElapsed + currentTickPart) % normalTickDuration;

        for (int j = 0; j < tick_number; j++) {

            // Gravity, Left, Right and more...
            for (Vector force : Game.ball.appliedForce)
                applyForce(force, Game.ball.velocity);

            // Reaction
            applyOppositeForce();

            // Frictions
            applyForce(Game.ball.velocity.opposite().of(0.001 * (Game.ball.velocity.length() + 5)), Game.ball.velocity);

            double MoveX = Game.ball.velocity.x;
            double MoveY = Game.ball.velocity.y;

            double absMoveX = Math.abs(MoveX);
            double absMoveY = Math.abs(MoveY);

            for (int i = 0; i < max(absMoveX / pxHideBoxCheck, absMoveY / pxHideBoxCheck); i++) {

                // Reaction
                applyOppositeForce();

                if (absMoveX - pxHideBoxCheck > i)
                    Game.ball.point.x += MoveX / absMoveX * pxHideBoxCheck;
                if (absMoveY - pxHideBoxCheck > i)
                    Game.ball.point.y += MoveY / absMoveY * pxHideBoxCheck;

                MoveX = Game.ball.velocity.x;
                MoveY = Game.ball.velocity.y;

                absMoveX = Math.abs(MoveX);
                absMoveY = Math.abs(MoveY);

            }

            Game.ball.point.x += Game.ball.velocity.x % pxHideBoxCheck;
            Game.ball.point.y += Game.ball.velocity.y % pxHideBoxCheck;


        }


    }

    private static boolean applyOppositeForce() {
        boolean collide = false;
        for (DrawComponent comp : Game.components) {
            if (!(comp instanceof OrientedRectangle))
                continue;
            OrientedRectangle rect = (OrientedRectangle) comp;
            Collisions.CollisionResult result = getCollisionAngle(Game.ball, rect);
            if (!result.collide())
                continue;
            collide = true;
            float angle = y_vector.angleWith(result.projectionVector());
            if (Math.abs(Game.ball.velocity.angleWith(result.projectionVector())) < Collisions.RIGHT_ANGLE) {
                setOppositeForceLine(angle);
                applyForce(Game.ball.velocity.rotate(angle).opposite().y().rotate(-angle), Game.ball.velocity);
            }
        }

        return collide;
    }

    private static boolean colliding() {
        for (DrawComponent comp : Game.components) {
            if (!(comp instanceof OrientedRectangle))
                continue;
            OrientedRectangle rect = (OrientedRectangle) comp;
            Collisions.CollisionResult result = getCollisionAngle(Game.ball, rect);
            if (!result.collide())
                continue;
            return true;
        }

        return false;
    }

    private static void setOppositeForceLine(float angle) {
        Vector vector = Game.ball.velocity.rotate(angle).opposite().y().rotate(-angle).of(500);
        Collisions.line.a.x = Game.ball.point.x;
        Collisions.line.a.y = Game.ball.point.y;

        Collisions.line.b.x = Game.ball.point.x + vector.x;
        Collisions.line.b.y = Game.ball.point.y + vector.y;
    }


    public static void startLogic() {
        loop.start();
    }

    private static void applyForce(final Vector force, final Vector velocity) {
        velocity.x += force.x;
        velocity.y += force.y;
    }


}
