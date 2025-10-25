package utils;

public class CounterSemaphore {
    private int count;

    public CounterSemaphore(int initialCount) {
        if (initialCount < 0) {
            throw new IllegalArgumentException("initialCount < 0");
        }
        this.count = initialCount;
    }

    public synchronized void acquire() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        count--;
    }

    public synchronized void release() {
        count++;
        if (count > 0) {
            notify();
        }
    }
}
