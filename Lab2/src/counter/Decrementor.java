package counter;

public class Decrementor extends Thread {
    private Counter counter;

    public Decrementor(Counter c) {
        this.counter = c;
    }

    public void run() {
        for(int i = 0; i < Counter.DEFAULT_COUNT; i++) {
            counter.decrement();
        }
    }
}
