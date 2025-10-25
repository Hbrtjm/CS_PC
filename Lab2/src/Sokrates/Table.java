package Sokrates;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Fork> forks = new ArrayList<>();
    private final List<Philosopher> philosophers = new ArrayList<>();
    private final Supervisor supervisor;

    public Table(int amount) {
        this.supervisor = new Supervisor(amount - 1);
        setTable(amount);
    }

    private void setTable(int amount) {
        forks.clear();
        philosophers.clear();

        for (int i = 0; i < amount; i++) {
            forks.add(new Fork());
        }
        for (int i = 0; i < amount; i++) {
            Fork left  = forks.get(i);
            Fork right = forks.get((i + 1) % amount);
            philosophers.add(new Philosopher(i, left, right, supervisor));
        }
    }

    public List<Philosopher> philosophers() { return philosophers; }
}
