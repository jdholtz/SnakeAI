package src.screen;

import src.game.Constants;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class StartPanel extends Panel {
    private final Screen screen;
    private final JButton playerButton;
    private final JButton evolutionButton;

    StartPanel(Screen screen) {
        this.screen = screen;

        this.playerButton = new Button("Start Player Game", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.playerButton.addActionListener(this);
        this.add(this.playerButton);

        this.evolutionButton = new Button("Start Evolution", (int) (Constants.SCREEN_WIDTH / 1.7), Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.evolutionButton.addActionListener(this);
        this.add(this.evolutionButton);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.playerButton) {
            this.screen.setPanel(new PlayerSelectionPanel(this.screen));
        } else if (actionEvent.getSource() == this.evolutionButton) {
            this.screen.setPanel(new EvolutionStartPanel(this.screen));
        } else {
            System.out.println("Unknown actionEvent. Skipping...");
        }
    }
}
