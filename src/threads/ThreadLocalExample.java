package threads;

/**
 *  ThreadLocal creates local variable for threads with shared runnable instance!
 */
public class ThreadLocalExample {


    public static class MyRunnable implements Runnable {
        // variable local to thread even with shared runnable!
        private ThreadLocal<Integer> threadLocal =
                new ThreadLocal<Integer>();

        @Override
        public void run() {
            int random = (int) (Math.random() * 100D);
            System.out.println(Thread.currentThread().getName()+ " setting random number to ThreadLocal: "+random);
            threadLocal.set(random);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(Thread.currentThread().getName()+" Obtained set ThreadLocal: "+threadLocal.get());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

}
