import counter.Counter;
import counter.Decrementor;
import counter.Incrementor;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running task 1");
        Counter counter = new Counter();
        Decrementor decrementor = new Decrementor(counter);
        Incrementor incrementor = new Incrementor(counter);
        decrementor.start();
        incrementor.start();
        try {
            decrementor.join();
            incrementor.join();

            System.out.println(counter.getCounter());
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        decrementor.interrupt();
        incrementor.interrupt();
    }
}