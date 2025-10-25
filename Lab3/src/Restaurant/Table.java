package Restaurant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    Lock lock = new ReentrantLock();
    public Table()
}
