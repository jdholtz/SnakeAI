package src.screen;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Screen implements KeyListener {
    private final GamePanel panel;

    public Screen () {
        JFrame mainScreen = new JFrame("Snake");
        this.panel = new GamePanel();
        mainScreen.add(this.panel);
        mainScreen.addKeyListener(this);

        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.pack();
        mainScreen.setResizable(false);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 80) { // p
            this.panel.pause();
        } else if (e.getKeyCode() == 75) { // k
            this.panel.game.stop();
        }

        this.panel.game.processKeyPress(e.getKeyCode());
    }

    // These functions are here to satisfy the KeyListener interface
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}