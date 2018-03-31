package threads;

/**
 * Created by dwivesha on 11/8/2017.
 * For all phylosophers do below
 while(true) {
 // Initially, thinking about life, universe, and everything
 think();

 // Take a break from thinking, hungry now
 pick_up_left_fork();
 pick_up_right_fork();
 eat();
 put_down_right_fork();
 put_down_left_fork();

 // Not hungry anymore. Back to thinking!
 }

 *
 */
public class DiningPhilosophers {

    public static void main(String[] args) {
        // create 5 philosophers and 5 forks
        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[5];

        // initialise forks
        for(int i = 0; i < forks.length; i++){
            forks[i] = new Object();
        }
        // initialise philosophers
        for(int i = 0; i < philosophers.length; i++){
            Object lFork = forks[i];
            Object rFork = forks[(i+1)%(forks.length)];
            philosophers[i] = new Philosopher(lFork,rFork);
        }
        // run philosophers
        for(int i = 0; i < philosophers.length; i++){
            new Thread(philosophers[i], "Philosopher"+(i+1)).start();
        }

    }


}

class Philosopher implements Runnable{

    // We have forks shared.

    private Object lFork;
    private Object rFork;

    Philosopher(Object lFork, Object rFork){
        this.lFork = lFork;
        this.rFork = rFork;
    }

    @Override
    public void run() {
        while(true){
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pick_up_left_fork(){
        // cannot synchronize on lFork here
            System.out.println(Thread.currentThread().getName()+" Picked up left fork");
    }

    private void pick_up_right_fork(){
        // cannot synchronize on rFork here
            System.out.println(Thread.currentThread().getName()+" Picked up right fork");
    }

    private void think() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() +" is thinking...");
        Thread.sleep(2000);
    }

    private void eat() throws InterruptedException {
        // must have locks on both forks to eat
        synchronized (lFork) {
            pick_up_left_fork();
            synchronized (rFork) {
                pick_up_right_fork();
                System.out.println(Thread.currentThread().getName() + " is eating...");
                Thread.sleep(2000);
            }
            System.out.println(Thread.currentThread().getName() + " is done eating.");
        }
    }

}
