package Platon;

// Zadanie 2 - nieparzysty filozof bierze najpierw lewy widelec

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final Table table;
    private final List<Thread> threads = new ArrayList<>();

    public Simulation(int philosophers) {
        this.table = new Table(philosophers);
    }

    public void run() {
        for (Philosopher p : table.philosophers()) {
            Thread t = new Thread(p, p.toString());
            threads.add(t);
            t.start();
        }
    }

    public static void main(String[] args) {
        new Simulation(5).run();
    }
}
