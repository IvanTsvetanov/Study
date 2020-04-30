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

            Runnable r2 = new Gate(counter, 30);
            Thread t2 = new Thread(r2);
            t2.start();
            //t2.join();
            expectedGuests += 30;


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(expectedGuests);
        System.out.println(counter.getCounter());
    }
}
