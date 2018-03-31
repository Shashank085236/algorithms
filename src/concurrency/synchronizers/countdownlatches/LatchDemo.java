package concurrency.synchronizers.countdownlatches;

/**
 * Created by dwivesha on 11/22/2017.
 */
public class LatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CustomCountDownLatch latch = new CustomCountDownLatch(5);
        Examinee e1 = new Examinee(latch);
        Examinee e2 = new Examinee(latch);
        Examinee e3 = new Examinee(latch);
        Examinee e4 = new Examinee(latch);
        Examinee e5 = new Examinee(latch);

        new Thread(e1,"Examinee1").start();
        new Thread(e2,"Examinee1").start();
        new Thread(e3,"Examinee1").start();
        new Thread(e4,"Examinee1").start();
        new Thread(e5,"Examinee1").start();

        //
        System.out.println("Examiner is waiting till all Examinee finished their exam..");
        latch.await();
        System.out.println("All Examinees done");

    }
}

class Examinee implements Runnable{
    CustomCountDownLatch latch;

    public Examinee(CustomCountDownLatch latch){
        this.latch = latch;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" : Writing exam...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getName()+" : Finished exam...");
        // every thread must call countdown on latch once task is done.
        latch.countDown();
    }
}