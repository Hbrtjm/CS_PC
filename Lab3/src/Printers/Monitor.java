package Printers;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Set<Printer> printersSet = new HashSet<>();
    Lock lock = new ReentrantLock();

    public Monitor(int M) {
        for(int i = 0;i < M;i++) {
            printersSet.add(new Printer(i));
        }
    }

    synchronized public Printer reserve() {
        if (lock.tryLock()) {
            lock.lock();
            Printer reservedPrinter = new Printer(-1);
            Iterator<Printer> iterator = printersSet.iterator();
            try {
                while (!iterator.hasNext()) {
                    lock.wait();
                }
                reservedPrinter = iterator.next();
                printersSet.remove(reservedPrinter);
            } catch (InterruptedException e) {
                System.out.println("Interrupted" + e.getMessage());
            } finally {
                lock.unlock();
            }
            return reservedPrinter;
        }
    }

    synchronized public void release(Printer reservedPrinter) {
        lock.lock();
        try {
            printersSet.add(reservedPrinter);
            lock.notifyAll();
        }
        finally {
            lock.unlock();
        }
    }
}
