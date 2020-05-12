package Q3;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.Seat;

import java.util.concurrent.locks.ReentrantLock;

public class SeatY implements Seat {
    ReentrantLock fork1 = new ReentrantLock();
    ReentrantLock fork2 = new ReentrantLock();

    @Override
    public void askFork1() {
        this.fork1.lock();
    }

    @Override
    public void askFork2() {
        this.fork2.lock();
    }

    @Override
    public void assignForks(ReentrantLock reentrantLock, ReentrantLock reentrantLock1) {
        this.fork1 = reentrantLock1;
        this.fork2 = reentrantLock;
    }
}
