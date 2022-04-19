public class GameAI {
    private final Game[] games = new Game[Constants.STARTING_POPULATION];
    private final SnakeAI[] snakeAIs = new SnakeAI[Constants.STARTING_POPULATION];
    public int generation;

    GameAI() {
        this.generation = 0;
        this.init();
    }

    private void init() {
        this.generation += 1;

        for (int i = 0; i < this.games.length; i++) {
            this.games[i] = new Game();
            this.snakeAIs[i] = new SnakeAI(this.games[i], null, null);
        }
    }

    public void run() {
        for (int i = 0; i < this.games.length; i++) {
            Game game = this.games[i];
            if (game.isRunning) {
                this.snakeAIs[i].performAction();
                game.run();
            } else if (this.snakeAIs[i].isAlive) {
                this.snakeAIs[i].end();
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
        this.end();
        this.init();
    }

    private void end() {
        double[] stats = this.getGenerationStats();
        System.out.println("Best score for generation " + this.generation + ": " + stats[0]);
        System.out.println("Average score for generation " + this.generation + ": " + stats[1]);
        System.out.println();
    }

    /**
     * Returns the best score and the average score of the generation
     */
    private double[] getGenerationStats() {
        int bestScore = 0;
        int sumOfScores = 0;

        for (SnakeAI snake : this.snakeAIs) {
            int score = snake.getScore();
            sumOfScores += score;

            if (score > bestScore) {
                bestScore = score;
            }
        }

        double averageScore = sumOfScores / (double) this.snakeAIs.length;

        return new double[] {bestScore, averageScore};
    }
}
