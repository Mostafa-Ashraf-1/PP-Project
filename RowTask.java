import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RowTask extends RecursiveAction {

    private int[][] board;
    private ReentrantLock lock;
    private CountDownLatch latch;
    private AtomicInteger errors;


    public RowTask(int[][] board, ReentrantLock lock, CountDownLatch latch, AtomicInteger errors) {
        this.board = board;
        this.lock = lock;
        this.latch = latch;
        this.errors = errors;
    }

    @Override
    protected void compute() {

        for (int row = 0; row < 9; row++) {

            int result = Check.checkRow(board, row);

            if (result != 0) {

                lock.lock();
                try {
                    errors.incrementAndGet();
                } finally {
                    lock.unlock();
                }
            }
            latch.countDown();
        }
    }
}
