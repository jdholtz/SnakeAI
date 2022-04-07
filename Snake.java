public class Snake {
    private final Position headPos;
    public final Position[] body;
    public int direction = Constants.DIRECTION_RIGHT;

    Snake() {
        this.body = new Position[1];
        this.body[0] = new Position(0, 0);
        this.headPos = this.body[0];
    }

    public void move() {
        for (int i = 0; i < this.body.length - 1; i++) {
            this.body[i] = this.body[i + 1];
        }

        switch (this.direction) {
            case Constants.DIRECTION_UP:
                this.headPos.setY(this.headPos.getY() - 1);
                break;
            case Constants.DIRECTION_RIGHT:
                this.headPos.setX(this.headPos.getX() + 1);
                break;
            case Constants.DIRECTION_DOWN:
                this.headPos.setY(this.headPos.getY() + 1);
                break;
            case Constants.DIRECTION_LEFT:
                this.headPos.setX(this.headPos.getX() - 1);
                break;
            default:
                throw new Error("Invalid direction in move() function");
        }

        this.body[this.body.length - 1] = this.headPos;
    }
}
