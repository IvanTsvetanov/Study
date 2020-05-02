package Q2FromScratch;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

public class CyclicQueue implements NumberQueue {
    public int[] elements;
    public int read, write = 0;
    public int capacity;

    public CyclicQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity + 1];
    }

    @Override
    public void enqueue(int i) throws IndexOutOfBoundsException {
        if(read != ( write + 1 ) % (elements.length)) {
            elements[write] = i;                 // Store x in buf at write pointer
            write = (write+1)%(elements.length); // Advance the write pointer
        } else {
            throw new IndexOutOfBoundsException("Queue is full");
        }
    }

    @Override
    public int dequeue() throws IndexOutOfBoundsException {
        int r;   // Variable used to save the return value

        if( read != write) {
            r = elements[read];                 // Save return value
            read = (read+1)%(elements.length);  // Advance the read pointer

            return r;
        } else {
            throw new IndexOutOfBoundsException("Queue is empty!");
        }

    }

    @Override
    public boolean isEmpty() {
        if(read == write) return true;
        else return false;
    }

    public static void main(String[] args) {

    }
}
