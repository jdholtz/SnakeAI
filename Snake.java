public class Snake {
    public Position[] body;
    public int direction = Constants.DIRECTION_RIGHT;

    Snake() {
        this.body = new Position[1];
        this.body[0] = new Position(0, 0);
    }

    public void move() {
        for (int i = this.body.length - 1; i > 0; i--) {
            Position pos = new Position(this.body[i - 1].getX(), this.body[i - 1].getY());
            this.body[i] = pos;
        }

        switch (this.direction) {
            case Constants.DIRECTION_UP:
                this.body[0].setY(this.body[0].getY() - 1);
                break;
            case Constants.DIRECTION_RIGHT:
                this.body[0].setX(this.body[0].getX() + 1);
                break;
            case Constants.DIRECTION_DOWN:
                this.body[0].setY(this.body[0].getY() + 1);
                break;
            case Constants.DIRECTION_LEFT:
                this.body[0].setX(this.body[0].getX() - 1);
                break;
            default:
                throw new Error("Invalid direction in move() function");
        }
    }

    public void grow() {
        Position tailPos = this.body[this.body.length - 1];
        Position[] newBody = new Position[this.body.length + 1];

        for (int i = 0; i < this.body.length; i++) {
            newBody[i] = this.body[i];
        }

        newBody[newBody.length - 1] = tailPos;
        this.body = newBody;
    }
}
