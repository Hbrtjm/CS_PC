package Sokrates;

import utils.BinarySemaphore;

public class Fork {
    private final BinarySemaphore semaphore = new BinarySemaphore(true);

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }
}
