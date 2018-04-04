package dynamicprogramming;

/**
 * Created by dwivesha on 4/3/2018.
 *
 * Statement: There are 3 operation allowed with some cost associated.
 1. Insert a character : cost 2
 2. Delete a character : cost 1
 3. Replace a character : cost 3 if characters are different else 0

 Goal is to convert a given word T1 into other word T2 with minimum cost involved.

 Example convert CART to CAT
 Greedy approach:
 1. convert R->T (cost 1) : CATT
 2. delete T (cost 1) : CAT
 total cost = 2

 Can we do better? What if we delete R from CART(total cost: 1)?

 Let's try to convert SUNDAY -> SATURDAY (cost:3)

 *
 */
public class EditDistance {
    public static final int INSERT_COST= 2;
    public static final int DELETE_COST= 1;
    public static final int REPLACE_COST= 3;

    public static void main(String[] args) {

        String T1 = "sunday";
        String T2 = "saturday";

        int[][] dp = new int[T1.length()+1][T2.length()+1];

        for(int row = 0; row <= T1.length() ; row++){
            for(int col = 0; col <= T2.length(); col++){
                // trivial cases
                if(col == 0){
                    //2.convert "" -> EXPONENTIAL by insert
                    dp[row][col] = row;
                }
                else if(row == 0){
                   // convert EXPONENTAIL -> "" by delete
                    dp[row][col] = col;
                }
                else {
                    // try all operations and pick one with min cost.
                    int costReplace = T1.charAt(row - 1) == T2.charAt(col - 1) ? 0 : REPLACE_COST;
                    dp[row][col] = DPUtil.min(dp[row - 1][col - 1] + costReplace, dp[row - 1][col] + DELETE_COST, dp[row][col - 1] + INSERT_COST);
                }
            }
        }

        DPUtil.printDpTable(T2,T1,dp);

    }
}
