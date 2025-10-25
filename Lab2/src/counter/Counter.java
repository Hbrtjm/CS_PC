package counter;

import utils.BinarySemaphore;

public class Counter {
    private int count = 0;

    public static int DEFAULT_COUNT = 10000;

    private final BinarySemaphore semaphore;

    public Counter() {
        this.semaphore = new BinarySemaphore(true);
    }

    public void decrement() {
        try{
            this.semaphore.acquire();
            count--;
        } catch  (InterruptedException e) {
            System.out.println(e.getMessage());
        } 
        finally {
            this.semaphore.release();
        }
    }
    
    public void increment() {
        try { 
            this.semaphore.acquire();
            count++;
        } catch  (InterruptedException e) {
            System.out.println(e.getMessage());
        } 
        finally {
            this.semaphore.release();
        }
    }

    public int getCounter() {
        return count;
    }

}
