
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class UnfairCheckPrime {

    private static final int MAX_INT = 1000000;
    private static final int CONCURRENCY = 10;
    private static AtomicInteger totalPrimeNumbers = new AtomicInteger(0);

    private static void checkPrime(int x) {
        if ((x & 1) == 0) {
            return;
        }

        for (int i = 3; i <= (int) Math.pow(x, 0.5); i += 2) {
            if (x % i == 0) {
                return;
            }
        }
        totalPrimeNumbers.incrementAndGet();
    }

    private static class BatchWorker implements Runnable {

        private final String name;
        private final CountDownLatch latch;
        private final int nstart;
        private final int nend;

        public BatchWorker(String name, CountDownLatch latch, int nstart, int nend) {
            this.name = name;
            this.latch = latch;
            this.nstart = nstart;
            this.nend = nend;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = nstart; i < nend; i++) {
                checkPrime(i);
            }
            long duration = System.currentTimeMillis() - start;
            System.out.printf("thread %s [%d, %d) completed in %d ms%n",
                    name, nstart, nend, duration);
            latch.countDown();
        }
    }
}
