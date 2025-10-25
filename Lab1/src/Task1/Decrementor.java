package Task1;

public class Decrementor extends Thread {
    Counter counter;

    public Decrementor(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for(int i = 0;i < 1000;i++)
        {
                counter.decrement();
        }
    }
}
