package Restaurant;

import java.util.concurrent.ThreadLocalRandom;

public class ClientPair extends Thread {
    private Waiter waiter;
    private int pairId;

    public ClientPair(int pairId, int personId, Waiter waiter) {
        this.pairId = pairId;
        this.waiter = waiter;
        this.setName("Pair-" + pairId + "-Person-" + personId);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                waking();
                waiter.wantTable(pairId);
                eat();
                waiter.freeUp();
                
            } catch (Exception e) {
                System.out.println("Exception in " + Thread.currentThread().getName() + ": " + e.getMessage());
                break;
            }
        }
    }

    private void eat() {
        try {
            int eatTime = ThreadLocalRandom.current().nextInt(1000, 3000);
            System.out.println(Thread.currentThread().getName() + " is eating for " + eatTime + "ms");
            Thread.sleep(eatTime);
            System.out.println(Thread.currentThread().getName() + " finished eating");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " interrupted while eating");
        }
    }

    private void waking() {
        try {
            int walingTime = ThreadLocalRandom.current().nextInt(500, 2000);
            System.out.println(Thread.currentThread().getName() + " walking for " + walingTime + "ms");
            Thread.sleep(walingTime);
            System.out.println(Thread.currentThread().getName() + " finished walking");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + e.getMessage());
        }
    }
}
