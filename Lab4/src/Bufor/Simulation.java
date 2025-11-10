package Bufor;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private int N = 10;
    private int P = 5;

    private final Consumer consumer;
    private final Producer producer;
    private final List<Processor> processorList = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();

    public Simulation() {
        Buffer bufor = new Buffer(N, P);

        producer = new Producer(bufor, 0, this.N);
        for (int i = 1; i <= P; i++) {
            processorList.add(new Processor(bufor, i, this.N));
        }
        consumer = new Consumer(bufor, P, this.N);
    }

    private void run() {
        Thread t = producer;
        t.setName("Producer");
        threads.add(t);
        t.start();
        for (int i = 0; i < processorList.size(); i++) {
            t = processorList.get(i);
            t.setName("Processor-" + (i + 1));
            threads.add(t);
            t.start();
        }
        t = consumer;
        t.setName("Consumer");
        threads.add(t);
        t.start();
    }

    public static void main(String[] args) {
        new Simulation().run();
    }
}
