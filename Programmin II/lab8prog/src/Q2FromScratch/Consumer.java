package Q2FromScratch;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.FactoryWorker;
import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

public class Consumer extends FactoryWorker {

    private static String jobType = "Consumer";
    private int idNum;
    private NumberQueue belt;
    // Not sure if needed. Holds the variable that is dequeued from the belt.
    private int takenNumber;

    public Consumer(int idNum, NumberQueue belt) {
        super(jobType, idNum, belt);
        this.idNum = idNum;
        this.belt = belt;
    }

    // Runs the thread until it gets interrupted.
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            try {
                action();
            } catch (Exception e) {
                messageError();
            }
        }
    }

    @Override
    public void message(int i) {
        System.out.println(this.jobType + " " + idNum +
                " picked " + this.belt +
                " from the belt.");
    }

    // Take a number from the queue (belt).
    @Override
    public int action() {
        int takenNumber = belt.dequeue();
        this.takenNumber = takenNumber;
        return takenNumber;
    }
}
