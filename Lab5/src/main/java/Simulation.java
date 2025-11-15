import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Simulation {
    private final int threadCount;
    private final int taskCount;
    private final int maxIter;
    private final int height;
    private final int width;
    private final double zoom;
    private final boolean render;
    private final BufferedImage image;

    public Simulation(int threadCount, int taskCount, int maxIter, int height, int width, double zoom, boolean render) {
        this.threadCount = threadCount;
        this.taskCount = taskCount;
        this.maxIter = maxIter;
        this.height = height;
        this.width = width;
        this.zoom = zoom;
        this.render = render;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void run() {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        System.out.println("Simulation run started: threads=" + threadCount + ", tasks=" + taskCount);
        List<Future<Void>> futures = new ArrayList<>();

        if (taskCount != (height * width)) {
            int totalTasks = taskCount;
            int step = Math.max(1, height / totalTasks);
            int y1 = 0;
            while (y1 < height) {
                int y2 = Math.min(y1 + step - 1, height - 1);

                MandelbrotCalculator worker =
                        new MandelbrotCalculator(
                                new Int4(0, y1, width - 1, y2),
                                zoom, maxIter, image, width, height, true
                        );

                futures.add(threadPool.submit(worker));
                y1 += step;
            }

        } else {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    MandelbrotCalculator worker =
                            new MandelbrotCalculator(
                                    new Int4(x, y, x, y),
                                    zoom, maxIter, image, width, height, true
                            );
                    futures.add(threadPool.submit(worker));
                }
            }
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                System.out.println("Execution exception: " + e.getMessage());
            }
        }
        threadPool.shutdown();
        if (render) {
            new MandelbrotFrame(image).render();
        }
    }
}
