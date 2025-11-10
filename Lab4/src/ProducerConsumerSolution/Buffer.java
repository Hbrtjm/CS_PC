package ProducerConsumerSolution;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Buffer {
    private final List<String> elements = new LinkedList<>();
    private final Condition producerCond;
    private final Condition consumerCond;
    private final Condition consumerGateCond;
    private final Condition producerGateCond;
    private final ReentrantLock lock = new ReentrantLock();
    private final int maxSize;

    public Buffer(int maxPCThreads) {
        this.producerCond = lock.newCondition();
        this.producerGateCond = lock.newCondition();
        this.consumerCond = lock.newCondition();
        this.consumerGateCond = lock.newCondition();
        this.maxSize = 2 * maxPCThreads;
    }

    public void take(int amount, int consumerId) {
        lock.lock();
        boolean waitedAtGate = false;
        try {
            while (lock.hasWaiters(consumerCond)) {
                waitedAtGate = true;
                consumerGateCond.await();
            }
            while (elements.size() < amount) {
                consumerCond.await();
            }
            for (int i = 0; i < amount; i++) {
                elements.remove(0);
            }
            producerCond.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (waitedAtGate) consumerGateCond.signal();
            lock.unlock();
        }
    }

    public void put(int amount, int producerId) {
        lock.lock();
        boolean waitedAtGate = false;
        try {
            while (lock.hasWaiters(producerCond)) {
                waitedAtGate = true;
                producerGateCond.await();
            }
            while (elements.size() + amount > maxSize) {
                producerCond.await();
            }
            for (int i = 0; i < amount; i++) {
                elements.add(".");
            }
            consumerCond.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (waitedAtGate) producerGateCond.signal();
            lock.unlock();
        }
    }
}
