package threads;

/**
 * Created by dwivesha on 11/8/2017.
 */
public class ParallelSum {

    public static void main(String[] args) throws InterruptedException {
        Add adder1 = new Add(1,10);
        Add adder2 = new Add(11,20);
        Add adder3 = new Add(21,30);
        Add adder4 = new Add(31,40);
        Add adder5 = new Add(41,50);


        Thread t1 = new Thread(adder1);
        Thread t2 = new Thread(adder2);
        Thread t3 = new Thread(adder3);
        Thread t4 = new Thread(adder4);
        Thread t5 = new Thread(adder5);

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
        // main should join when all adder threads completes.
        // NOTE: all adder threads will keep running in parellel except MAIN thread which should wait till all thread finishes.
        // Q. does thread release lock once join is called?
        /**
        If you look at source code of join() method,It internally invokes wait() method
         and wait() method release all the resources before going to WAITING state.

        public final synchronized void join(){
            ...
            while (isAlive()) {
                wait(0);
            }
            ...
        }
         */

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        int sum = adder1.getTotal()+adder2.getTotal()+adder3.getTotal()+adder4.getTotal()+adder5.getTotal();
        System.out.println("Total: "+sum);
    }
}

class Add implements Runnable{

    private int start;
    private int end;
    private int sum = 0;

    Add(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run(){
        for(int i = start; i <= end; i++){
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " is working...");
            }catch (InterruptedException e){}
            sum+=i;
        }
    }

    public int  getTotal(){
        return sum;
    }

}
