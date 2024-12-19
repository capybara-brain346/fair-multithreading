import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FairCheckPrime {
    private static final int MAX_INT = 100000000;
    private static final int CONCURRENCY = 10;
    private static AtomicInteger totalPrimeNumbers = new AtomicInteger(0);
    private static AtomicInteger currentNum = new AtomicInteger(2);

    private static void checkPrime(int x) {
        if ((x & 1) == 0) {
            return;
        }
        for (int i = 3; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return;
            }
        }
        totalPrimeNumbers.incrementAndGet();
    }

    private static class Worker implements Runnable {
        private final String name;
        private final CountDownLatch latch;

        public Worker(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                int x = currentNum.incrementAndGet();
                if (x > MAX_INT) {
                    break;
                }
                checkPrime(x);
            }
            long duration = System.currentTimeMillis() - start;
            System.out.printf("thread %s completed in %d ms%n", name, duration);
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(CONCURRENCY);

        // Start all worker threads
        for (int i = 0; i < CONCURRENCY; i++) {
            Thread thread = new Thread(new Worker(String.valueOf(i), latch));
            thread.start();
        }

        try {
            latch.await(); // Wait for all threads to complete
            long duration = System.currentTimeMillis() - start;
            System.out.printf("checking till %d found %d prime numbers. took %d ms%n",
                    MAX_INT, totalPrimeNumbers.get() + 1, duration);
        } catch (InterruptedException e) {
            System.err.println("Calculation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}