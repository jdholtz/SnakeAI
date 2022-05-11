package src.screen.panels;

import src.game.Constants;
import src.game.Position;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
        this.drawString(string, g, x, y, true);
    }

    protected void drawString(String string, Graphics g, int x, int y, boolean shouldCenter) {
        Position pos;
        if (shouldCenter) {
            pos = this.getCenteredTextPosition(g, string, x, y);
        } else {
            pos = new Position(x, y);
        }

        g.drawString(string, pos.getX(), pos.getY());
    }

    protected Position getCenteredTextPosition(Graphics g, String string, int x, int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int newX = x - (fontMetrics.stringWidth(string) / 2);
        int newY = ((y - fontMetrics.getHeight())) + fontMetrics.getAscent();
        return new Position(newX, newY);
    }

    protected void drawTitle(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 96));
        g.setColor(Color.GREEN);

        int x = Constants.SCREEN_WIDTH / 2;
        int y = Constants.SCREEN_HEIGHT / 5;
        this.drawString("Snake AI", g, x, y);
    }
}
