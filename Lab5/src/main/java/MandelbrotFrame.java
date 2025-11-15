import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MandelbrotFrame extends JFrame {

    private final BufferedImage image;

    public MandelbrotFrame(BufferedImage image) {
        super("Mandelbrot");
        this.image = image;

        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

    public void render() {
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
