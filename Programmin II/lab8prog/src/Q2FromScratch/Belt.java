package Q2FromScratch;

public class Belt extends CyclicQueue {
    private Object lockA = new Object();
    private Object lockB = new Object();

    public Belt(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void enqueue(int enteredNumber) {
        while (read == (write + 1) % (elements.length)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        elements[write] = enteredNumber;
        write = (write + 1) % (elements.length);
        notifyAll();

    }

    @Override
    public synchronized int dequeue() {
        int r = 0;
        while (read == write) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        r = elements[read];
        read = (read + 1) % (elements.length);
        notifyAll();
        return r;

    }
}
