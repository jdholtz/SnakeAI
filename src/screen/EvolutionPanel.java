package src.screen;

import src.ai.GameAI;
import src.game.Constants;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class EvolutionPanel extends GamePanel {
    private final GameAI gameAI;

    EvolutionPanel() {
        super();
        this.gameAI = new GameAI();
        this.game = this.gameAI.getActiveGame();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.isPaused) {
            return;
        }

        if (actionEvent.getSource() == super.timer) {
            this.gameAI.run();
            this.game = this.gameAI.getActiveGame();
        }
        super.actionPerformed(actionEvent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGeneration(g);
    }

    private void drawGeneration(Graphics g) {
        int x = (int) (Constants.SCREEN_WIDTH / 4.2);
        int y = Constants.SCREEN_HEIGHT / 16;
        super.drawString("Generation " + this.gameAI.numGeneration, g, x, y);
    }
}
