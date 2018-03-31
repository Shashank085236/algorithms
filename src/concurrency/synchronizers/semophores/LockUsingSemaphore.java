package concurrency.synchronizers.semophores;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Scenario: you have a map id -> employee
 * read: 3 threads allowed to read map concurrently.
 * write: Only 1 thread is allowed to write.
 *
 * read must be blocked when write is happening.
 *
 *
 */
public class LockUsingSemaphore {

    public static void main(String[] args) {
        Map<Integer, String> sharedResource = new ConcurrentHashMap<>();
        sharedResource.put(1,"1");
        sharedResource.put(2,"2");
        WriteReadSemaphore semaphore = new WriteReadSemaphore(3);
        Reader r1 = new Reader(semaphore, sharedResource);
        Reader r2 = new Reader(semaphore, sharedResource);
        Reader r3 = new Reader(semaphore, sharedResource);
        Reader r4 = new Reader(semaphore, sharedResource);
        Reader r5 = new Reader(semaphore, sharedResource);
        Reader r6 = new Reader(semaphore, sharedResource);

        Writer w1 = new Writer(semaphore, sharedResource);
        Writer w2 = new Writer(semaphore, sharedResource);
        Writer w3 = new Writer(semaphore, sharedResource);

        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
        new Thread(r4).start();
        new Thread(r5).start();
        new Thread(r6).start();

        new Thread(w1).start();
        new Thread(w2).start();
        new Thread(w3).start();


    }

}

class Reader implements Runnable{
    WriteReadSemaphore semaphore;
    Map<Integer,String> resource;

    public Reader(WriteReadSemaphore semaphore, Map<Integer,String> resource){
        this.semaphore = semaphore;
        this.resource = resource;
    }


    @Override
    public void run() {
        try {
            semaphore.acquireRead();
                for(Map.Entry<Integer,String> entry : resource.entrySet()){
                    System.out.println(Thread.currentThread().getName()+" - key: "+entry.getKey()+" value: "+entry.getValue());
                    Thread.sleep(5000);
                }
            semaphore.releaseRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Writer implements Runnable{
    WriteReadSemaphore semaphore;
    Map<Integer,String> resource;
    public Writer(WriteReadSemaphore semaphore, Map<Integer,String> resource){
        this.semaphore = semaphore;
        this.resource = resource;
    }


    @Override
    public void run() {
        try {
            semaphore.acquireWrite();
            System.out.println(Thread.currentThread().getName()+" adding "+resource.size()+1);
            Thread.sleep(5000);
            resource.put(resource.size()+1,(resource.size()+1)+"");
            semaphore.releaseWrite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class WriteReadSemaphore{

    int readPermits;
    int writePermit = 1;

    WriteReadSemaphore(int readPermits){
        this.readPermits = readPermits;
    }

    public synchronized void  acquireRead() throws InterruptedException {
        while(!hasReadPermit()){
            System.out.println("Reader waiting as no read permit...");
            wait();
        }
        --readPermits;
    }

    public synchronized void acquireWrite() throws InterruptedException {
        while(writePermit == 0){
            System.out.println("Writer waiting as no write permit...");
            wait();
        }
        writePermit = 0;
    }

    public synchronized void releaseRead(){
        ++readPermits;
        if(hasReadPermit()){
            notifyAll();
        }
    }

    public synchronized void releaseWrite(){
        writePermit = 1;
        if(writePermit == 1){
            notifyAll();
        }
    }


    private boolean hasReadPermit(){
        return (readPermits > 0  && writePermit > 0);
    }

}