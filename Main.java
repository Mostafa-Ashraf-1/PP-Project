import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String args[])
    {
        final int NUM_TASKS = 27;
        final int NUM_THREADS = 3;
        ForkJoinPool pool = new ForkJoinPool(NUM_THREADS);
        ReentrantLock lock = new ReentrantLock();
        AtomicInteger errors = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(NUM_TASKS);

        int[][] board = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
        };
        
        //Invokes the mainTask
        pool.invoke(new MainTask(board, lock, latch, errors));
        //waiting for all tasks to be finished
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*getting the errors counter
        if zero >> valid
        otherwise >> invalid
        */

        if (errors.get() == 0)
        {
            System.out.println("Valid board");
        }
        else{System.out.println("Invalid board");}
    }
}
