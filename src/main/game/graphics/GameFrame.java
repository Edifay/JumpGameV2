package main.game.graphics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameFrame {

    public static JFrame gameFrame = new JFrame();
    public static Render render = new Render();

    protected static Listener listener = new Listener();

    protected static Thread graphicsThread;

    public static void initGameFrame() {
        gameFrame.setSize(1280, 720);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        render.addMouseListener(listener);
        gameFrame.addKeyListener(listener);
        render.addMouseMotionListener(listener);

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
