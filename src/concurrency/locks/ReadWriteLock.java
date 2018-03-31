package concurrency.locks;

/**
 Read Access:   If no threads are writing, and no threads have requested write access.
 Write Access:   If no threads are reading or writing.

 Note: this read-write lock is not reentrant and prone to deadlock.

 consider this case:
 Thread 1 gets read access.
 Thread 2 requests write access but is blocked because there is one reader.
 Thread 1 re-requests read access (re-enters the lock), but is blocked because there is a write request

 */
public class ReadWriteLock{

    private int readers       = 0;
    private int writers       = 0;
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException{
        while(writers > 0 || writeRequests > 0){
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead(){
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        while (readers > 0 || writers > 0) {
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite() throws InterruptedException{
        writers--;
        notifyAll();
    }
}
