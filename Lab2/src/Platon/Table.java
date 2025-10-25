package Platon;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Fork> forks = new ArrayList<>();
    private final List<Philosopher> philosophers = new ArrayList<>();

    public Table(int amount) {
        setTable(amount);
    }

    public final void setTable(int amount) {
        forks.clear();
        philosophers.clear();

        for (int i = 0; i < amount; i++) {
            forks.add(new Fork());
        }
        for (int i = 0; i < amount; i++) {
            Fork left  = forks.get(i);
            Fork right = forks.get((i + 1) % amount);
            philosophers.add(new Philosopher(i, left, right));
        }
    }

    public List<Philosopher> philosophers() { return philosophers; }
}
