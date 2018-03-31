package concurrency.locks;

/**
 * Once a thread gets access to lock, it can call lock() multiple times but it has to relase lock also same number of times
 */
public class ImprovedReentrantLock {

    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int hold = 0;

    public synchronized void lock() throws InterruptedException {

        while(Thread.currentThread() != lockedBy && isLocked){
            wait();
        }

        isLocked = true;
        lockedBy = Thread.currentThread();
        hold++;
    }

    // release lock only when no hold remaining.
    public synchronized void unlock(){

        if(Thread.currentThread() == this.lockedBy){
            hold--;
            if(hold == 0){
                isLocked = false;
                notifyAll();
            }
        }

    }

}
