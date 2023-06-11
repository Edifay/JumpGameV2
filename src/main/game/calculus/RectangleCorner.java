package main.game.calculus;

/**
 *
 * A                                     B
 *   ___________________________________
 *  |                                   |
 *  |                                   |
 *  |                                   |
 *  |                                   |
 *  |___________________________________|
 * D                                     C
 *
 */
public class RectangleCorner {

    public Point A;
    public Point B;
    public Point C;
    public Point D;


    public RectangleCorner() {
        this.A = new Point();
        this.B = new Point();
        this.C = new Point();
        this.D = new Point();
    }

    public RectangleCorner(final Point A, final Point B, final Point C, final Point D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }

}
