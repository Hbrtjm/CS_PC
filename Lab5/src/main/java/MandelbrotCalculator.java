import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class MandelbrotCalculator implements Callable<Void> {
    private final int x0, x1, y0, y1, width, height, maxIter;
    private final double zoom;
    private final BufferedImage img;
    private final boolean render;

    public MandelbrotCalculator(Int4 rect, double zoom, int maxIter, BufferedImage img, int width, int height, boolean render) {
        this.x0 = rect.x0(); this.y0 = rect.y0();
        this.x1 = rect.x1(); this.y1 = rect.y1();
        this.zoom = zoom;
        this.maxIter = maxIter;
        this.img = img;
        this.width = width;
        this.height = height;
        this.render = render;
    }

    @Override
    public Void call() {
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                double zx = 0.0, zy = 0.0;
                double cX = (x - width / 2.0) / zoom;
                double cY = (y - height / 2.0) / zoom;

                int iter = maxIter;
                while (zx * zx + zy * zy < 4.0 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                if (render) {
                    img.setRGB(x, y, iter | (iter << 8));
                }
            }
        }
        return null;
    }
}
