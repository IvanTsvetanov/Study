package Q3;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

public class Belt extends CyclicQueue {

    public Belt(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void enqueue(int enteredNumber) {
        if ((tailPointer + 1) % queue.length == headPointer) {
            try {
                wait();
            }
        catch (Exception e)  {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } }
        else {
            try {
                tailPointer = (tailPointer + 1) % queue.length;
                queue[tailPointer] = enteredNumber;
                //notifyAll();
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    @Override
    public synchronized int dequeue() {
        int toBeRemoved = 0;
        try {
            if (headPointer == tailPointer) {
                wait();
                return 0;
            }
            // Checks if we can delete an element.
            // The check is done by looking if the pointer will overwrite unprocessed data.
            else {
                headPointer = (headPointer + 1) % queue.length;
                toBeRemoved = queue[headPointer];
                return toBeRemoved;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
