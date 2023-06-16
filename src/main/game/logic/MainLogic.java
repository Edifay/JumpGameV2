package main.game.logic;

import main.game.Game;
import main.game.graphics.DrawComponent;
import main.game.graphics.GameFrame;
import main.game.graphics.entities.OrientedRectangle;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.max;
import static main.game.logic.Collisions.getCollisionAngle;

public class MainLogic {

    protected static Thread loop;

    protected static final Listener listener = new Listener();

    protected static Vector gravity = new Vector(0f, 0.035f);
    public static Vector right = new Vector(0.03f, 0f);
    public static Vector left = new Vector(-0.03f, 0f);

    public static final double JUMP_GRIP_ANGLE = Math.PI / 3;
    public static boolean jump = false;
    public static boolean alreadyJump = false;
    public static boolean doubleJumpAvailable = false;
    public static final float JUMP_FORCE = -3.5f;

    protected final static long NORMAL_TICK_DURATION = 2200000L;
    private static double currentTickState = 0.0d;


    protected final static int PX_HIDE_BOX_CHECK = 1;

    private static final Vector X_VECTOR = new Vector(1, 0);
    private static final Vector Y_VECTOR = new Vector(0, 1);

    public static final Queue<Runnable> runOnLogicThread = new LinkedList<>();

    public static void initLogic() {
        GameFrame.render.addMouseListener(listener);
        GameFrame.gameFrame.addKeyListener(listener);
        GameFrame.render.addMouseMotionListener(listener);

        Game.ball.appliedForce.add(gravity);

        setupLoop();
    }

    private static void setupLoop() {
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
    }

    private static void tickLogic(final long timeElapsed) {
        long tick_number = (long) ((timeElapsed + currentTickState) / (double) NORMAL_TICK_DURATION);
        currentTickState = (timeElapsed + currentTickState) % NORMAL_TICK_DURATION;

        for (int j = 0; j < tick_number; j++) {

            // Gravity, Left, Right and more...
            for (Vector force : Game.ball.appliedForce)
                applyForce(force, Game.ball.velocity);

            // Reaction
            ReactionForceResult reactionForceResult = applyReactionForce();

            // Frictions
            applyForce(Game.ball.velocity.opposite().x().of(0.001 * (1.5 * Game.ball.velocity.length() + 5)), Game.ball.velocity); // x
            applyForce(Game.ball.velocity.opposite().y().of(0.0001 * Game.ball.velocity.length()), Game.ball.velocity); // y

            jumpCalculator(reactionForceResult);

            double MoveX = Game.ball.velocity.x;
            double MoveY = Game.ball.velocity.y;

            moveBall(MoveX, MoveY, Math.abs(MoveX), Math.abs(MoveY));

        }

    }

    private static void moveBall(double MoveX, double MoveY, double absMoveX, double absMoveY) {
        ReactionForceResult reactionForceResult;
        for (int i = 0; i < max(absMoveX / PX_HIDE_BOX_CHECK, absMoveY / PX_HIDE_BOX_CHECK); i++) {

            // Reaction
            reactionForceResult = applyReactionForce();

            jumpCalculator(reactionForceResult);

            if (absMoveX - PX_HIDE_BOX_CHECK > i)
                Game.ball.point.x += MoveX / absMoveX * PX_HIDE_BOX_CHECK;
            if (absMoveY - PX_HIDE_BOX_CHECK > i)
                Game.ball.point.y += MoveY / absMoveY * PX_HIDE_BOX_CHECK;

            MoveX = Game.ball.velocity.x;
            MoveY = Game.ball.velocity.y;

            absMoveX = Math.abs(MoveX);
            absMoveY = Math.abs(MoveY);

        }

        Game.ball.point.x += Game.ball.velocity.x % PX_HIDE_BOX_CHECK;
        Game.ball.point.y += Game.ball.velocity.y % PX_HIDE_BOX_CHECK;
    }

    private static void jumpCalculator(ReactionForceResult reactionForceResult) {
        if (reactionForceResult.collide && Math.abs(reactionForceResult.angle) < JUMP_GRIP_ANGLE) {
            doubleJumpAvailable = true;
            alreadyJump = false;
            if (jump) {
                Game.ball.velocity.y = JUMP_FORCE;
                alreadyJump = true;
            }
        } else if (jump && (!reactionForceResult.collide || Math.abs(reactionForceResult.angle - Math.PI) > JUMP_GRIP_ANGLE) && doubleJumpAvailable && !alreadyJump) {
            alreadyJump = true;
            doubleJumpAvailable = false;
            Game.ball.velocity.y = JUMP_FORCE;
        }
    }

    private static ReactionForceResult applyReactionForce() {
        boolean collide = false;
        float angle = 0f;
        for (DrawComponent comp : Game.components) {
            if (!(comp instanceof OrientedRectangle))
                continue;
            OrientedRectangle rect = (OrientedRectangle) comp;
            Collisions.CollisionResult result = getCollisionAngle(Game.ball, rect);
            if (!result.collide())
                continue;
            collide = true;
            angle = Y_VECTOR.angleWith(result.projectionVector());
            if (Math.abs(Game.ball.velocity.angleWith(result.projectionVector())) < Collisions.RIGHT_ANGLE) {
                setOppositeForceLine(angle);
                applyForce(Game.ball.velocity.rotate(angle).opposite().y().rotate(-angle), Game.ball.velocity);
                if (result.projectionVector().length() + 0.5f < Game.ball.radius) {
                    System.out.println("Adding !");
                    applyForce(result.projectionVector().opposite().of(0.02), Game.ball.velocity);
                }

            }
        }

        if (!collide)
            System.out.println("DROPPING !");

        return new ReactionForceResult(collide, angle);
    }

    public record ReactionForceResult(boolean collide, float angle) {
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

    public static void stopLogic() {
        loop.interrupt();
        setupLoop();
    }

    public static boolean isRunning() {
        return loop.isAlive();
    }

    private static void applyForce(final Vector force, final Vector velocity) {
        velocity.x += force.x;
        velocity.y += force.y;
    }


}
