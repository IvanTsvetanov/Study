package Q2;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.UnitCounter;

// Counts the number of zoo guests.
public class Counter implements UnitCounter {

    // Holds the number of guests.
    private int counter = 0;

    // Adds one visitor.
    @Override
    public synchronized void addOne() {
        this.counter++;
    }

    // Returns the number of visitors.
    @Override
    public int getCounter() {
        return this.counter;
    }
}
