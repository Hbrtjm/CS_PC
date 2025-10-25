package Printers;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<User> userList = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    public Simulation(int N, int M) {
        Monitor monitor = new Monitor(M);

        for(int i = 0; i < N; i++) {
            this.userList.add(new User(monitor));
        }
    }

    private void run() {
        for(User u : this.userList) {
            Thread t = new Thread(u, u.toString());
            threads.add(t);
            t.start();
        }
    }

    public static void main(String[] args) {
        new Simulation(3, 1).run();
    }
 }
