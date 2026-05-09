import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTask extends RecursiveAction{

    private int[][] board;
    private ReentrantLock lock;
    private CountDownLatch latch;
    private AtomicInteger errors;

    public MainTask(int [][] board, ReentrantLock lock, CountDownLatch latch, AtomicInteger errors)
        {
            this.board = board;
            this.lock = lock;
            this.latch = latch;
            this.errors = errors;
        }

    public void compute()
    {
        //Make an instance for each task and set the parameters.
        
        
        //RowTask
        RowTask rowTask = new RowTask(board, lock, latch, errors);
        //ColumnTask
        ColTask colTask = new ColTask(board, lock, latch, errors);
        //SquareTask
        SquareTask squareTask = new SquareTask(board, lock, latch, errors);

        
    //Use fork to run 2 of the tasks
       rowTask.fork();
       colTask.fork();
       
        /*
        Run the third task directly using compute()
        so the current worker thread also does useful work
        instead of only waiting.
         */
       squareTask.compute();

        //do join for the first 2 tasks to wait them to be finished..
        rowTask.join();
        colTask.join();
        
    }

}
