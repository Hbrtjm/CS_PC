package Bufor;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {
    private final Buffer buffer;
    private final int id;
    private int idx = 0;
    private final int N;

    public Producer(Buffer buffer, int id, int N) {
        this.buffer = buffer;
        this.id = id;
        this.N = N;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                idx = buffer.produce(this.idx);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}
