package Restaurant;

public class ClientPair {
    Waiter waiter;
    int id;

    public ClientPair(int id, Waiter waiter) {
        this.waiter = waiter;
        this.id = id;
    }

    public void reserveTable() {
        waiter.reserveTable();
    }

    public void eat() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ownBusiness() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
