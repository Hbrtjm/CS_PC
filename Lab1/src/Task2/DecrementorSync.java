package Task2;

public class DecrementorSync extends Thread {
    CounterSync counter;

    public DecrementorSync(CounterSync counter) {
        this.counter = counter;
    }

    public void run() {
        for(int i = 0;i < 1000;i++)
        {
                counter.decrement();
        }
    }
}
