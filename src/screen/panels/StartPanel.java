package src.screen.panels;

import src.game.Constants;
import src.screen.Screen;
import src.screen.components.Button;
import src.screen.panels.EvolutionStartPanel;
import src.screen.panels.Panel;
import src.screen.panels.PlayerSelectionPanel;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.event.ActionEvent;


public class StartPanel extends Panel {
    private final Screen screen;
    private final JButton playerButton;
    private final JButton evolutionButton;

    public StartPanel(Screen screen) {
        this.screen = screen;

        this.playerButton = new Button("Start Player Game", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.playerButton.addActionListener(this);
        this.add(this.playerButton);

        this.evolutionButton = new Button("Start Evolution", (int) (Constants.SCREEN_WIDTH / 1.6), Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.evolutionButton.addActionListener(this);
        this.add(this.evolutionButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.drawTitle(g);
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
