package Printers;

import java.util.concurrent.ThreadLocalRandom;

public class User extends Thread {
    Monitor monitor;
    Printer printer;
    String task;
    int id;

    public User(Monitor monitor, int id) {
        this.monitor = monitor; this.id = id;
    }

    @Override
    public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                generateTask();
                ThreadLocalRandom.current().nextInt(100, 500);
                reservePrinter();
                print();
                releasePrinter();
            }
    }

    // Source: https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
    private static String generateString(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = ThreadLocalRandom.current().nextInt(letters.length());
            sb.append(letters.charAt(idx));
        }
        return sb.toString();
    }

    private void generateTask() {
        this.task = "User-" + this.id + " " + generateString(ThreadLocalRandom.current().nextInt(20));
    }

    private void print() {
        this.printer.print(task);
    }

    public void reservePrinter() {
        this.printer = this.monitor.reserve();
    }

    public void releasePrinter() {
        this.monitor.release(this.printer);
    }
}
