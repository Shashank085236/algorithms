package sorting;

import java.util.Arrays;

/**
 * Created by dwivesha on 3/30/2018.
 * A linear sort algorithm.
 * - We can sort chars also as they act as integers.
 */
public class CounterSort {

    public int[] sort(int[] input){
        int[] sorted = new int[input.length];
        int[] bucket = new int[input.length];

        //counter for number of occurances of elements
        for(int index = 0; index < input.length; index++){
            ++bucket[input[index]];
        }

        // traverse through buckets and store elements in sorted array.
        // This is linear even with two loops. why?
        // because inner loops can't go for more than number of occurance of repeted elements.
        int currentPos = 0;
        for(int index=0; index < bucket.length; index++){
            if(bucket[index] != 0){
                for(int i = 0 ; i < bucket[index] ; i++){
                    sorted[currentPos++] = index;
                }
            }
        }

        return sorted;
    }

    public static void main(String[] args) {
        int[] input = {5,2,4,1,3,2,4,5,3,5,4,4,5,3,5};
        System.out.println(Arrays.toString(new CounterSort().sort(input)));
    }

}
