package threads;

/**
 * Use Java multi-threading to calculate the expression 3*2/(1+2)
 *
 *  thread1 multiplication
 *  thread2 for sum
 *  main thread for division
 *
 */
public class EquationSolver {

    public static void main(String[] args) throws InterruptedException {
        Adder adder = new Adder(1,2);
        Multiplier multiplier = new Multiplier(1,2);
        Thread adderThread = new Thread(adder);
        Thread multiplierThread = new Thread(multiplier);

        adderThread.start(); multiplierThread.start();

        adderThread.join();
        multiplierThread.join();
        System.out.println((double)multiplier.getTotal()/adder.getTotal());

    }
}

class Adder implements Runnable{

    private int value1;
    private int value2;

    private int total;

    public Adder(int value1, int value2){
        this.value1 = value1;
        this.value2 = value2;
    }

    public void run(){
        total = value1 + value2;
    }

    public int getTotal(){
        return this.total;
    }

}

class Multiplier implements Runnable{

    private int value1;
    private int value2;

    private int total;

    public Multiplier(int value1, int value2){
        this.value1 = value1;
        this.value2 = value2;
    }

    public void run(){
        total = value1 * value2;
    }

    public int getTotal(){
        return this.total;
    }


}


