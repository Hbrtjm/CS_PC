package Task2;

public class IncrementorSync extends Thread {
    CounterSync counter;

    public IncrementorSync(CounterSync counter) {
        this.counter = counter;
    }

    public void run() {
        for(int i = 0;i < 1000;i++)
        {
            counter.increment();
        }
    }
}
