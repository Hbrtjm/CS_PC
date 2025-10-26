package Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<ClientPair> clients = new ArrayList<>();
    private Waiter waiter;
    
    public Simulation(int N) {
        this.waiter = new Waiter();
        for(int i = 0; i < N; i++) {
            clients.add(new ClientPair(i, 1, this.waiter));
            clients.add(new ClientPair(i, 2, this.waiter));
        }
    }
    
    public void start() {
        for(ClientPair client : clients) {
            client.start();
        }
    }
    
    public static void main(String[] args) {
        int numberOfPairs = 3;
        Simulation simulation = new Simulation(numberOfPairs);
        simulation.start();
    }
}
