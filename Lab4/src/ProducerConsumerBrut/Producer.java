package ProducerConsumerBrut;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {
    private final Buffer buffer;
    private final int M;
    private String fileName;
    private final Simulation sim;

    public Producer(Buffer buffer, int M, Simulation sim) {
        this.buffer = buffer;
        this.M = M;
        this.sim = sim;
        fileName = sim.getId() + "_producers_time_brut.csv";
    }

    public void run() {
        while (sim.isRunning()) {
            produce();
        }
    }

    private void produce() {
        int amount = ThreadLocalRandom.current().nextInt(1, M + 1);
        long start = System.nanoTime();
        buffer.put(amount);
        long end = System.nanoTime();
        long latency = end - start;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(amount + ";" + latency + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
