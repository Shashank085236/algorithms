package suffix.ds;
import java.util.Arrays;

public class SuffixArray {

    private Suffix[] suffixes;
    private String text;
    public SuffixArray(String text) {
        this.text = text;
        int n = text.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(i);
        Arrays.sort(suffixes);
    }

    public int index(int i) {
        if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
        return suffixes[i].index;
    }


    /**
     * Returns the length of the longest common prefix of the i
     * smallest suffix and the <em>i</em>-1st smallest suffix.
     */
    public int lcp(int i) {
        if (i < 1 || i >= suffixes.length) throw new IllegalArgumentException();
        return lcpSuffix(suffixes[i], suffixes[i - 1]);
    }

    // longest common prefix of s and t
    private static int lcpSuffix(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return n;
    }


    private class Suffix implements Comparable<Suffix> {
        private final int index;

        private Suffix(int index) {
            this.index = index;
        }

        private int length() {
            return text.length() - index;
        }

        private char charAt(int i) {
            return text.charAt(index + i);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            //return in case this is substring of that or vice-versa.
            //example: this="abc" and that = "abcde"
            return this.length() - that.length();
        }

    }

    /******************************************************************************
     ********************           TESTING      **********************************
     *******************************************************************************/

    public static void main(String[] args) {
        String s = "GCTAGGACGTCTGTACCTAGACGTCTGTCTTAG";
        SuffixArray suffix = new SuffixArray(s);
        System.out.println("input: "+s);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("  i ind lcp rnk select");
        System.out.println("---------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffix.index(i);
            String ith = "\"" + s.substring(index, s.length()) + "\"";
            if (i == 0) {
                System.out.printf("%3d %3d %3s  %s\n", i, index, "-", ith);
            } else {
                int lcp = suffix.lcp(i);
                System.out.printf("%3d %3d %3d  %s\n", i, index, lcp, ith);
            }
        }
    }

}