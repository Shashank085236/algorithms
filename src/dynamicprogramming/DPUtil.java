package dynamicprogramming;

/**
 * Created by dwivesha on 4/3/2018.
 */
public class DPUtil {
    public static void printDpTable(String H, String V, int[][] table){
        H = H.toUpperCase(); V = V.toUpperCase();
        int dp[][] = new int[table.length+1][table[0].length+1];

        for(int row = 0 ; row < dp.length ; row++){
            for(int col = 0; col < dp[0].length; col++){
                if(row == 0 && col == 0) dp[0][0] = '*';
                else if((row == 0 && col == 1) || (row == 1 && col == 0)) dp[row][col] = '-';
                else if(row == 0 && col > 1) dp[row][col] = H.charAt(col - 2);
                else if(col == 0 && row > 1) dp[row][col] = V.charAt(row - 2);
                else dp[row][col] = table[row-1][col-1];
            }
        }

        for(int row = 0; row < dp.length; row++){
            for(int col =0; col < dp[0].length; col++){
                if(row == 0 || col == 0)
                    System.out.print((char)dp[row][col]+" | ");
                else
                    System.out.print(dp[row][col]+" | ");
            }
            System.out.println();
        }
    }
}
