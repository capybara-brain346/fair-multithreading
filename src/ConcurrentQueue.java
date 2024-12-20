
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

class Queue {

    private List<Integer> queue;
    private ReentrantLock lock;

    public Queue() {
        this.queue = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void enqueue(int item) {
        lock.lock();
        try {
            queue.add(item);
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() {
        lock.lock();
        try {
            if (queue.isEmpty()) {
                throw new RuntimeException("removing from an empty queue");
            }
            return queue.remove(0);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}

public class ConcurrentQueue {

    private static final int NUM_THREADS = 1000;

    public static void main(String[] args) throws InterruptedException {
        Queue queue = new Queue();
        CountDownLatch enqueueSignal = new CountDownLatch(NUM_THREADS);
        // CountDownLatch dequeueSignal = new CountDownLatch(NUM_THREADS);
        Random random = new Random();

        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                queue.enqueue(random.nextInt());
                enqueueSignal.countDown();
            }).start();
        }

        // for (int i = 0; i < NUM_THREADS; i++) {
        //     new Thread(() -> {
        //         queue.dequeue();
        //         dequeueSignal.countDown();
        //     }).start();
        // }

        enqueueSignal.await();
        // dequeueSignal.await();

        System.out.println("size: " + queue.size());
    }
}
