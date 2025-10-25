package Diogenes;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public int id() { return id; }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        try {
            leftFork.acquire();
            System.out.println("Philosopher " + id + " is picked up left fork");
            try {
                rightFork.acquire();
                System.out.println("Philosopher " + id + " is picked up right fork");
                System.out.println("Philosopher " + id + " is eating");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                System.out.println("Philosopher " + id + " finished eating");
            } finally {
                rightFork.release();
            }
        } finally {
            leftFork.release();
        }
    }

    @Override
    public String toString() {
        return "Philosopher-" + id;
    }
}
