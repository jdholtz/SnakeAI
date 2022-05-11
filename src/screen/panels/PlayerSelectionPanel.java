package src.screen.panels;

import src.game.Constants;
import src.screen.Screen;
import src.screen.components.Button;
import src.screen.components.Label;
import src.screen.components.TextField;

import java.awt.event.ActionEvent;


public class PlayerSelectionPanel  extends SelectionPanel {

    PlayerSelectionPanel(Screen screen) {
        this.screen = screen;
        this.initGUI();
    }

    @Override
    protected void initGUI() {
        // Make labels
        Label totalCellsLabel = new Label("Board Size (one side)", Constants.SCREEN_WIDTH / 2 - 100, 300, 200, 30);
        Label FPSLabel = new Label("FPS", Constants.SCREEN_WIDTH / 2 - 100, 400, 200, 30);

        // Make text fields
        this.totalCellsField = new TextField(this.totalCellsFormat, totalCellsLabel);
        this.FPSField = new TextField(this.FPSFormat, FPSLabel);

        // Add all components to the panel
        this.add(totalCellsLabel);
        this.add(FPSLabel);

        this.startButton = new Button("Start Evolution", Constants.SCREEN_WIDTH / 2 - 100, 525, 200, 35);

        super.initGUI();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.startButton) {
            super.adjustAllValues();
            super.setValues();
            this.screen.setPanel(new PlayerPanel());
        } else {
            System.out.println("Unknown actionEvent. Skipping...");
        }
    }
}
