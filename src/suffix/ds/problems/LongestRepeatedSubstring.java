package suffix.ds.problems;

import suffix.ds.SuffixArray;

/**
 * Created by dwivesha on 4/1/2018.
 * Demo for longest repeated substring in DNA sequence.
 * Input: GCTAGGACGTCTGTACCTAGACGTCTGTCTTAG
 * Output: GACGTCTGT
 *
 * This algorithm finds longest repeated substring in provided text.
 * What if we are given two texts T1 and T2 and asked to find longest common substring betwwen them?
 * @see dynamic programming
 */
public class LongestRepeatedSubstring {

    public static void main(String[] args) {
        String sequence = "GCTAGGACGTCTGTACCTAGACGTCTGTCTTAG";
        //String sequence = "banana";
        SuffixArray suffixes = new SuffixArray(sequence);
        // O(n) implementation once suffix array is built
        int maxLcp = 0;
        int pos = 0;
        for(int i=1; i< sequence.length(); i++){
            int lcp = suffixes.lcp(i);
            if(lcp > maxLcp){
                maxLcp = lcp;
                pos = i;
            }
        }
        System.out.println("Input: "+sequence);
        System.out.printf("pos in sorted suffix array: %d, maxLcp: %d \n", pos,maxLcp);
        System.out.println("suffix: "+sequence.substring(suffixes.index(pos)));
        System.out.println("Longest repeated substring: "+sequence.substring(suffixes.index(pos),suffixes.index(pos)+maxLcp));


    }


}
