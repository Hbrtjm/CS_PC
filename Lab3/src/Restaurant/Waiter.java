package Restaurant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.HashMap;
import java.util.Map;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private final Condition tableAvailable = lock.newCondition();
    private final Table table;
    private final Map<Integer, Integer> waitingPairs = new HashMap<>();
    private boolean tableOccupied = false;
    private int currentPairId = -1;

    public Waiter() {
        table = new Table();
    }

    public void wantTable(int pairId, int personId) {
        lock.lock();
        try {
            waitingPairs.put(pairId, waitingPairs.getOrDefault(pairId, 0) + 1);
            
            System.out.println("Person " + personId + "  from pair " + pairId + " wants table. Waiting count: " + waitingPairs.get(pairId));

            while (currentPairId != pairId && (tableOccupied || waitingPairs.getOrDefault(pairId, 0) < 2)) {
                tableAvailable.await();
            }

            if (!tableOccupied && waitingPairs.getOrDefault(pairId, 0) >= 2 && currentPairId == -1) {
                tableOccupied = true;
                currentPairId = pairId;
                table.reserveLeftSeat();
                table.reserveRightSeat();
                waitingPairs.remove(pairId);
                tableAvailable.signalAll();
                System.out.println("Pair " + pairId + " got the table");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
    
    public void freeUp(int personId) {
        System.out.println("Person " + personId + " from pair " + currentPairId + " is freeing up the table");
        lock.lock();
        if(personId == 1) {
            this.table.freeRightSeat();
        } else {
            this.table.freeLeftSeat();
        }
        try {
            if (this.table.checkAvailable()) {
                tableOccupied = false;
                currentPairId = -1;
                tableAvailable.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
