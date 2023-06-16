package main.game.graphics;

import main.game.logic.Listener;

import javax.swing.*;

public class GameFrame {

    public static JFrame gameFrame = new JFrame();
    public static Render render = new Render();

    protected static Thread graphicsThread;

    public static void initGameFrame() {
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setUndecorated(true);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        gameFrame.setContentPane(render);

        gameFrame.setVisible(true);

        setupGraphicThread();
        graphicsThread.start();
    }


    public static void setupGraphicThread() {
        graphicsThread = new Thread(() -> {
            try {
                while (true) {
                    render.repaint();
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void update() {
        gameFrame.repaint();
    }

}
