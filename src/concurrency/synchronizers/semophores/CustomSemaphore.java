package concurrency.synchronizers.semophores;

/**
 * Semaphore: Takes mutex a level ahead. They are often used to restrict the number of threads that can access a resource.
 *  Example, limit max 10 connections to access a file simultaneously.
 *
 */
public class CustomSemaphore {

    /**
     * permits are number of threads that can be permitted to access a resource.
     * value can be -ve in which case, release must occur before any acquire can be granted.
     */
    int permits;

    public CustomSemaphore(int permits) { this.permits = permits; }


    public  synchronized void acquire() throws InterruptedException {

        // wait till permit is available
        while(permits <= 0){
            wait();
        }

        permits--;
    }

    public synchronized void release(){
        permits++;
        //notify all waiting threads when permits are available
        if(permits > 0) {
            notifyAll();
        }
    }

}
