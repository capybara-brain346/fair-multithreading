import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class RecordData {
    int[] attrs = new int[3];
}

class Record {
    RecordData data = new RecordData();
    ReentrantLock lock = new ReentrantLock();
}

class Database {
    private static final int NUM_RECORDS = 3;
    private static final int NUM_CONN = 6;
    private Record[] records = new Record[NUM_RECORDS];
    private Random random = new Random();

    public Database() {
        initDB();
    }

    private void initDB() {
        for (int i = 0; i < NUM_RECORDS; i++) {
            records[i] = new Record();
            records[i].data.attrs[0] = i;
            records[i].data.attrs[1] = random.nextInt(20) + 10;
        }
    }

    private void acquireLock(String txn, int recIdx) {
        System.out.printf("txn %s: wants to acquire lock on record: %d%n", txn, recIdx);
        records[recIdx].lock.lock();
        System.out.printf("txn %s: acquired lock on record: %d%n", txn, recIdx);
    }

    private void releaseLock(String txn, int recIdx) {
        records[recIdx].lock.unlock();
        System.out.printf("txn %s: released lock on record: %d%n", txn, recIdx);
    }

    class TransactionWorker implements Runnable {
        private String txnName;

        public TransactionWorker(String txnName) {
            this.txnName = txnName;
        }

        @Override
        public void run() {
            while (true) {
                // lock two records randomly
                int rec1 = random.nextInt(NUM_RECORDS);
                int rec2 = random.nextInt(NUM_RECORDS);

                if (rec1 == rec2) {
                    continue;
                }

                acquireLock(txnName, rec1);
                acquireLock(txnName, rec2);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                releaseLock(txnName, rec2);
                releaseLock(txnName, rec1);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void startSimulation() {
        Thread[] threads = new Thread[NUM_CONN];

        for (int i = 0; i < NUM_CONN; i++) {
            String txnName = String.valueOf((char) ('A' + i));
            threads[i] = new Thread(new TransactionWorker(txnName));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

public class Deadlock {
    public static void main(String[] args) {
        Database db = new Database();
        db.startSimulation();
    }
}