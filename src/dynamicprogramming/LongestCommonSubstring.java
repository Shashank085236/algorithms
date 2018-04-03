package dynamicprogramming;

/**
 * Created by dwivesha on 4/1/2018.
 */
public class LongestCommonSubstring {


    public static void main(String[] args) {

        String T1 = "GTCCACGTAG";
        String T2 = "TGACGTGCT";
        //common = ACGT

        int[][] dp = new int[T2.length()+1][T1.length()+1]; // keep elements of first row and column 0

        int max = 0;
        int max_row = 0;

        for(int row = 0; row < T2.length(); row++) {
            for(int col = 0; col < T1.length(); col++){
                if(T1.charAt(col) == T2.charAt(row)){
                    dp[row+1][col+1] = dp[row][col] + 1;
                }else{
                    dp[row+1][col+1] = 0;
                }
                if(dp[row+1][col+1] > max){
                    max = dp[row+1][col+1];
                    max_row = row+1;
                }
            }
        }

        DPUtil.printDpTable(T1,T2,dp);

        System.out.println("\n\nLongest common substring: "+T2.substring(max_row-max,max_row));


    }
}
