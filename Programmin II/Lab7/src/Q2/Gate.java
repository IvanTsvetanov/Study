package Q2;

// Creates a gate via which people can enter.
public class Gate implements Runnable {

    // Holds the number of visitors that have entered using this gate.
    private int visitorsViaGate = 0;
    // Creates a counter to keep count of the overall number of guests
    private Counter counter;

    public Gate(Counter counter, int visitorsViaGate) {
        this.counter = counter;
        this.visitorsViaGate = visitorsViaGate;
    }

    // Adds to the counter the number of people that have entered using the gate.
    @Override
    public void run() {
        for(int i = 0; i < visitorsViaGate; i++) {
            counter.addOne();
        }
    }
}
