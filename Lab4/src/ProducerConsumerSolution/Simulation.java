package ProducerConsumerSolution;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int M;
    private final int P;
    private final int C;
    private final int ID;

    private final Buffer buffer;
    private final List<Producer> producers = new ArrayList<>();
    private final List<Consumer> consumers = new ArrayList<>();

    private volatile boolean running = true;

    public Simulation() {
        this(0, 1000, 10, 10);
    }

    public Simulation(int id, int M, int P, int C) {
        this.M = M;
        this.P = P;
        this.C = C;
        this.ID = id;
        this.buffer = new Buffer(2 * M);

        for (int i = 0; i < P; i++) producers.add(new Producer(buffer, M, this, i));
        for (int i = 0; i < C; i++) consumers.add(new Consumer(buffer, M, this, i));
    }

    public void run() {
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);
    }

    public void stop() {
        running = false;
        producers.forEach(Thread::interrupt);
        consumers.forEach(Thread::interrupt);
    }

    public boolean isRunning() {
        return running;
    }

    public int getId() {
        return ID;
    }

    public static void main(String[] args) {
        if (args.length >= 3) {
            int M = Integer.parseInt(args[0]);
            int C = Integer.parseInt(args[1]);
            int P = Integer.parseInt(args[2]);
            int ID = Integer.parseInt(args[3]);
            Simulation sim = new Simulation(ID, M, C, P);
            sim.run();
        } else {
            Simulation sim = new Simulation();
            sim.run();
        }
    }
}
