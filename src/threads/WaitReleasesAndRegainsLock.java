package threads;

import java.util.Date;

/**
 * Created by dwivesha on 11/8/2017.
 *
 * Problem: Every thread has some important job and some non-important jobs.
 * every thread must finish all important jobs first. Once all threads are done with important jobs, then each thread will given chance to do non-important jobs.
 *
 *
 */
public class WaitReleasesAndRegainsLock {
    public static void main(String[] args) throws Exception {
        testCuncurrency();
    }

    private static void testCuncurrency() throws InterruptedException {
        //share same lock to different workers
        Object lock = new Object();
        Thread t1 = new Thread(new WaitTester(lock));
        Thread t2 = new Thread(new WaitTester(lock));
        Thread t3 = new Thread(new WaitTester(lock));
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(15 * 1000);
        synchronized (lock) {
            System.out.println("Time: " + new Date().toString()+ ";" + "Notifying all threads as all important tasks done!");
            lock.notifyAll(); //someone has to notify threads when they are waiting. All waiting threads will finish non important jobs now
        }
    }

    private static class WaitTester implements Runnable {
        private Object lock;
        public WaitTester(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println(getTimeAndThreadName() + ":doing my important job");
                    Thread.sleep(5 * 1000); //simulate an important initialization job

                    System.out.println(getTimeAndThreadName() + ":thread goes into waiting state and releases the lock");
                    lock.wait();                  // job done so wait before performing non-important jobs below and release lock for other threads

                    System.out.println(getTimeAndThreadName() + ":thread is awake and have reacquired the lock");

                    System.out.println(getTimeAndThreadName() + ":finished doing remaining non-important task.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getTimeAndThreadName() {
        return "Time: " + new Date().toString() + ";" + Thread.currentThread().getName();
    }
}
