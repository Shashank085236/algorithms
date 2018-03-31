package concurrency.locks;

/**
 *  Simple lock gaurds against spurious wakeups but not reenterant
 *  Drawback: Not reentrant in nature i.e calling lock() again from same thread will block it forever!
 */

public class SimpleSpinLock {

    private boolean isLocked;

    public SimpleSpinLock(){
        isLocked = false;
    }

    // threads must wait till lock is available
    public synchronized void lock() throws InterruptedException {
        while (isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
