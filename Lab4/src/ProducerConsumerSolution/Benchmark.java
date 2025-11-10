package ProducerConsumerSolution;

public class Benchmark {

    private static final long RUN_DURATION_MS = 10_000;

    public static void main(String[] args) {
        int[][] parameterSets = {
                {1000, 10, 10},
                {10_000, 100, 100},
                {100_000, 1000, 1000}
        };

        for (int i = 0; i < parameterSets.length; i++) {
            int M = parameterSets[i][0];
            int P = parameterSets[i][1];
            int C = parameterSets[i][2];

            System.out.printf("M=%d, P=%d, C=%d%n", M, P, C);

            Simulation sim = new Simulation(i, M, P, C);
            sim.run();

            try {
                Thread.sleep(RUN_DURATION_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            sim.stop();
            System.out.printf("Finished simulation %d stopped after %d ms%n%n", i + 1, RUN_DURATION_MS);
        }
}
}

