package concurrency.executers;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/***************************************************************************************************************************************************
 *
 * Created by dwivesha on 11/7/2017.
 *
 *
 * Notes:

 1. ExwcuterService: It extends Executer and below are important methods added on top of Executer's void execute(Runnable task).

 - boolean isShutdown() : returns true when the executor has been shut down.

 - void shutdown() : initiates an orderly shutdown in which previously submitted
                     tasks are executed but no new tasks are accepted.

 - <T> Future<T> submit(Callable<T> task) : submits a value-returning task for
                    execution and returns a Future representing the pending results of the task.

 - Future<?> submit(Runnable task) : submits a Runnable task for execution
                    and returns a Future representing that task.

2. Executers : Util that provides different kind of executers with different policies

 - ExecutorService newCachedThreadPool() : creates cached pool and removes any thread in pool not in use for 1 min.
                    Keeps growing pool size as required.

 - ExecutorService newSingleThreadExecutor() : creates single thread executer which picks tasks from queue and executes sequencially.

 - ExecutorService newFixedThreadPool(int poolsize) :creates a thread pool that re-uses a fixed number of
                    threads operating off a shared unbounded queue.At most nThreads threads are actively
                    processing tasks. If additional tasks are submitted when all threads are active,
                    they wait in the queue until a thread is available. If any thread terminates through
                    failure during execution before shutdown,a new thread will be created to take its place
                    when subsequent tasks need to be executed. The pool's threads exist until the executor is shut down.


 ******************************************************************************************************************************************************/


public class ExecuterServiceDemo {


        static Executor pool = Executors.newFixedThreadPool(5);

        public static void main(String[] args) throws IOException
        {
            ServerSocket socket = new ServerSocket(9000);
            while (true)
            {
                final Socket s = socket.accept();
                Runnable r = new Runnable() {
                    @Override
                    public void run()
                    {
                        doWork(s);
                    }
                };
                pool.execute(r);
            }
        }

        static void doWork(Socket s) {
            System.out.println("making connection with socket: " +s);
        }

}
