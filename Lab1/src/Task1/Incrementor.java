package Task1;

public class Incrementor extends Thread {
    Counter counter;

    public Incrementor(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for(int i = 0;i < 1000;i++)
        {
            counter.increment();
        }
    }
}
