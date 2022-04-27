package src.genetic_algorithm;

import src.ai.SnakeAI;
import src.game.Game;

import java.util.Random;


/**
 * This class is responsible for most of the genetic algorithm of the program.
 * It creates a new generation of snakes based on the previous generation.
 */
public class Generation {
    public SnakeAI bestSnake;

    private final SnakeAI[] snakes;
    private double bestScore;
    private double averageScore;

    public Generation(SnakeAI[] snakes) {
        this.snakes = snakes;
        this.calculateStats();
        this.setBestSnake();
    }

    /**
     * Calculates the best score and the average score of the generation
     */
    private void calculateStats() {
        double bestScore = 0;
        int sumOfScores = 0;

        for (SnakeAI snake : this.snakes) {
            double score = snake.getScore();
            sumOfScores += score;

            if (score > bestScore) {
                bestScore = score;
            }
        }

        double averageScore = sumOfScores / (double) this.snakes.length;

        this.bestScore = bestScore;
        this.averageScore = averageScore;
    }

    public void setBestSnake() {
        SnakeAI bestSnake = this.snakes[0];
        for (SnakeAI snake : this.snakes) {
            if (snake.getScore() > bestSnake.getScore()) {
                bestSnake = snake;
            }
        }

        this.bestSnake = bestSnake;
    }

    public double[] getStats() {
        return new double[] {this.bestScore, this.averageScore};
    }

    public SnakeAI[] createNewGeneration() {
        SnakeAI[] babies = new SnakeAI[this.snakes.length];
        for (int i = 0; i < babies.length; i++) {
            SnakeAI[] parents = this.selectParents();
            babies[i] = this.crossover(parents);
        }

        return babies;
    }

    private SnakeAI[] selectParents() {
        SnakeAI parent1 = this.selectParent();
        SnakeAI parent2 = this.selectParent();
        return new SnakeAI[] {parent1, parent2};
    }

    /**
     * Selects a parent at random. Snakes that have a higher fitness have a higher
     * chance of getting selected. This provides variation to the next generation
     * of snakes to prevent the snakes from becoming too similar and not improve.
     */
    private SnakeAI selectParent() {
        int fitnessSum = 0;
        for (SnakeAI snake : this.snakes) {
            fitnessSum += snake.getFitness();
        }

        // If the fitness sum is less than 1, this will make sure nextInt() doesn't throw an error
        int rand = new Random().nextInt(Math.max(fitnessSum, 1));

        int currentSum = 0;

        for (SnakeAI snake : this.snakes) {
            currentSum += snake.getFitness();
            if (currentSum > rand) {
                return snake;
            }
        }

        // This will never actually be reached. It's here to please the compiler.
        return this.snakes[0];
    }

    /**
     * Genes are selected at random from each of the two parents.
     * Those genes are then assigned to the child.
     */
    private SnakeAI crossover(SnakeAI[] parents) {
        double[][] newHiddenWeights = new double[parents[0].hiddenLayerWeights.length][parents[0].hiddenLayerWeights[0].length];
        for (int i = 0; i < newHiddenWeights.length; i++) {
            for (int j = 0; j < newHiddenWeights[i].length; j++) {
                int rand = new Random().nextInt(2);
                newHiddenWeights[i][j] = parents[rand].hiddenLayerWeights[i][j];
            }
        }

        double[][] newOutputWeights = new double[parents[0].outputLayerWeights.length][parents[0].outputLayerWeights[0].length];
        for (int i = 0; i < newOutputWeights.length; i++) {
            for (int j = 0; j < newOutputWeights[i].length; j++) {
                int rand = new Random().nextInt(2);
                newOutputWeights[i][j] = parents[rand].outputLayerWeights[i][j];
            }
        }

        return new SnakeAI(new Game(), newHiddenWeights, newOutputWeights);
    }
}
