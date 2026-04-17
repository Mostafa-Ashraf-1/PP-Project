import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTask extends RecursiveAction{

    int[][] board;
    ReentrantLock lock;
    CountDownLatch latch;
    AtomicInteger errors;

    public void compute()
    {
        //Make an instance for each task and set the parameters.
        
        /*
        RowTask
        ColumnTask
        SquareTask
        */

        /*
        Use fork to run 2 of the tasks
        use compute to run the remaining task

        (Therefore that the third task would be running on the current cpu so...)

        do join for the first 2 tasks to wait them to be finished..

        
        */
    }

}
