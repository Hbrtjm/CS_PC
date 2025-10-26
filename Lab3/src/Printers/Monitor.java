package Printers;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor {
    private Set<Printer> availablePrinters = new HashSet<>();
    private Lock lock = new ReentrantLock();
    private Condition printerAvailable = lock.newCondition();

    public Monitor(int M) {
        for(int i = 0; i < M; i++) {
            availablePrinters.add(new Printer(i));
        }
    }

    public Printer reserve() {
        lock.lock();
        try {
            while (availablePrinters.isEmpty()) {
                printerAvailable.await();
            }
            Iterator<Printer> iterator = availablePrinters.iterator();
            Printer reservedPrinter = iterator.next();
            availablePrinters.remove(reservedPrinter);
            System.out.println("Thread " + Thread.currentThread().getName() + " reserved printer " + reservedPrinter.id);
            return reservedPrinter;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for printer: " + e.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void release(Printer printer) {
        lock.lock();
        try {
            availablePrinters.add(printer);
            System.out.println("Thread " + Thread.currentThread().getName() + " released printer " + printer.id);
            printerAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
