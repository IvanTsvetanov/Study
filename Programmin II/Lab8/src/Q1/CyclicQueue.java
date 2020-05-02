package Q1;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

// Writes a circular queue data structure.
public class CyclicQueue implements NumberQueue {

    // Size of the queue. (i.e. max number of elements)
    private int capacity;
    // Create the queue.
    private int[] queue;
    // Points to the front of the queue.
    private int headPointer = 0;
    // Points to the back of the queue.
    private int tailPointer = 0;

    // Sets the size of the queue.
    public CyclicQueue(int capacity) {
        this.capacity = capacity;
        // We make the capacity of the queue to be "capacity + 1"
        // because we want to start indexing the elements from 1 (and not from 0, as the array does).
        // We want to start indexing the elements from index 1, because the formulas
        // that are used to check if we can enqueue() and dequeue() check for starting index of 1.
        queue = new int[capacity + 1];
    }

    @Override
    public void enqueue(int enteredNumber) throws IndexOutOfBoundsException {
        // Checks if we can enter an element.
        // The check is done by looking if the pointer will overwrite unprocessed data.
        if ((tailPointer + 1) % queue.length != headPointer) {
            tailPointer = (tailPointer + 1) % queue.length;
            queue[tailPointer] = enteredNumber;
        } else {
            throw new IndexOutOfBoundsException("The queue is full! You cannot add more elements!");
        }
    }

    @Override
    public int dequeue() throws IndexOutOfBoundsException {
        int toBeRemoved;

        // Checks if we can delete an element.
        // The check is done by looking if the pointer will overwrite unprocessed data.
        if (headPointer != tailPointer) {
            headPointer = (headPointer + 1) % queue.length;
            toBeRemoved = queue[headPointer];
            return toBeRemoved;
        } else {
            throw new IndexOutOfBoundsException("The queue is empty! There isn't anything more to delete!");
        }
    }

    @Override
    public boolean isEmpty() {
        // Checks if the pointers are at the same position in the queue
        if (headPointer == tailPointer) {
            return true;
        } else return false;
    }

    public static void main(String[] args) {
        CyclicQueue cq = new CyclicQueue(5);
        cq.enqueue(1);
        cq.enqueue(2);
        cq.enqueue(3);
        cq.enqueue(4);
        cq.enqueue(5);
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        /*cq.enqueue(6);
        cq.dequeue();
        System.out.println(cq.isEmpty());*/
    }
}

/*
/*Checks if the queue is full, i.e. if we can add more elements.
        if (tailLoops > headLoops && tailPointer == headPointer) {
        throw new IndexOutOfBoundsException("The queue is full! You cannot add more elements!");
        } else {
        // Checks if we need to loop to the start of the array.
        if (tailPointer == queue.length) {
        // Loop from to the start.
        tailPointer = 0;
        // Enter the element into the queue.
        queue[tailPointer] = enteredNumber;
        // Keeps track of the loops.
        tailLoops++;
        }

        // If we don't need to loop, just move the pointer.
        else if (tailPointer != queue.length) {
        // Enter the element into the queue.
        queue[tailPointer] = enteredNumber;
        // Move to the space in the queue.
        tailPointer++;
        }
        }*/

  /*if (tailLoops == headLoops && tailPointer == headPointer) {
            throw new IndexOutOfBoundsException("The queue is empty! There isn't anything more to delete!");
        } else {
            // Get the element that is going to be removed.
            int toBeRemoved = queue[headPointer];

            // Check if we need to loop to the start of the queue.
            if (headPointer == queue.length) {
                headPointer = 0;
                // Keeps track of the loops.
                headLoops++;
            }

            // If we don't need to loop, just move the pointer.
            else if (headPointer != queue.length) {
                headPointer++;
            }

            return toBeRemoved;
        }*/