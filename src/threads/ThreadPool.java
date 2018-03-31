package threads;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by dwivesha on 11/7/2017.
 *
 * Thread Pool is a pool of workers which continuouly looks for any task(Runnable) added to queue.
 *
 - Task Producer will generate the task.
 - Task submitted to Blocking queue (our custom implementation)
 - Available threads (Task Executor) in the thread pool get the tasks from blocking queue
 - Thread(s) executes & finishes the task
 - Thread become available to pick another task from queue

 */
public class ThreadPool {

    BlockingQueue <Task> queue;
    public ThreadPool(int queueSize, int nThread) {
        queue = new BlockingQueue<>(queueSize);
        String threadName = null;
        TaskExecutor worker = null;
        for (int count = 0; count < nThread; count++) {
            threadName = "Thread-"+count;
            worker = new TaskExecutor(queue);
            Thread thread = new Thread(worker, threadName);
            thread.start();
        }
    }

    public void submitTask(Task task) throws InterruptedException {
        queue.enqueue(task);
    }


    /*********** TESTING **************************/

    public static void main(String[] args) throws InterruptedException {
        int queueSize = 5;
        int poolSize = 3;

        ThreadPool pool = new ThreadPool(queueSize, poolSize);
        for(int i=1 ; i <= 10; i++){
            Task t = new TestTask(i);
            pool.submitTask(t);
        }

    }
}

/**
 *  These are workers in Thread pool which should keep running and looking for any task submitted to queue.
 */
class TaskExecutor implements Runnable {
    BlockingQueue<Task> queue;

    public TaskExecutor(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String name = Thread.currentThread().getName();
                Task task = queue.dequeue();
                System.out.println("Task Started by Thread :" + name);
                task.doTask();
                System.out.println("Task Finished by Thread :" + name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


/**
 * Simple BlockingQueue Implementation
 * It is important to have lock on single Object for enqueue and dequeue so that
 * both producers and consumers are notified
 */
class BlockingQueue<Type>  {
    private Queue<Type> queue = new LinkedList<Type>();
    private int EMPTY = 0;
    private int MAX_TASK_IN_QUEUE = -1;

    public BlockingQueue(int size){
        this.MAX_TASK_IN_QUEUE = size;
    }

    public synchronized void enqueue(Type task)
            throws InterruptedException  {
        // keep waiting till queue is full.
        while(this.queue.size() == this.MAX_TASK_IN_QUEUE) {
            wait();
        }
        // notify all producers and consumers.
        if(this.queue.size() == EMPTY) {
            notifyAll();
        }
        System.out.println("Adding "+task.toString());
        this.queue.offer(task);
    }

    public synchronized Type dequeue()
            throws InterruptedException{
        // keep waiting till queue is empty
        while(this.queue.size() == EMPTY){
            wait();
        }
        // notify all producers and consumers
        if(this.queue.size() == this.MAX_TASK_IN_QUEUE){
            notifyAll();
        }

        Type type = this.queue.poll();
        System.out.println("consumed "+type);
        return type;
    }

}


interface Task{
    void doTask();
}

class TestTask implements Task{
    private int number;
    public TestTask(int number) {
        this.number = number;
    }
    public void doTask() {
        System.out.println("Start executing of task number :"+ number);
        try {
            //Simulating processing time
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End executing of task number :"+ number);
    }

    @Override
    public String toString() {
        return "Task"+number;
    }
}


