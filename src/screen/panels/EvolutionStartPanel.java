package src.screen.panels;

import src.game.Constants;
import src.screen.Screen;
import src.screen.components.Button;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.event.ActionEvent;


public class EvolutionStartPanel extends Panel {
    private final Screen screen;
    private final JButton loadFileButton;
    private final JButton startNewButton;

    EvolutionStartPanel(Screen screen) {
        this.screen = screen;

        this.loadFileButton = new Button("Load From File", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.loadFileButton.addActionListener(this);
        this.add(this.loadFileButton);

        this.startNewButton = new Button("Start New Evolution", (int) (Constants.SCREEN_WIDTH / 1.6), Constants.SCREEN_HEIGHT / 2, 200, 40);
        this.startNewButton.addActionListener(this);
        this.add(this.startNewButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.drawTitle(g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.loadFileButton) {
            this.screen.setPanel(new EvolutionSelectionPanel(this.screen, true));
        } else if (actionEvent.getSource() == this.startNewButton) {
            this.screen.setPanel(new EvolutionSelectionPanel(this.screen, false));
        } else {
            System.out.println("Unknown actionEvent. Skipping...");
        }
    }
}
