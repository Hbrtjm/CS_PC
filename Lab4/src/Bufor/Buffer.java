package Bufor;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int[] slots;
    private final boolean[] busy;
    private final int size;
    private final int finalLevel;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condFree;
    private final Condition[] condStage;
    private final Condition condDone;

    public Buffer(int size, int finalLevel) {
        this.size = size;
        this.finalLevel = finalLevel;
        this.slots = new int[size];
        this.busy  = new boolean[size];
        Arrays.fill(slots, -1);
        for (int i = 0; i < size; i++) slots[i] = -1;

        this.condFree  = lock.newCondition();
        this.condStage = new Condition[finalLevel];
        for (int i = 0; i < finalLevel; i++) {
            this.condStage[i] = lock.newCondition();
        }
        this.condDone  = lock.newCondition();
    }

    public int produce(int idx) throws InterruptedException {
        lock.lock();
        try {
            while (slots[idx] != -1 || busy[idx]) condFree.await();
            busy[idx] = true;
        } finally { lock.unlock(); }

        Thread.sleep(ThreadLocalRandom.current().nextInt(100, 500));
        this.printState();

        lock.lock();
        try {
            slots[idx] = 0;
            busy[idx] = false;
            condStage[0].signal();
            idx = (idx + 1) % size;
            System.out.print(" -> Prodooced ->");
            this.printState();
            System.out.println("");
            return idx;
        } finally { lock.unlock(); }
    }

    public int process(int idx, int level) throws InterruptedException {
        int want = level - 1;

        lock.lock();
        try {
            while (!(slots[idx] == want && !busy[idx])) {
                condStage[want].await();
            }
            busy[idx] = true;
        } finally { lock.unlock(); }

        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        this.printState();

        lock.lock();
        try {
            slots[idx] = level;
            busy[idx] = false;
            if (level == finalLevel) condDone.signal();
            else                      condStage[level].signal();
            idx = (idx + 1) % size;
            System.out.print(" -> Processed ->");
            this.printState();
            System.out.println("");
            return idx;
        } finally { lock.unlock(); }
    }


    public int consume(int idx) throws InterruptedException {
        lock.lock();
        try {
            while (!(slots[idx] == finalLevel && !busy[idx])) {
                condDone.await();
            }
            busy[idx] = true;
        } finally { lock.unlock(); }

        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        this.printState();

        lock.lock();
        try {
            slots[idx] = -1;
            busy[idx] = false;
            condFree.signal();
            idx = (idx + 1) % size;
            System.out.print(" -> Consoomed ->");
            this.printState();
            System.out.println("");
            return idx;
        } finally { lock.unlock(); }
    }

    public void printState() {
        System.out.print(Arrays.toString(slots));
    }

    public int size() { return size; }
    public int finalLevel() { return finalLevel; }
}
