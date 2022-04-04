public class Cell {
    private final Position position;
    private boolean isApple = false;
    private boolean isSnake = false;

    Cell(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean isApple() {
        return this.isApple;
    }

    public void setApple(boolean isApple) {
        this.isApple = isApple;
    }

    public boolean isSnake() {
        return this.isSnake;
    }

    public void setSnake(boolean isSnake) {
        this.isSnake = isSnake;
    }
}
