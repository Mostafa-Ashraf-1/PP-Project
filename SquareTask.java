import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SquareTask extends RecursiveAction {

    private int[][] board;
    private ReentrantLock lock;
    private CountDownLatch latch;
    private AtomicInteger errors;

    public SquareTask(int[][] board, ReentrantLock lock, CountDownLatch latch, AtomicInteger errors) {
        this.board = board;
        this.lock = lock;
        this.latch = latch;
        this.errors = errors;
    }

    @Override
    protected void compute() {
        
        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                
                if (!Check.checkSquare(board, startRow, startCol)) {
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
}
