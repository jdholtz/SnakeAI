package src.screen;

import src.game.Constants;

import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

public class SelectionPanel extends Panel implements FocusListener {
    protected Screen screen;
    protected Button startButton;

    protected NumberFormat totalCellsFormat = NumberFormat.getIntegerInstance();
    protected NumberFormat FPSFormat = NumberFormat.getIntegerInstance();

    protected TextField totalCellsField;
    protected TextField FPSField;

    SelectionPanel() {}

    protected void initGUI() {
        // The fields and button have to be initialized before this function is called

        // Set default values
        this.totalCellsField.setValue((int) Math.sqrt(Constants.TOTAL_CELLS));
        this.FPSField.setValue(1000 / Constants.FRAME_INTERVAL);

        // Set focus listeners
        this.totalCellsField.addFocusListener(this);
        this.FPSField.addFocusListener(this);

        // Add components to panel
        this.add(this.totalCellsField);
        this.add(this.FPSField);

        this.startButton.addActionListener(this);
        this.add(startButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.drawTitle(g);
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
    protected void adjustValues(TextField textField) {
        String text = textField.getText();
        // Remove all non-numbers (excluding decimal and negative sign) from text
        text = text.replaceAll("[^0-9.-]", "");

        // Get rid of everything after the decimal if there is one
        text = text.split("\\.")[0];

        // If the text field is empty
        int input;
        if (text.equals("")) {
            input = 0;
        } else {
            input = Integer.parseInt(text);
        }

        if (textField == this.totalCellsField) {
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

    protected void adjustAllValues() {
        this.adjustValues(this.totalCellsField);
        this.adjustValues(this.FPSField);
    }

    protected void setValues() {
        Constants.TOTAL_CELLS = Integer.parseInt(this.totalCellsField.getText()) * Integer.parseInt(this.totalCellsField.getText());
        Constants.FRAME_INTERVAL = 1000 / Integer.parseInt(this.FPSField.getText());
    }
}
