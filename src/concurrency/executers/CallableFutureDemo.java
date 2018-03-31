package concurrency.executers;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Random;
import java.util.concurrent.Callable;
import java.sql.Timestamp;

public class CallableFutureDemo {
    public static void main(String[] args) {

        int counter = 0; // used for just debugging purpose.

        ExecutorService executorservice = Executors.newFixedThreadPool(5);
        ConcurrentLinkedQueue<Future<Result>> results = new ConcurrentLinkedQueue<Future<Result>>();
        // submitting  tasks and storing Future to List.
        for(int i = 0; i < 20 ; i++) {
            results.add(executorservice.submit(new Task()));
        }

        while(results.size() > 0){
            for( Future<Result> result : results ){
                counter++;
                // trying to cancel 3rd task
                if(counter == 3){
                    //interrupt if task is executing also...
                    //returns true if cancelled successfully, false otherwise
                    //Expect an IntrupptedException in case task already started running.
                    System.out.println(result.cancel(true));

                    //System.out.println("Task cancelled? "+result.isCancelled());
                }

                //get is Blocking,thus before calling get, we must check if task is done.
                if(result.isDone()){
                    try {
                        // We should look here if task is not already cancelled as, we cannot get result for a cancelled task.
                        if(!result.isCancelled())
                            System.out.println(result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    results.remove(result);
                }
            }
        }

        executorservice.shutdown();

    }
}



class Task implements Callable<Result> {

    @Override
    public Result call() throws Exception {
        return performTask();
    }

    private Result performTask() {
        Result result = new Result();
        result.setTime(System.currentTimeMillis());
        result.setMessage(getMessage());
        return result;
    }

    private String getMessage(){
        // assume this take time,so implementing a delay
        Random rand = new Random();
        int  n = rand.nextInt(5000) + 1;
        try {

            synchronized (this) {
                wait(n);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "message obtained after delay: "+n+"ms.";

    }
}


class Result {

    private String message;
    private long time;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Timestamp getTime() {
        return new Timestamp(time);
    }
    public void setTime(long time) {
        this.time = time;
    }

    public String toString(){
        return "[ time: "+getTime()+", message: "+getMessage()+"]";
    }
}