package Task3;

import java.util.LinkedList;
import java.util.List;

public class Buffer {
    List<String> Elements = new LinkedList<>();
    int MAX_SIZE = 3;

    synchronized public String take() {
        String element;
        // While jest potrzebne, ponieważ bufor mógł zostać opróżniony przez inny proces i przy wypuszczeniu z "wait()"
        // może brakować elementów
        while(Elements.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        element = Elements.removeFirst();
        notifyAll();
        return element;
    }

    synchronized public void put(String message) {
        // While jest potrzebne, ponieważ bufor mógł zostać zapełniony przez inny proces i przy wypuszczeniu z "wait()"
        // może wystąpić przepełnienie bufora
        while(Elements.size() == MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Elements.add(message);
        notifyAll();
    }
}