package main.game.logic;

import main.game.Game;
import main.game.calculus.Point;
import main.game.calculus.RectangleCorner;
import main.game.graphics.entities.Ball;
import main.game.graphics.entities.Line;
import main.game.graphics.entities.OrientedRectangle;

import static main.game.logic.MainLogic.runOnLogicThread;

public class Collisions {

    public static final float RIGHT_ANGLE = (float) (Math.PI / 2);

    public static final OrientedRectangle rect = new OrientedRectangle(new Point(400f, 450f), 0, 600, 30f);
    public static final Ball ball_test = new Ball(new Point(100f, 50f), 15f);

    public static final Line line = new Line(new Point(), new Point());

    static {
        runOnLogicThread.add(() -> {
            //  Game.components.add(ball_test);
            //  Game.components.add(rect);
            Game.components.add(line);
        });
    }

    public static CollisionResult getCollisionAngle(final Ball ball, final OrientedRectangle rect) {
        Point rotated_ball_point = rotate(ball.point, rect.point, rect.angle);
        RectangleCorner corners = rect.defaultRectangle();

        Point projection = new Point();

        if (corners.A.y > rotated_ball_point.y && rect.A) {
            projection = new Point(rotated_ball_point.x, corners.A.y);
            if (projection.x < corners.A.x)
                projection.x = corners.A.x;
            if (projection.x > corners.B.x)
                projection.x = corners.B.x;
        } else if (corners.B.x < rotated_ball_point.x && rect.B) {
            projection = new Point(corners.B.x, rotated_ball_point.y);
            if (projection.y < corners.B.y)
                projection.y = corners.B.y;
            if (projection.y > corners.C.y)
                projection.y = corners.C.y;
        } else if (corners.C.y < rotated_ball_point.y && rect.C) {
            projection = new Point(rotated_ball_point.x, corners.C.y);
            if (projection.x > corners.C.x)
                projection.x = corners.C.x;
            if (projection.x < corners.D.x)
                projection.x = corners.D.x;
        } else if (corners.D.x > rotated_ball_point.x && rect.D) {
            projection = new Point(corners.D.x, rotated_ball_point.y);
            if (projection.y < corners.A.y)
                projection.y = corners.A.y;
            if (projection.y > corners.D.y)
                projection.y = corners.D.y;
        }

        if (false && projection.x == 0 && projection.y == 0 && rotated_ball_point.x > corners.A.x && rotated_ball_point.x < corners.C.x && rotated_ball_point.y > corners.A.y && rotated_ball_point.y < corners.C.y) {
            // DO SOMETING IF IN Rectangle.
        }

        if (distancePoints(rotated_ball_point, projection) < ball.radius) {
            Point tp = rotate(projection, rect.point, -rect.angle);
            return new CollisionResult(true, new Vector(tp.x - ball.point.x, tp.y - ball.point.y));
        }

        return new CollisionResult(false, new Vector(0, 0));

    }

    public record CollisionResult(boolean collide, Vector projectionVector) {
    }

    public static Point rotate(Point point, Point originPoint, float angle) {
        return new Point(
                originPoint.x + (point.x - originPoint.x) * Math.cos(angle) - (point.y - originPoint.y) * Math.sin(angle),
                originPoint.y + (point.x - originPoint.x) * Math.sin(angle) + (point.y - originPoint.y) * Math.cos(angle)
        );
    }

    public static float distancePoints(Point a, Point b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2d) + Math.pow(a.y - b.y, 2d));
    }

}
