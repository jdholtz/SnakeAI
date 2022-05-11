package src.screen.panels;

import src.game.Constants;
import src.screen.Screen;
import src.screen.components.Button;
import src.screen.components.Label;
import src.screen.components.TextField;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class EvolutionSelectionPanel extends SelectionPanel {
    private final boolean loadFile;

    // Text fields
    private TextField populationField;
    private TextField mutationField;
    private TextField maxMovesField;

    EvolutionSelectionPanel(Screen screen, boolean loadFile) {
        this.screen = screen;
        this.loadFile = loadFile;

        this.initGUI();
    }

    public void initGUI() {
        // Make labels
        Label populationLabel = new Label("Starting Population", Constants.SCREEN_WIDTH / 5, 300, 200, 30);
        Label mutationLabel = new Label("Mutation Rate", Constants.SCREEN_WIDTH / 5, 400, 200, 30);
        Label maxMovesLabel = new Label("Max Moves Per Apple", Constants.SCREEN_WIDTH / 5, 500, 200, 30);
        Label totalCellsLabel = new Label("Board Size (one side)", (int) (Constants.SCREEN_WIDTH / 1.7), 300, 200, 30);
        Label FPSLabel = new Label("FPS", (int) (Constants.SCREEN_WIDTH / 1.7), 400, 200, 30);

        // Make text field formats
        NumberFormat populationFormat = NumberFormat.getIntegerInstance();
        NumberFormat mutationFormat = new DecimalFormat("0.##");
        NumberFormat maxMovesFormat = NumberFormat.getIntegerInstance();

        // Make text fields
        this.populationField = new TextField(populationFormat, populationLabel);
        this.mutationField = new TextField(mutationFormat, mutationLabel);
        this.maxMovesField = new TextField(maxMovesFormat, maxMovesLabel);
        this.totalCellsField = new TextField(this.totalCellsFormat, totalCellsLabel);
        this.FPSField = new TextField(this.FPSFormat, FPSLabel);

        // Set default values
        this.populationField.setValue(Constants.STARTING_POPULATION);
        this.mutationField.setValue(Constants.MUTATION_RATE);
        this.maxMovesField.setValue(Constants.MOVES_PER_APPLE);

        // Set focus listeners
        this.populationField.addFocusListener(this);
        this.mutationField.addFocusListener(this);
        this.maxMovesField.addFocusListener(this);

        // Add all components to the panel
        this.add(populationLabel);
        this.add(mutationLabel);
        this.add(maxMovesLabel);
        this.add(totalCellsLabel);
        this.add(FPSLabel);
        this.add(this.populationField);
        this.add(this.mutationField);
        this.add(this.maxMovesField);

        // Start evolution button
        this.startButton = new Button("Start Evolution", (int) (Constants.SCREEN_WIDTH / 1.7), 525, 200, 35);
        super.initGUI();
    }

    @Override
    protected void adjustValues(TextField textField) {
        String text = textField.getText();
        // Remove all non-numbers (excluding decimal and negative sign) from text
        text = text.replaceAll("[^0-9.-]", "");

        // If the text box is empty
        if (text.equals("")) {
            text = "0";
        }

        if (textField == this.mutationField) {
            double input = Double.parseDouble(text);

            if (input > 1) {
                this.mutationField.setValue(1.0);
            } else {
                input = Math.max(input, 0.0);
                this.mutationField.setValue(input);
            }
        }
        // Get rid of everything after the decimal if there is one
        text = text.split("\\.")[0];

        int input = Integer.parseInt(text);

        if (textField == this.populationField) {
            if (input > Constants.STARTING_POPULATION_LIMIT) {
                this.populationField.setValue(Constants.STARTING_POPULATION_LIMIT);
            } else {
                input = Math.max(input, 1);
                this.populationField.setValue(input);
            }
        } else if (textField == this.maxMovesField) {
            if (input > Constants.MAX_MOVES_LIMIT) {
                this.maxMovesField.setValue(Constants.MAX_MOVES_LIMIT);
            } else {
                input = Math.max(input, 2);
                this.maxMovesField.setValue(input);
            }
        }

        super.adjustValues(textField);
     }

     @Override
     protected void adjustAllValues() {
        this.adjustValues(this.populationField);
        this.adjustValues(this.mutationField);
        this.adjustValues(this.maxMovesField);
        super.adjustAllValues();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.startButton) {
            this.setEvolutionValues();
            this.screen.setPanel(new EvolutionPanel(this.loadFile));
        } else {
            System.out.println("Unknown actionEvent. Skipping...");
        }
    }

    /**
     * This function sets all the values from the text fields into the program
     * before starting the evolution.
     */
    private void setEvolutionValues() {
        this.adjustAllValues();
        Constants.STARTING_POPULATION = Integer.parseInt(String.valueOf(this.populationField.getValue()));
        Constants.MUTATION_RATE = Double.parseDouble(this.mutationField.getText());
        Constants.MOVES_PER_APPLE = Integer.parseInt(String.valueOf(this.maxMovesField.getValue()));
        super.setValues();
    }
}
