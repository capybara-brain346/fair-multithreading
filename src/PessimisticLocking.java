
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PessimisticLocking {

    private static final int NUM_THREADS = 1000000;
    private static int count = 0;
    private static final ReentrantLock lock = new ReentrantLock();
    private static AtomicInteger atomicCOunt = new AtomicInteger(0);

    private static void intCountWithLock(CountDownLatch latch) {
        lock.lock();
        try {
            count++;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
        latch.countDown();
    }

    private static void intCountAtomic(CountDownLatch latch) {
        atomicCOunt.incrementAndGet();
        latch.countDown();
    }

    private static void countAtomic() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(() -> intCountAtomic(latch));
            thread.start();
        }

        latch.await();
    }

    public static void main(String[] args) {

    }

}
