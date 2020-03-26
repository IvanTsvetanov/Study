package Q2;

public class Zoo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        int expectedGuests = 0;

        try {
            Runnable r = new Gate(counter, 20);
            Thread t = new Thread(r);
            t.start();
            t.join();
            expectedGuests += 20;

            Runnable r2 = new Gate(counter, 100000);
            Thread t2 = new Thread(r2);
            t2.start();
            t2.join();
            expectedGuests += 100000;

            Runnable r3 = new Gate(counter, 1000);
            Thread t3 = new Thread(r3);
            t3.start();
            t3.join();
            expectedGuests += 1000;

            Runnable r4 = new Gate(counter, 9999);
            Thread t4 = new Thread(r4);
            t4.start();
            t4.join();
            expectedGuests += 9999;

            Runnable r5 = new Gate(counter, 1000000);
            Thread t5 = new Thread(r5);
            t5.start();
            t5.join();
            expectedGuests += 1000000;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(expectedGuests);
        System.out.println(counter.getCounter());
    }
}
