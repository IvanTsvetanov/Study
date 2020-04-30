package Q2;

import java.util.ArrayList;
import java.util.List;

public class ZooTest {
    public static void main(String[] args) {
        Counter counter = new Counter();
        int numberGates = 20;
        int numberGuestsPerGate = 5000;
        List<Gate> allGates = new ArrayList<>();
        List<Thread> allGateThreads = new ArrayList<>();
        for (int i = 0; i < numberGates; i++) {
            allGates.add(new Gate(counter, numberGuestsPerGate));
            Thread gateThread = new Thread(allGates.get(i));
            allGateThreads.add(gateThread);
            gateThread.start();
        }
        try {
            for (int i = 0; i < numberGates; i++) {
                allGateThreads.get(i).join();
            }
        } catch (Exception ex) {ex.printStackTrace();}

        System.out.println(counter.getCounter());
        System.out.println(numberGates*numberGuestsPerGate);

    }
}
