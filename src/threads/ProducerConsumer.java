package threads;

import java.util.Queue;

/**
 * Created by dwivesha on 11/8/2017.
 */
public class ProducerConsumer {
}


class Producer<T> implements Runnable{

    private Queue<T> queue;
    private int MAX_SIZE;

    public Producer(Queue<T> queue, int MAX_SIZE){
        this.queue = queue;
        this.MAX_SIZE = MAX_SIZE;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (queue) {
                while (queue.size() == MAX_SIZE) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                    }
                }

                //queue.offer();
                queue.notifyAll();
            }
        }
    }

}

class Consumer implements Runnable{

    private Queue queue;
    private int MAX_SIZE;

    public Consumer(Queue queue, int MAX_SIZE){
        this.queue = queue;
        this.MAX_SIZE = MAX_SIZE;
    }

    @Override
    public void run() {

    }

}


