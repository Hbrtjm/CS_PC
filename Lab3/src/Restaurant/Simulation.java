package Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    List<ClientPair> clientsPairs = new ArrayList<>();
    Waiter waiter;
    public Simulation(int N) {
        this.waiter = new Waiter();
        for(int i = 0; i < N;i++) {
            clientsPairs.add(new ClientPair(i, this.waiter));
        }
    }



}
