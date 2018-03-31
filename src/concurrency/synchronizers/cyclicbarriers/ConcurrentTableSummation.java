package concurrency.synchronizers.cyclicbarriers;

class ConcurtentTableSummation {

    static int[] results;

    public static void main(String args[]) {

        //should be shared among all inner classes too!

        int matrix[][] =
                {
                        { 1 },
                        { 2, 2 },
                        { 3, 3, 3 },
                        { 4, 4, 4, 4 },
                        { 5, 5, 5, 5, 5 }
                };



        final int rows = matrix.length;
        results = new int[rows];

        Runnable merger = new Runnable() {
            public void run()
            {
                int sum = 0;
                for (int i = 0; i < rows; i++)
                {
                    sum += results[i];
                }
                System.out.println("Results are: " + sum);
            }
        };
        /*
         * public CyclicBarrier(int parties,Runnable barrierAction)
         * Creates a new CyclicBarrier that will trip when the given number
         * of parties (threads) are waiting upon it, and which will execute 
         * the merger task when the barrier is tripped, performed 
         * by the last thread entering the barrier.
         */
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(rows, merger);
        for (int i = 0; i < rows; i++)
        {
            new Summer(barrier, i, matrix).start();
        }
        System.out.println("Waiting...");
    }



}

class Summer extends Thread {
    int row;

    CustomCyclicBarrier barrier;
    int[][] matrix;
    private static int results[];

    Summer(CustomCyclicBarrier barrier, int row, int matrix[][]) {
        this.barrier = barrier;
        this.row = row;
        this.matrix = matrix;
        this.results = new int[matrix.length];
    }

    public void run() {

        int columns = matrix[row].length;
        int sum = 0;
        for (int i = 0; i < columns; i++) {
            sum += matrix[row][i];
        }
        results[row] = sum;
        try {
            System.out.println(Thread.currentThread().getName()+ " barrier reached Results for row " + row + " : " + sum);
            barrier.await();
            System.out.println(Thread.currentThread().getName()+ " crossed the barrier.");
        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}