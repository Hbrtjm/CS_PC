package Sokrates;

import utils.CounterSemaphore;

public class Supervisor {
    CounterSemaphore counterSemaphore;

    public Supervisor(int philosophers) {
        this.counterSemaphore = new CounterSemaphore(philosophers);
    }

    synchronized public void acquire() throws InterruptedException {
        this.counterSemaphore.acquire();
    }

    synchronized public void release() {
        this.counterSemaphore.release();
    }
}
