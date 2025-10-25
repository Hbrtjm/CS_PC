package Printers;

import java.util.concurrent.ThreadLocalRandom;

public class User extends Thread {
    Monitor monitor;
    Printer printer;
    String task;

    public User(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                generateTask();
                reservePrinter();
                print();
                releasePrinter();
            }
    }

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
        this.task = generateString(ThreadLocalRandom.current().nextInt(20));
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
