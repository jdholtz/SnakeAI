package src.screen.panels;

import src.game.Constants;
import src.game.Game;
import src.screen.panels.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;


public class PlayerPanel extends GamePanel {
    PlayerPanel() {
        super.game = new Game();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.isPaused) {
            return;
        }

        if (actionEvent.getSource() == super.timer) {
            this.game.run();
        }
        super.actionPerformed(actionEvent);
    }

    @Override
    public void processKeyPress(int keyCode) {
        super.processKeyPress(keyCode);
        if (!this.isPaused) {
            this.game.processKeyPress(keyCode);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!super.game.isRunning) {
            this.drawGameOverScreen(g);
        }
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 64));
        int x = Constants.SCREEN_WIDTH / 2;
        int y = Constants.SCREEN_HEIGHT / 2;
        super.drawString("Game Over", g, x, y);
    }
}
