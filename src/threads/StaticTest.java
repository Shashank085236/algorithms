package threads;

/**
 * // local variables to method are safe while global static variables are not
 */
public class StaticTest {

    public static void main(String[] args) {
        //mocks different request from server.
        StaticWorker w1 = new StaticWorker();
        StaticWorker w2 = new StaticWorker();
        StaticWorker w3 = new StaticWorker();

        new Thread(w1).start();
        new Thread(w2).start();
        new Thread(w3).start();

    }
}


class StaticWorker implements Runnable{

    @Override
    public void run() {
        System.out.println(StaticUtil.work());
    }
}

class StaticUtil{
    private static int global = 1;
    public static int work(){
        int count = 1;
        for( ; count < 6 ;++count,++global){
            System.out.println(Thread.currentThread().getName()+" [count: "+count+", global: "+global);
            try {
                Thread.sleep(1000);
            }catch (Exception e){e.printStackTrace();}
        }
        return count;
    }
}