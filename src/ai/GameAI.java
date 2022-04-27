package src.ai;

import src.game.Constants;
import src.game.Game;
import src.genetic_algorithm.Generation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


/**
 * This class is responsible for running the entire simulation. It keeps
 * track of all the snakes and games and updates them every frame.
 */
public class GameAI {
    public Generation generation;
    public int numGeneration;

    private final Game[] games = new Game[Constants.STARTING_POPULATION];
    private final SnakeAI[] snakeAIs = new SnakeAI[Constants.STARTING_POPULATION];
    private SnakeAI bestSnake;

    public GameAI() {
        this.generation = null;
        this.numGeneration = 0;
        this.loadBestSnake();
        this.init(null);
    }

    private void init(SnakeAI[] snakeAIs) {
        this.numGeneration++;

        for (int i = 0; i < this.games.length; i++) {
            if (snakeAIs == null) {
                this.games[i] = new Game();

                if (this.bestSnake == null) {
                    this.snakeAIs[i] = new SnakeAI(this.games[i]);
                } else {
                    this.snakeAIs[i] = new SnakeAI(this.games[i], this.bestSnake.hiddenLayerWeights, this.bestSnake.outputLayerWeights);
                }
            } else {
                // These snakes are the children from a previous generation
                this.snakeAIs[i] = snakeAIs[i].copy();
                this.games[i] = this.snakeAIs[i].getGame();
            }
        }
    }

    private void loadBestSnake() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("snakeData/snakeData.csv"));
            SnakeAI bestSnake = new SnakeAI(new Game());
            int i = 0;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");

                // Last line, so set the best snake's score
                if (values.length == 1) {
                    bestSnake.setScore(Integer.parseInt(values[0]));
                    break;
                }

                double[] weights = Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();

                if (i < Constants.NUM_HIDDEN) {
                    bestSnake.hiddenLayerWeights[i] = weights;
                } else {
                    // The lines are now output weights
                    bestSnake.outputLayerWeights[i - Constants.NUM_HIDDEN] = weights;
                }

                i++;
            }
            this.bestSnake = bestSnake;
        } catch (IOException e) {
            System.out.println("Snake Data file not found. Loading new snakes");
            this.bestSnake = null;
        }
    }

    /**
     * Saves the weights of the best snake into a file, so it can be loaded later
     */
    private void saveBestSnake() {
        try {
            // First make the directory if it doesn't exist
            if (!this.makeDirectory()) {
                throw new IOException("Error making Snake Data directory");
            }

            // Write the snake's weights to a file
            FileWriter writer = new FileWriter("snakeData/snakeData.csv");
            for (int i = 0; i < this.bestSnake.hiddenLayerWeights.length; i++) {
                for (int j = 0; j < this.bestSnake.hiddenLayerWeights[i].length; j++) {
                    writer.append(String.valueOf(this.bestSnake.hiddenLayerWeights[i][j])).append(" ");
                }
                writer.append("\n");
            }
            writer.append("\n");
            for (int i = 0; i < this.bestSnake.outputLayerWeights.length; i++) {
                for (int j = 0; j < this.bestSnake.outputLayerWeights[i].length; j++) {
                    writer.append(String.valueOf(this.bestSnake.outputLayerWeights[i][j])).append(" ");
                }
                writer.append("\n");
            }
            // Write score on last line
            writer.append((char) this.bestSnake.getScore());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving best snake");
            e.printStackTrace();
        }
    }

    private boolean makeDirectory() {
        File directory = new File("snakeData");
        if (!directory.exists()) {
            return directory.mkdir();
        }

        return true;
    }

    public void run() {
        for (int i = 0; i < this.games.length; i++) {
            Game game = this.games[i];
            if (game.isRunning) {
                this.snakeAIs[i].performAction();
                game.run();
            }
        }
    }

    /**
     * Returns a game that is running so the GamePanel knows which game to display
     */
    public Game getActiveGame() {
        for (Game game : this.games) {
            if (game.isRunning) {
                return game;
            }
        }

        // If no games are running, create a new generation
        this.restart();
        return this.games[0];
    }

    private void restart() {
        this.endSnakes();
        this.generation = new Generation(this.snakeAIs);
        this.end();

        if (this.bestSnake == null || this.generation.bestSnake.getScore() > this.bestSnake.getScore()) {
            this.bestSnake = this.generation.bestSnake.copy();
            this.saveBestSnake();
        }

        SnakeAI[] snakeAIs = this.generation.createNewGeneration();
        this.init(snakeAIs);
    }

    private void endSnakes() {
        for (SnakeAI snake : this.snakeAIs) {
            snake.end();
        }
    }

    private void end() {
        double[] stats = this.generation.getStats();
        System.out.println("Best score for generation " + this.numGeneration + ": " + stats[0]);
        System.out.println("Average score for generation " + this.numGeneration + ": " + stats[1]);
        System.out.println();
    }
}
