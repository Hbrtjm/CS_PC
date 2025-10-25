package utils;

public class BinarySemaphore {
    private boolean available;

    public BinarySemaphore(boolean initiallyAvailable) {
        this.available = initiallyAvailable;
    }

    public synchronized void acquire() throws InterruptedException {
        while (!available) {
            wait();
        }
        available = false;
    }

    public synchronized void release() {
        if (!available) {
            available = true;
            notify();
        }
    }
}
