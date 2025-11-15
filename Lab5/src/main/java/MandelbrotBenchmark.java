import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MandelbrotBenchmark {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        final int RUNS = 10;
        final int WIDTH = 800;
        final int HEIGHT = 600;
        final int ZOOM = 150;
        final boolean RENDER = true;

        int cores = Runtime.getRuntime().availableProcessors();
        int[] threadAmounts = { 1, cores, 4 * cores };
        int[] maxIters = { 100, 200, 500, 1000, 2500, 5000, 10000 };

        List<Result> results = new ArrayList<>();

        for (int maxIter : maxIters) {
            for (int threads : threadAmounts) {
                int[] taskConfigs = { threads, 10 * threads, WIDTH * HEIGHT };

                for (int tasks : taskConfigs) {
                    ArrayList<Long> times = new ArrayList<>();
                    for (int i = 0; i < RUNS; i++) {
                        Simulation simulation = new Simulation(threads, tasks, maxIter, HEIGHT, WIDTH, ZOOM, RENDER);
                        long start = System.nanoTime();
                        simulation.run();
                        long end = System.nanoTime();
                        times.add(end - start);
                    }

                    double meanNs = mean(times);
                    double stdNs = dev(times, meanNs);
                    double meanMs = meanNs / 1_000_000.0;
                    double stdMs = stdNs / 1_000_000.0;
                    Result r = new Result(maxIter, threads, tasks, meanMs, stdMs);
                    results.add(r);
                }
            }
        }

        Result best = findFastest(results);
        printTable(results, best);
    }

    private static Result findFastest(List<Result> results) {
        Result best = null;
        for (Result r : results) {
            if (best == null || r.meanMs() < best.meanMs()) {
                best = r;
            }
        }
        return best;
    }

    private static void printTable(List<Result> results, Result best) {
        System.out.println();
        System.out.printf("%-8s %-8s %-12s %-12s %-12s%n",
                "MaxIter", "Threads", "Tasks", "Mean(ms)", "StdDev(ms)");
        System.out.println("--------------------------------------------------------------");

        for (Result r : results) {
            boolean fastest = (r == best);

            System.out.printf("%-8d %-8d %-12d %-12.3f %-12.3f%s%n",
                    r.maxIter(), r.threads(), r.pixelsPerThread(),
                    r.meanMs(), r.stdMs(),
                    fastest ? "  <-- BEST" : "");
        }

        System.out.println("\nBest config:");
        System.out.printf("MaxIter=%d, Threads=%d, Tasks=%d, Mean=%.3f ms, StdDev=%.3f ms%n",
                best.maxIter(), best.threads(), best.pixelsPerThread(),
                best.meanMs(), best.stdMs());
    }

    private static double mean(List<Long> values) {
        double sum = 0;
        for (long v : values)
            sum += v;
        return sum / values.size();
    }

    private static double dev(List<Long> values, double mean) {
        double sumSq = 0;
        for (long v : values)
            sumSq += (v - mean) * (v - mean);
        return Math.sqrt(sumSq / values.size());
    }
}
