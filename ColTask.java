import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

public class ColTask extends RecursiveAction{

private int[][] board;
private ReentrantLock lock;
private CountDownLatch latch;
private Error errors;

public ColTask(int [][] board, ReentrantLock lock, CountDownLatch latch, Error errors)
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
                    errors.inc();
                }

                finally{
                    lock.unlock();
                }
                
            }

            latch.countDown();
        }
    }

}
