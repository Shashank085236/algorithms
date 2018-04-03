package dynamicprogramming;

/**
 * Created by dwivesha on 4/1/2018.
 */
public class LongestCommonSubstring {


    private static void printDP(int[][] dp){
        for(int row = 0; row < dp.length; row++){
            for(int col =0; col < dp[0].length; col++){
                System.out.print(dp[row][col]+" | ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        String T1 = "GTCCACGTAG";
        String T2 = "TGACGTGCT";
        //common = ACGT

        int[][] dp = new int[T1.length()+1][T2.length()+1]; // keep elements of first row and column 0

        int max = 0;
        int max_row = 0;

        for(int row = 0; row < T2.length(); row++) {
            for(int col = 0; col < T1.length(); col++){
                if(T1.charAt(col) == T2.charAt(row)){
                    dp[col+1][row+1] = dp[col][row] + 1;
                }else{
                    dp[col+1][row+1] = 0;
                }
                if(dp[col+1][row+1] > max){
                    max = dp[col+1][row+1];
                    max_row = row+1;
                }
            }
        }

        printDP(dp);
        System.out.println("\n\nLongest common substring: "+T2.substring(max_row-max,max_row));


    }
}
