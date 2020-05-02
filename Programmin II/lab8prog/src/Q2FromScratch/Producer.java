package Q2FromScratch;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.FactoryWorker;
import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.NumberQueue;

import java.util.Random;

public class Producer extends FactoryWorker {

    private static String jobType = "Producer";
    private int idNum;
    private NumberQueue belt;

    public Producer(int idNum, NumberQueue belt) {
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
    public void message(int idNum) {
        System.out.println(this.jobType + " " + idNum +
                " picked " + this.belt +
                " from the belt.");
    }

    // Generate a random number and add it to the queue.
    @Override
    public int action() {
        Random rand = new Random();
        int randomNum = rand.nextInt(1000);
        belt.enqueue(randomNum);
        return randomNum;
    }
}
