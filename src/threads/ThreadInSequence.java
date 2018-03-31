package threads;

/**
 * Created by dwivesha on 11/7/2017.
 *
 * Q. Why we have used state.wait() and state.notifyAll() instead of just wait() and notifyAll()?
 * A. wait() => this.wait() and notifyAll() => this.notifyAll(). Clearly this doesn't holds lock so IllegalMonitorStateException will be thrown.
 * To call wait/notify/notifyAll, lock must be held first and thus these methods must be called from synchronized context.
 *
 */
public class ThreadInSequence {

    public static void main(String[] args) {
        State sharedState = new State(1);
        Thread t1 = new Thread(new Worker(sharedState,1),"thread1");
        Thread t2 = new Thread(new Worker(sharedState, 2), "thread2");
        Thread t3 = new Thread(new Worker(sharedState,3), "thread3");
        t1.start(); t2.start(); t3.start();
    }

}

class State {
    private int state;
    State(int state){
        this.state = state;
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState(){
        return this.state;
    }
}

class Worker implements Runnable{

    private int[] counts = {1,2,3,4,5,6,7,8,9,10};
    private static State state;
    private int id;

    Worker(State state, int id){
        this.state = state;
        this.id = id;
    }

    public void run(){
        synchronized (state) {
            for (int i : counts) {
                while(state.getState() != this.id){
                    try {
                        state.wait(); // release lock on state
                        // must regain lock on state to reenter here.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+": " + i);
                int newstate = this.id == 3 ? 1:(id+1);
                state.setState(newstate);
                state.notifyAll();
            }
        }
    }

}



