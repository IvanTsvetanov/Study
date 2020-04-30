package Q3;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

public class Belt extends CyclicQueue {

    public Belt(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void enqueue(int enteredNumber) {
        try {
            if ((tailPointer + 1) % queue.length == headPointer) {
                Thread.sleep(2000);
                enqueue(enteredNumber);
            }
            // Checks if we can enter an element.
            // The check is done by looking if the pointer will overwrite unprocessed data.
            if ((tailPointer + 1) % queue.length != headPointer) {
                tailPointer = (tailPointer + 1) % queue.length;
                queue[tailPointer] = enteredNumber;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public synchronized int dequeue() {
        int toBeRemoved;
        try {
            if (headPointer == tailPointer) {
                Thread.sleep(2000);
                dequeue();
            }
            // Checks if we can delete an element.
            // The check is done by looking if the pointer will overwrite unprocessed data.
            if (headPointer != tailPointer) {
                headPointer = (headPointer + 1) % queue.length;
                toBeRemoved = queue[headPointer];
                return toBeRemoved;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
