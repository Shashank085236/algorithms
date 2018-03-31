package concurrency.forkjoin;


import java.lang.reflect.Array;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 ************************************************ Fork join frmaework ****************************************************

 The fork/join framework is an implementation of the ExecutorService interface that helps you take advantage of multiple processors.
 It is designed for work that can be broken into smaller pieces recursively.
 The goal is to use all the available processing power to enhance the performance of your application.

 As with any ExecutorService implementation, the fork/join framework distributes tasks to worker threads in a thread pool.
 The fork/join framework is distinct because it uses a work-stealing algorithm.
 Worker threads that run out of things to do can steal tasks from other threads that are still busy.

 The center of the fork/join framework is the ForkJoinPool class, an extension of the AbstractExecutorService class.
 ForkJoinPool implements the core work-stealing algorithm and can execute ForkJoinTask processes.



 Basic Use

 The first step for using the fork/join framework is to write code that performs a segment of the work.
 Your code should look similar to the following pseudocode:

 if (my portion of the work is small enough)
    do the work directly
 else
    split my work into two pieces

 invoke the two pieces and wait for the results
 Wrap this code in a ForkJoinTask subclass, typically using one of its more specialized types,
 either RecursiveTask (which can return a result) or RecursiveAction.

 After your ForkJoinTask subclass is ready,
 create the object that represents all the work to be done and pass it to the invoke() method of a ForkJoinPool instance.

 */

public class MergeSortForkJoin {
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] helper = (T[])Array.newInstance(a[0].getClass() , a.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.invoke(new MergeSortTask<T>(a, helper, 0, a.length-1));
    }


    private static class MergeSortTask<T extends Comparable<? super T>> extends RecursiveAction{
        private static final long serialVersionUID = -749935388568367268L;
        private final T[] a;
        private final T[] helper;
        private final int lo;
        private final int hi;

        public MergeSortTask(T[] a, T[] helper, int lo, int hi){
            this.a = a;
            this.helper = helper;
            this.lo = lo;
            this.hi = hi;
        }
        @Override
        protected void compute() {
            if (lo>=hi) return;
            int mid = lo + (hi-lo)/2;
            MergeSortTask<T> left = new MergeSortTask<>(a, helper, lo, mid);
            MergeSortTask<T> right = new MergeSortTask<>(a, helper, mid+1, hi);
            invokeAll(left, right);
            merge(this.a, this.helper, this.lo, mid, this.hi);
        }
        private void merge(T[] a, T[] helper, int lo, int mid, int hi){
            for (int i=lo;i<=hi;i++){
                helper[i]=a[i];
            }
            int i=lo,j=mid+1;
            for(int k=lo;k<=hi;k++){
                if (i>mid){
                    a[k]=helper[j++];
                }else if (j>hi){
                    a[k]=helper[i++];
                }else if(isLess(helper[i], helper[j])){
                    a[k]=helper[i++];
                }else{
                    a[k]=helper[j++];
                }
            }
        }
        private boolean isLess(T a, T b) {
            return a.compareTo(b) < 0;
        }
    }
}