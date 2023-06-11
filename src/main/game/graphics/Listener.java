package main.game.graphics;

import main.game.Game;
import main.game.logic.MainLogic;

import java.awt.event.*;

public class Listener implements KeyListener, MouseListener, MouseMotionListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("KeyCode : " + e.getKeyCode());
        if (!Game.ball.appliedForce.contains(MainLogic.left) && e.getKeyCode() == 37)
            Game.ball.appliedForce.add(MainLogic.left);
        if (!Game.ball.appliedForce.contains(MainLogic.right) && e.getKeyCode() == 39)
            Game.ball.appliedForce.add(MainLogic.right);
        if (e.getKeyCode() == 38)
            Game.ball.velocity.y = -5f;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37)
            Game.ball.appliedForce.remove(MainLogic.left);
        if (e.getKeyCode() == 39)
            Game.ball.appliedForce.remove(MainLogic.right);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Game.ball.point.x = e.getX();
        Game.ball.point.y = e.getY();

        Game.ball.velocity.x = 0;
        Game.ball.velocity.y = 0;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Game.ball.point.x = e.getX();
        Game.ball.point.y = e.getY();

        Game.ball.velocity.x = 0;
        Game.ball.velocity.y = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
