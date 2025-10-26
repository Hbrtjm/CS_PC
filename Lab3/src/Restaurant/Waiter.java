package Restaurant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.HashMap;
import java.util.Map;

public class Waiter {
    private Lock lock = new ReentrantLock();
    private final Condition tableAvailable = lock.newCondition();
    
    private final Map<Integer, Integer> waitingPairs = new HashMap<>();
    private boolean tableOccupied = false;
    private int currentPairId = -1;
    
    public void wantTable(int pairId) {
        lock.lock();
        try {
            waitingPairs.put(pairId, waitingPairs.getOrDefault(pairId, 0) + 1);
            
            System.out.println("Person from pair " + pairId + " wants table. Waiting count: " + waitingPairs.get(pairId));
            
            while (tableOccupied || waitingPairs.getOrDefault(pairId, 0) < 2) {
                tableAvailable.await();
            }

            if (!tableOccupied && waitingPairs.getOrDefault(pairId, 0) == 2) {
                tableOccupied = true;
                currentPairId = pairId;
                waitingPairs.remove(pairId);
                System.out.println("Pair " + pairId + " got the table");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
    
    public void freeUp() {
        lock.lock();
        try {
            if (tableOccupied) {
                System.out.println("Person from pair " + currentPairId + " is freeing up the table");
                tableOccupied = false;
                currentPairId = -1;
                tableAvailable.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
