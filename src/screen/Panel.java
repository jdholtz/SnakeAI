package src.screen;

import src.game.Constants;
import src.game.Position;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Panel extends JPanel implements ActionListener {
    protected boolean isPaused;

    Panel() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {}

    public void processKeyPress(int keyCode) {} // Used by the game panel classes

    protected void pause() {
        this.isPaused = !this.isPaused;
    }

    protected void drawString(String string, Graphics g, int x, int y) {
        Position centeredPos = this.getCenteredTextPosition(g, string, x, y);
        g.drawString(string, centeredPos.getX(), centeredPos.getY());
    }

    protected Position getCenteredTextPosition(Graphics g, String string, int x, int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int newX = x - (fontMetrics.stringWidth(string) / 2);
        int newY = ((y - fontMetrics.getHeight())) + fontMetrics.getAscent();
        return new Position(newX, newY);
    }
}
