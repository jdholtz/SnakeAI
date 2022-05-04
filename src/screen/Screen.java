package src.screen;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Screen implements KeyListener {
    private final JFrame mainScreen;
    private Panel panel;

    public Screen () {
        this.mainScreen = new JFrame("Snake");
        this.mainScreen.addKeyListener(this);
        this.mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainScreen.setResizable(false);

        this.panel = new StartPanel(this);
        this.mainScreen.add(this.panel);
        this.mainScreen.pack();
        this.mainScreen.setLocationRelativeTo(null);
        this.mainScreen.setVisible(true);
    }

    public void setPanel(Panel panel) {
        this.mainScreen.remove(this.panel);
        this.panel = panel;
        this.mainScreen.add(panel);
        this.panel.revalidate();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.panel.processKeyPress(e.getKeyCode());
    }

    // These functions are here to satisfy the KeyListener interface
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
