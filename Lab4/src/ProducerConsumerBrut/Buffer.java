package ProducerConsumerBrut;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Buffer {
    private final List<String> elements = new LinkedList<>();
    private final Condition producerCond;
    private final Condition consumerCond;
    private final Lock lock = new ReentrantLock();
    private final int maxSize;

    public Buffer(int maxPCThreads) {
        this.producerCond = lock.newCondition();
        this.consumerCond = lock.newCondition();
        this.maxSize = 2 * maxPCThreads;
    }

    public void take(int amount) {
        lock.lock();
        try {
            while (elements.size() < amount) {
                try {
                    consumerCond.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            for (int i = 0; i < amount; i++) {
                elements.remove(0);
            }
            producerCond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void put(int amount) {
        lock.lock();
        try {
            while (elements.size() + amount > maxSize) {
                try {
                    producerCond.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            for (int i = 0; i < amount; i++) {
                elements.add(".");
            }
            consumerCond.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
