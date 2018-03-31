package concurrency.synchronizers.countdownlatches;

/**

 - CountDownLatch: With a latch, the waiters wait for the last arriving thread to arrive, but those arriving threads don't do any waiting themselves.
 Example: Exam prompter who waits patiently for each student to hand in their test. Students don't wait once they complete their exams and are free to leave.
            Once the last student hands in the exam (or the time limit expires), the prompter stops waiting and leaves with the tests.

 - CyclicBarrier: With a barrier, all threads arrive and then wait for the last to arrive.
 Example: If you are going to a picnic, and you need to first meet at some common point from where you all will start your journey.


 CyclicBarrier makes threads do waiting while countdownlatch makes

 **/

public class CustomCountDownLatch {

    private int count;

    public CustomCountDownLatch(int count){
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {

        while (count > 0){
            wait();
        }

    }

    public synchronized void countDown() {
        count--;
        //If count is equal to 0, notify all waiting threads.
        if(count == 0) {
            this.notifyAll();
        }
    }

}
