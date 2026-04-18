import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ColTask extends RecursiveAction{

private int[][] board;
private ReentrantLock lock;
private CountDownLatch latch;
private AtomicInteger errors;

public ColTask(int [][] board, ReentrantLock lock, CountDownLatch latch, AtomicInteger errors)
{
    this.board = board;
    this.lock = lock;
    this.latch = latch;
    this.errors = errors;
}
    @Override
    public void compute()
    {
        for (int col = 0; col < 9; col++)
        {
            if(Check.checkCol(board, col) != 0)
            {
                lock.lock();
                
                try{
                    errors.incrementAndGet();
                }

                finally{
                    lock.unlock();
                }
                
            }

            latch.countDown();
        }
    }

}
