package Bufor;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {
    private final Buffer buffer;
    private int idx;
    private final int N;

    public Consumer(Buffer buffer, int finalValue, int N) {
        this.buffer = buffer;
        this.idx = 0;
        this.N = N;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                idx = buffer.consume(this.idx);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}
