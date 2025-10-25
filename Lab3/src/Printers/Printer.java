package Printers;

import java.util.concurrent.ThreadLocalRandom;

public class Printer {
    int id;

    public Printer(int id) {
        this.id = id;
    }

    public void print(String value){
        System.out.println(value);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
