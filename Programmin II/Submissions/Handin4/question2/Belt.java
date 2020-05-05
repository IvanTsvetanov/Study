public class Belt extends CyclicQueue {

    private Object readLock = new Object();
    private Object writeLock = new Object();
    private Object waitLock = new Object();

    public Belt(int capacity) {
        super(capacity);
    }

    public void enqueue(int enteredNumber) {
        synchronized (writeLock) {

            while (read == (write + 1) % (elements.length)) {
                try {
                    synchronized (waitLock) {
                        waitLock.wait();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            elements[write] = enteredNumber;
            write = (write + 1) % (elements.length);


            synchronized (waitLock) {
                waitLock.notifyAll();
            }


        }
    }

    /*@Override
    public synchronized void enqueue(int enteredNumber) {
        while (read == (write + 1) % (elements.length)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        elements[write] = enteredNumber;
        write = (write + 1) % (elements.length);
        notifyAll();
    }*/


    public int dequeue() {
        synchronized (readLock) {
            int r = 0;

            while (read == write) {
                try {

                    synchronized (waitLock) {
                        waitLock.wait();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            r = elements[read];
            read = (read + 1) % (elements.length);
            synchronized (waitLock) {
                waitLock.notifyAll();
            }
            return r;

        }
    }
/*
    @Override
    public synchronized int dequeue() {


        int r = 0;

        System.out.println(r);
        while (read == write) {
            try {

                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            r = elements[read];
            read = (read + 1) % (elements.length);
            notifyAll();
            return r;
    }*/
}
