public class Snake {
    public Position[] body;
    public int direction = Constants.DIRECTION_UP;
    private int length;

    Snake(int numCellsOnSide) {
        // Rounds down if numCellsOnSide is odd
        int middleCell = numCellsOnSide / 2;

        this.length = 3;
        this.body = new Position[this.length];

        // Generate the starting snake in the middle of the screen
        for (int i = 0; i < this.length; i++) {
            this.body[i] = new Position(middleCell, middleCell + i);
        }
    }

    public void move() {
        for (int i = this.length - 1; i > 0; i--) {
            // A new position has to be created. Assigning directly would only make both
            // variables point to the same object, which creates problems
            Position pos = new Position(this.body[i - 1].getX(), this.body[i - 1].getY());
            this.body[i] = pos;
        }

        Position head = this.body[0];

        switch (this.direction) {
            case Constants.DIRECTION_UP:
                head.setY(head.getY() - 1);
                break;
            case Constants.DIRECTION_RIGHT:
                head.setX(head.getX() + 1);
                break;
            case Constants.DIRECTION_DOWN:
                head.setY(head.getY() + 1);
                break;
            case Constants.DIRECTION_LEFT:
                head.setX(head.getX() - 1);
                break;
            default:
                throw new Error("Invalid direction in move() function");
        }
    }

    public void grow() {
        // The snake body is copied to a larger array and the tail position is
        // duplicated at the end of the array. Therefore, the visible growth will
        // be seen next frame
        Position tailPos = this.body[this.length - 1];
        Position[] newBody = new Position[this.length + 1];

        for (int i = 0; i < this.length; i++) {
            newBody[i] = this.body[i];
        }

        newBody[newBody.length - 1] = tailPos;
        this.body = newBody;
        this.length += 1;
    }

    public int getLength() {
        return this.length;
    }
}
