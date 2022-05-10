package src.screen;

import src.ai.GameAI;
import src.game.Constants;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class EvolutionPanel extends GamePanel {
    private final GameAI gameAI;

    EvolutionPanel(boolean loadFile) {
        this.gameAI = new GameAI(loadFile);
        this.game = this.gameAI.getActiveGame();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.isPaused) {
            return;
        }

        if (actionEvent.getSource() == this.timer) {
            this.gameAI.run();
            this.game = this.gameAI.getActiveGame();
        }
        super.actionPerformed(actionEvent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawStats(g);
    }

    private void drawStats(Graphics g) {
        int x = (int) (Constants.SCREEN_WIDTH / 4.2);
        int y = Constants.SCREEN_HEIGHT / 16;
        super.drawString("Generation " + this.gameAI.numGeneration, g, x, y);

        if (this.gameAI.numGeneration < 2) {
            return;
        }

        double[] stats = this.gameAI.generation.getStats();

        x = Constants.SCREEN_WIDTH / 10;
        y = (int) (Constants.SCREEN_HEIGHT / 1.05);
        super.drawString("Best score for generation " + this.gameAI.numGeneration + ": " + stats[0], g, x, y, false);

        y += 30;
        super.drawString("Average score for generation " + this.gameAI.numGeneration + ": " + stats[1], g, x, y, false);
    }
}
