package concurrency.atomic;

/**
 *
 *  AtomicInteger  provides you with int value that is updated atomically.
 *
 */
public class CustomAtomicInteger {

    private int preValue;
    private int currentValue;

    public CustomAtomicInteger(int value){
        this.currentValue = value;
    }

    public synchronized int get(){
        return this.currentValue;
    }

    public synchronized void set(int newValue){
        this.currentValue = newValue;
    }

    //set new value and return previous value.
    public synchronized int getAndSet(int newValue){
        preValue = currentValue;
        set(newValue);
        return preValue;
    }

    //Compare with expect, if equal, set to update and return true.
    // In java this is done without synchronization, using CAS(compare and swap) technique.
    // synchronization is pesimmistic approach while CAS is optimistic approach which retries if fail.
    // CAS: cmpare value in memory with expect, if same write value in memory as update.
    // if value in memory is not same as expect, notify caller that update failed. it can be retried with updated expect and updare values!
    public synchronized boolean compareAndSet(int expect, int update){
        if(currentValue == expect){
            currentValue=update;
            return true;
        }
        else
            return false;
    }


    //Adds value to the current value. And return updated value.
    public synchronized int addAndGet(int value){
        return currentValue+=value;
    }

    //Increments current value by 1. And return updated value.
    public synchronized int incrementAndGet(){
        return ++currentValue;
    }


    //add to current value and return previous.
    public synchronized int getAndAdd(int value){
        preValue=currentValue;
        currentValue+=value;
        return preValue;
    }

    //increments and get previous value
    public synchronized int getAndIncrement(){
        return currentValue++;
    }

    //devrements and get updated value
    public synchronized int decrementAndGet(){
        return --currentValue;
    }

    //decrements and get previous value
    public synchronized int getAndDecrement(){
        return currentValue--;
    }


}
