import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class SnakeAI {
    public double[][] hiddenLayerWeights;
    public double[][] outputLayerWeights;
    private final Game game;
    private final NeuralNetwork network;
    private int moves;
    public int fitness;
    private int maxMoves;
    public boolean isAlive;

    SnakeAI(Game game, double[][] hiddenLayerWeights, double[][] outputLayerWeights) {
        if (hiddenLayerWeights == null || outputLayerWeights == null) {
            this.intializeWeights();
        } else {
            this.hiddenLayerWeights = hiddenLayerWeights;
            this.outputLayerWeights = outputLayerWeights;
        }

        this.game = game;
        this.network = new NeuralNetwork(this.hiddenLayerWeights, this.outputLayerWeights);
        this.maxMoves = 30;
        this.fitness = 0;
        this.moves = 0;
        this.isAlive = true;
    }

    private void intializeWeights() {
        this.hiddenLayerWeights = new double[8][24];
        this.outputLayerWeights = new double[4][24];

        for (int i = 0; i < hiddenLayerWeights.length; i++) {
            for (int j = 0; j < hiddenLayerWeights[i].length; j++) {
                Random rand = ThreadLocalRandom.current();
                hiddenLayerWeights[i][j] = rand.nextDouble() * 2 - 1;
            }
        }

        for (int i = 0; i < outputLayerWeights.length; i++) {
            for (int j = 0; j < outputLayerWeights[i].length; j++) {
                Random rand = ThreadLocalRandom.current();
                outputLayerWeights[i][j] = rand.nextDouble() * 2 - 1;
            }
        }
    }

    public void performAction() {
        double[] inputs = this.getInputs();
        double[] output = network.getAction(inputs);
        this.game.snake.direction = this.getBestOutput(output);

        this.moves += 1;
        this.maxMoves = 30 * (this.getScore() + 1);
        if (this.moves >= this.maxMoves) {
            this.game.stop();
        }
    }

    private double[] getInputs() {
        // All input values are between 0 and 1
        double[] inputs = new double[24];
        int iterations = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                double[] distances = this.getDistances(i, j);
                inputs[iterations] = distances[0];
                inputs[iterations + 1] = distances[1];
                inputs[iterations + 2] = distances[2];

                iterations += 3;
            }
        }

        return inputs;
    }

    private double[] getDistances(int x, int y) {
        double[] distances = new double[3];
        Position headPos = this.game.snake.body[0];
        Position position = headPos.copy();

        double distToBody = 0;
        double distToApple = 0;
        double distance = 0;

        // Increment everything first so it doesn't look at itself
        do {
            distance += 1;
            position.setX(position.getX() + x);
            position.setY(position.getY() + y);

            if (this.collidesWithBody(position) && distToBody == 0) {
                distToBody = distance;
            }

            if (this.collidesWithApple(position) && distToApple == 0) {
                distToApple = distance;
            }
        } while (!this.collidesWithBorder(position));

        distances[0] = 1 / distance;
        distances[1] = distToBody == 0 ? 0 : 1 / distToBody;
        distances[2] = distToApple == 0 ? 0 : 1 / distToApple;

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
        return this.game.snake.getLength() - 3;
    }

    public void end() {
        this.isAlive = false;
        this.fitness = this.calculateFitness();
    }

    private int calculateFitness() {
        // Ensures fitness is not too high if the snake gets really long
        if (this.getScore() < 10) {
            return this.moves * this.moves * (int) Math.pow(2, this.getScore());
        } else {
            return this.moves * this.moves * (int) Math.pow(2, 10) * (this.getScore() - 9);
        }
    }
}
