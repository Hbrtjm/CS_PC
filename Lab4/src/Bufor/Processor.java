package Bufor;

import java.util.concurrent.ThreadLocalRandom;

public class Processor extends Thread {
    private final Buffer buffer;
    private final int level;
    private int idx;
    private final int N;

    public Processor(Buffer buffer, int level, int N) {
        this.buffer = buffer;
        this.level = level;
        this.idx = 0;
        this.N = N;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                idx = buffer.process(idx, level);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}
