package ProducerConsumerSolution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {
    private final Buffer buffer;
    private final int M;
    private String fileName;
    private final Simulation sim;
    private final int id;

    public Consumer(Buffer buffer, int M, Simulation sim, int id) {
        this.buffer = buffer;
        this.M = M;
        this.sim = sim;
        this.id = id;
        fileName = sim.getId() + "_producers_time_solution.csv";
    }

    public void run() {
        while (sim.isRunning()) {
            consume();
        }
    }

    private void consume() {
        int amount = ThreadLocalRandom.current().nextInt(1, M + 1);
        long start = System.nanoTime();
        buffer.take(amount, id);
        long end = System.nanoTime();
        long latency = end - start;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(amount + ";" + latency + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
