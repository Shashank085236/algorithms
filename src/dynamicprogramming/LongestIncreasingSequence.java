package dynamicprogramming;

/**
 * Created by dwivesha on 4/5/2018.
 * Example {8,4,12,2,10,6,14,1,9,5,13,3,11,7,15}
 * Answer: 5: {2,6,9,11,15}
 */
public class LongestIncreasingSequence {
    public static void main(String[] args) {
        int[] input = {8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        int[] dp = new int[input.length];

        int largest = 0;
        for (int i = 0; i < input.length; i++) {
            int max = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (input[j] < input[i]) {
                    max = DPUtil.max(max, dp[j]);
                }
            }
            dp[i] = 1+max;
            largest = DPUtil.max(dp[i], largest);
        }

        System.out.println("Solution - LIS length " + (largest + 1));
    }
}
