import Task1.Decrementor;
import Task1.Incrementor;
import Task1.Counter;

import Task2.CounterSync;
import Task2.DecrementorSync;
import Task2.IncrementorSync;

import Task3.Consumer;
import Task3.Producer;
import Task3.Buffer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running task 1");
        Counter counter = new Counter();
        Decrementor decrementor = new Decrementor(counter);
        Incrementor incrementor = new Incrementor(counter);
        decrementor.start();
        incrementor.start();
        try {
            decrementor.join();
            incrementor.join();

            System.out.println(counter.getCounter());
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        decrementor.interrupt();
        incrementor.interrupt();

        System.out.println("Running task 2");

        CounterSync counterSync = new CounterSync();
        DecrementorSync syncDecrementor = new DecrementorSync(counterSync);
        IncrementorSync syncIncrementor = new IncrementorSync(counterSync);
        syncDecrementor.start();
        syncIncrementor.start();
        try {
            syncDecrementor.join();
            syncIncrementor.join();

            System.out.println(counterSync.getCounter());
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        syncDecrementor.interrupt();
        syncIncrementor.interrupt();

        System.out.println("Running task 3");

        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Producer producer2 = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);

        Thread producerThread = new Thread(producer);
        Thread producerThread2 = new Thread(producer2);
        Thread consumerThread = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer2);
        producerThread.start();
        producerThread2.start();
        consumerThread.start();
        consumerThread2.start();
        try {
            producerThread.join();
            producerThread2.join();
            consumerThread.join();
            consumerThread2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}