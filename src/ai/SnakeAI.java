package src.ai;

import src.game.Constants;
import src.game.Game;
import src.game.Position;
import src.neural_network.NeuralNetwork;

import java.util.Random;


/**
 * This class controls the snake. It performs the snake's actions and gets
 * all the information the network needs to make a prediction.
 */
public class SnakeAI {
    public double[][] hiddenLayerWeights;
    public double[][] outputLayerWeights;
    public boolean isAlive = true;

    private final Game game;
    private final NeuralNetwork network;
    private int moves = 0;
    private int fitness = 0;
    private int score = 0;

    SnakeAI(Game game) {
        this.intializeWeights();
        this.game = game;
        this.network = new NeuralNetwork(this.hiddenLayerWeights, this.outputLayerWeights);
    }

    public SnakeAI(Game game, double[][] hiddenLayerWeights, double[][] outputLayerWeights) {
        // Copy the matrices so each snake doesn't point to the same weights
        double[][][] weightsCopy = this.copyWeights(hiddenLayerWeights, outputLayerWeights);
        this.hiddenLayerWeights = weightsCopy[0];
        this.outputLayerWeights = weightsCopy[1];
        this.mutate();

        this.game = game;
        this.network = new NeuralNetwork(this.hiddenLayerWeights, this.outputLayerWeights);
    }

    private void intializeWeights() {
        // All values of the weights are between -1 and 1
        this.hiddenLayerWeights = new double[Constants.NUM_HIDDEN][Constants.NUM_INPUTS];
        this.outputLayerWeights = new double[4][Constants.NUM_HIDDEN];

        for (int i = 0; i < hiddenLayerWeights.length; i++) {
            for (int j = 0; j < hiddenLayerWeights[i].length; j++) {
                Random rand = new Random();
                hiddenLayerWeights[i][j] = rand.nextDouble() * 2 - 1;
            }
        }

        for (int i = 0; i < outputLayerWeights.length; i++) {
            for (int j = 0; j < outputLayerWeights[i].length; j++) {
                Random rand = new Random();
                outputLayerWeights[i][j] = rand.nextDouble() * 2 - 1;
            }
        }
    }

    /**
     * Doing a clone() on the 2D arrays only shallowly clones, meaning that
     * the inner arrays still point to the same object. This function ensures
     * that does not happen
     */
    private double[][][] copyWeights(double[][] hiddenLayerWeights, double[][] outputLayerWeights) {
        double[][] newHiddenWeights = new double[hiddenLayerWeights.length][];
        for (int i = 0; i < newHiddenWeights.length; i++) {
            newHiddenWeights[i] = hiddenLayerWeights[i].clone();
        }

        double[][] newOutputWeights = new double[outputLayerWeights.length][];
        for (int i = 0; i <  newOutputWeights.length; i++) {
            newOutputWeights[i] = outputLayerWeights[i].clone();
        }

        return new double[][][] {newHiddenWeights, newOutputWeights};
    }

    private void mutate() {
        for (int i = 0; i < hiddenLayerWeights.length; i++) {
            for (int j = 0; j < hiddenLayerWeights[i].length; j++) {
                Random rand = new Random();
                if (rand.nextDouble() < Constants.MUTATION_RATE) {
                    // Modify the gene a little. Just setting a random value to it doesn't work well
                    hiddenLayerWeights[i][j] += rand.nextGaussian() / 5;

                    // Ensure the values are between -1 and 1
                    if (hiddenLayerWeights[i][j] > 1) {
                        hiddenLayerWeights[i][j] = 1;
                    } else if (hiddenLayerWeights[i][j] < -1) {
                        hiddenLayerWeights[i][j] = -1;
                    }
                }
            }
        }

        for (int i = 0; i < outputLayerWeights.length; i++) {
            for (int j = 0; j < outputLayerWeights[i].length; j++) {
                Random rand = new Random();
                if (rand.nextDouble() < Constants.MUTATION_RATE) {
                    // Modify the gene a little. Just setting a random value to it doesn't work well
                    outputLayerWeights[i][j] += rand.nextGaussian() / 5;

                    // Ensure the values are between -1 and 1
                    if (outputLayerWeights[i][j] > 1) {
                        outputLayerWeights[i][j] = 1;
                    } else if (outputLayerWeights[i][j] < -1) {
                        outputLayerWeights[i][j] = -1;
                    }
                }
            }
        }
    }

    public void performAction() {
        double[] inputs = this.getInputs();
        double[] output = network.getPrediction(inputs);
        this.game.snake.direction = this.getBestOutput(output);
        this.updateScore();

        this.moves += 1;
        int maxMoves = Constants.MOVES_PER_APPLE * (this.score + 1);
        // The snake is most likely in a loop
        if (this.moves >= maxMoves) {
            this.game.stop();
        }
    }

    private double[] getInputs() {
        // All input values are between 0 and 1
        double[] inputs = new double[Constants.NUM_INPUTS];

        // Checks the distance to the wall, its body, and if it sees the apple in eight directions
        int iterations = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // Don't calculate the distance if there is no direction to look in
                if (i == 0 && j == 0) {
                    continue;
                }

                double[] distances = this.getDistances(j, i);
                inputs[iterations] = distances[0]; // Distance to wall
                inputs[iterations + 1] = distances[1]; // If the apple is in that direction
                inputs[iterations + 2] = distances[2]; // Distance to its body

                iterations += 3;
            }
        }

        return inputs;
    }

    /**
     * Gets the distance from the snake's head to the wall and its own body when
     * looking in the vector's direction (parameters x and y) as well as if the
     * apple is in that direction (0 or 1). If the apple/body is not found, the
     * output will be 0 for that position in the array.
     */
    private double[] getDistances(int x, int y) {
        double[] distances = new double[3];
        Position headPos = this.game.snake.body[0];
        Position position = headPos.copy();

        double distance = 0;
        double distToBody = 0;
        boolean hasFoundApple = false;

        // Increment everything first so it doesn't look at itself
        do {
            distance += 1;
            position.setX(position.getX() + x);
            position.setY(position.getY() + y);

            if (distToBody == 0 && this.collidesWithBody(position)) {
                distToBody = distance;
            }

            if (!hasFoundApple && this.collidesWithApple(position)) {
                hasFoundApple = true;
            }
        } while (!this.collidesWithBorder(position));

        distances[0] = 1 / distance;
        distances[1] = hasFoundApple ? 1 : 0;
        distances[2] = distToBody == 0 ? 0 : 1 / distToBody;

        return distances;
    }

    private boolean collidesWithBorder(Position pos) {
        // Might as well use the collision detection from the game class
        return this.game.snakeCollidesWithBorder(pos);
    }

    private boolean collidesWithBody(Position pos) {
        return this.game.snakeCollidesWithItself(pos);
    }

    private boolean collidesWithApple(Position pos) {
        return this.game.snakeCollidesWithApple(pos);
    }

    /**
     * Returns the index of the highest number
     */
    private int getBestOutput(double[] output) {
        int max = 0;

        for (int i = 1; i < output.length; i++) {
            if (output[i] > output[max]) {
                max = i;
            }
        }
        return max;
    }

    public int getScore() {
        return this.score;
    }

    public void updateScore() {
        this.score = this.game.snake.getLength() - 3; // Starting length is 3
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void end() {
        this.isAlive = false;
        this.fitness = this.calculateFitness();
    }

    /**
     * This is how the snakes are judged based on their performance.
     *
     * How it works:
     * This function prioritizes apples over staying alive longer. At first,
     * it encourages staying alive, but as the game goes on, there is a bigger
     * penalty for taking many moves, which prevents every snake from looping.
     */
    private int calculateFitness() {
        return (int) (this.moves + 1000 * Math.pow(this.score, 2) - 0.25 * Math.pow(this.moves, 1.4));
    }

    public double getFitness() {
        return this.fitness;
    }

    public Game getGame() {
        return this.game;
    }

    public SnakeAI copy() {
        SnakeAI newSnake = new SnakeAI(new Game(), this.hiddenLayerWeights, this.outputLayerWeights);
        newSnake.setScore(this.score);
        return newSnake;
    }
}
