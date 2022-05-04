package src.screen;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class StartPanel extends Panel {
    private final Screen screen;
    private final JButton playerButton;
    private final JButton simulationButton;

    StartPanel(Screen screen) {
        super();
        this.screen = screen;

        this.playerButton = new Button("Start Player Game", 200, 500, 200, 40);
        this.playerButton.addActionListener(this);
        this.add(this.playerButton);

        this.simulationButton = new Button("Start Evolution", 600, 500, 200, 40);
        this.simulationButton.addActionListener(this);
        this.add(this.simulationButton);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.playerButton) {
            this.screen.setPanel(new PlayerPanel());
        } else if (actionEvent.getSource() == this.simulationButton) {
            this.screen.setPanel(new EvolutionPanel());
        } else {
            System.out.println("Unknown actionEvent. Skipping...");
        }
    }
}
