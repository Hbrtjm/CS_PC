package Task2;

public class CounterSync {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    synchronized public void increment() {
        counter++;
    }

    synchronized public void decrement() {
        counter--;
    }
}
