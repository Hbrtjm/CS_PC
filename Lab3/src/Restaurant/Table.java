package Restaurant;

public class Table {
    private boolean leftFree = true;
    private boolean rightFree = true;

    public boolean checkAvailable()  {
        return rightFree && leftFree;
    }

    public void freeLeftSeat() {
        this.leftFree = true;
    }

    public void freeRightSeat() {
        this.rightFree = true;
    }

    public void reserveLeftSeat() {
        this.leftFree = false;
    }

    public void reserveRightSeat() {
        this.rightFree = false;
    }
}
