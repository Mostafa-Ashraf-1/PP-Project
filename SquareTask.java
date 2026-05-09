import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

public class SquareTask extends RecursiveAction {

    private int[][] board;
    private ReentrantLock lock;
    private CountDownLatch latch;
    private Error errors;

    public SquareTask(int[][] board, ReentrantLock lock, CountDownLatch latch, Error errors) {
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
                        errors.inc();
                    } finally {
                        lock.unlock();
                    }
                }
                
                latch.countDown();
            }
        }
    }
}
