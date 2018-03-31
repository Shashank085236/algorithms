package array.DNF;

/**
 * Created by shashank.dwivedi on 1/22/2018.
 *
 * statement:  Given `N' objects coloured red, white or blue, sort them so that objects of the same colour are adjacent,
 * with the colours in the order red, white and blue.
 *
 * Problem: Given an array a[] consisting 0s, 1s and 2s, write a function that sorts a[].
 * The functions should put all 0s first, then all 1s and all 2s in last.
 *
 * Solution:
 The problem was posed with three colours, here `0′, `1′ and `2′. The array is divided into four sections:

 a[1..Lo-1] zeroes (red)
 a[Lo..Mid-] ones (white)
 a[Mid..Hi] unknown
 a[Hi+1..N] twos (blue)

 Examine a[Mid]. There are three possibilities:
 a[Mid] is (0) red, (1) white or (2) blue.
 Case (0) a[Mid] is red, swap a[Lo] and a[Mid]; Lo++; Mid++
 Case (1) a[Mid] is white, Mid++
 Case (2) a[Mid] is blue, swap a[Mid] and a[Hi]; Hi--
 continue till Mid < Hi
 */
public class DNF {

    public static void dnf(int[] a){

        int low = 0;
        int mid = 0;
        int high = a.length -1;
        int temp = 0;

        while(mid <= high){
            switch(a[mid]){
                case 0:
                    //all 0's are collected at left so swap a[mid] and a[low]
                    temp   =  a[low];
                    a[low]  = a[mid];
                    a[mid] = temp;
                    mid++; low++;
                    break;

                case 1:
                    // all 1's are collected in mid section, so no need to swap here
                    mid++;
                case 2:
                    // all 2's are collected in last section, so swap with high.
                    temp   =  a[high];
                    a[high]  = a[mid];
                    a[mid] = temp;
                    mid++; high--;
                    break;
            }
        }
    }
}
