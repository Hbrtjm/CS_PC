package counter;

public class Incrementor extends Thread {
    private Counter counter;

    public Incrementor(Counter c) {
        this.counter = c;
    }

    public void run() {
        for(int i = 0; i < Counter.DEFAULT_COUNT; i++) {
            counter.increment();
        }
    }
}
