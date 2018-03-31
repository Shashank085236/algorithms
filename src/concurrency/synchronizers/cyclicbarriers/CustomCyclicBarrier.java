package concurrency.synchronizers.cyclicbarriers;

/**
 * A barrier that all threads must wait at till every thereds reaches there.
 * It can take an event thread which runs when all threads reaches to barrier.
 * barrierEvent is called cyclic as once all threads reaches,
 * CyclicBarrier can be reused once all threads are finished(This is not the case with CountDownLatch).
 *
 * How to use?
 * Share cyclic barrier among threads and call barrier.await() when barrier is reached!
 *
 */

public class CustomCyclicBarrier {

    private int totalParties;
    private int partiesAwait;

    Runnable barrierEvent;

    public CustomCyclicBarrier(int totalParties, Runnable barrierEvent){

        this(totalParties);
        this.barrierEvent = barrierEvent;
    }

    public CustomCyclicBarrier(int totalParties){
        this.totalParties = totalParties;
        this.partiesAwait = totalParties;
    }

    /**
     *  If the current thread is not the last to arrive(i.e. call await() method) then
     it waits until one of the following things happens -
     - The last thread to call arrive(i,.e. call await() method), or
     - Some other thread interrupts the current thread, or
     - Some other thread interrupts one of the other waiting threads, or
     - Some other thread times out while waiting for barrier, or
     - Some other thread invokes reset() method on this cyclicBarrier.
     */
    boolean ranEvent = false;
    public synchronized void await() throws InterruptedException {

        partiesAwait--;
        // wait till there are parties waiting
        if( partiesAwait > 0 ){
            wait();
        }
        // reset partiesAwait
        partiesAwait = totalParties;
        if(!ranEvent && null != barrierEvent) {
            barrierEvent.run();
            ranEvent = true;
        }
        notifyAll();

    }

}
