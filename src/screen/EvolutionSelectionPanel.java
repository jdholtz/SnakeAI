package src.screen;

import src.game.Constants;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EvolutionSelectionPanel extends Panel implements FocusListener {
    private final Screen screen;
    private final boolean loadFile;

    private Button startButton;

    // Text fields
    private TextField populationField;
    private TextField mutationField;
    private TextField maxMovesField;
    private TextField totalCellsField;
    private TextField FPSField;

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
        NumberFormat totalCellsFormat = NumberFormat.getIntegerInstance();
        NumberFormat FPSFormat = NumberFormat.getIntegerInstance();

        // Make text fields
        this.populationField = new TextField(populationFormat, populationLabel);
        this.mutationField = new TextField(mutationFormat, mutationLabel);
        this.maxMovesField = new TextField(maxMovesFormat, maxMovesLabel);
        this.totalCellsField = new TextField(totalCellsFormat, totalCellsLabel);
        this.FPSField = new TextField(FPSFormat, FPSLabel);

        // Set default values
        this.populationField.setValue(Constants.STARTING_POPULATION);
        this.mutationField.setValue(Constants.MUTATION_RATE);
        this.maxMovesField.setValue(Constants.MOVES_PER_APPLE);
        this.totalCellsField.setValue((int) Math.sqrt(Constants.TOTAL_CELLS));
        this.FPSField.setValue(1000 / Constants.FRAME_INTERVAL);

        // Set focus listeners
        this.populationField.addFocusListener(this);
        this.mutationField.addFocusListener(this);
        this.maxMovesField.addFocusListener(this);
        this.totalCellsField.addFocusListener(this);
        this.FPSField.addFocusListener(this);

        // Add all components to the panel
        this.add(populationLabel);
        this.add(mutationLabel);
        this.add(maxMovesLabel);
        this.add(totalCellsLabel);
        this.add(FPSLabel);
        this.add(this.populationField);
        this.add(this.mutationField);
        this.add(this.maxMovesField);
        this.add(this.totalCellsField);
        this.add(this.FPSField);

        // Start evolution button
        this.startButton = new Button("Start Evolution", (int) (Constants.SCREEN_WIDTH / 1.7), 525, 200, 35);
        this.startButton.addActionListener(this);
        this.add(startButton);
    }

    @Override
    public void focusGained(FocusEvent e) {
        TextField textField = (TextField) e.getSource();
        // Sets the cursor to the end of the text. Uses invokeLater to do that after all
        // the funky stuff done by JFormattedTextField. Calling it directly doesn't work.
        SwingUtilities.invokeLater(() -> textField.setCaretPosition(textField.getText().length()));
    }

    @Override
    public void focusLost(FocusEvent e) {
        this.adjustValues((TextField) e.getSource());
    }

    /**
     * This function is responsible for making sure the text box values
     * are within the correct ranges and format. Doing this now ensures
     * that it doesn't have to be done later on in the process and gives
     * users immediate feedback on exactly what values will be used.
     */
    private void adjustValues(TextField textField) {
        String text = textField.getText();
        // Remove all non-numbers (excluding decimal and negative sign) from text
        text = text.replaceAll("[^0-9.-]", "");

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
        } else if (textField == this.totalCellsField) {
            if (input > Math.sqrt(Constants.TOTAL_CELLS_LIMIT)) {
                this.totalCellsField.setValue((int) Math.sqrt(Constants.TOTAL_CELLS_LIMIT));
            } else {
                input = Math.max(input, 5);
                this.totalCellsField.setValue(input);
            }
        } else if (textField == this.FPSField) {
            if (input > 1000 / Constants.FRAME_INTERVAL_LIMIT) {
                this.FPSField.setValue(1000 / Constants.FRAME_INTERVAL_LIMIT);
            } else {
                input = Math.max(input, 1);
                this.FPSField.setValue(input);
            }
        }
     }

     private void adjustAllValues() {
        this.adjustValues(this.populationField);
        this.adjustValues(this.mutationField);
        this.adjustValues(this.maxMovesField);
        this.adjustValues(this.totalCellsField);
        this.adjustValues(this.FPSField);
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
        Constants.TOTAL_CELLS = Integer.parseInt(this.totalCellsField.getText()) * Integer.parseInt(this.totalCellsField.getText());
        Constants.FRAME_INTERVAL = 1000 / Integer.parseInt(this.FPSField.getText());
    }
}
