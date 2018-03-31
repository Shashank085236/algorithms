package sorting;

import java.util.Arrays;
import java.util.List;

import trie.Trie;

/**
 * Created by dwivesha on 3/30/2018.
 * An attempt to sort set of string(keys) lexicographically with TrieNode in linear time.
 * Assumption: all characters in key are in lowercase.
 */
public class LexicographicSorting {

    public List<String> sort(List<String> keys){
        Trie trie = new Trie(26);
        for(String key : keys){
            System.out.println("inserting "+key);
            trie.insert(key.toLowerCase());
        }

        return trie.DFS();
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("A","demo","for","trie","datastructure","implementation","for","sorting","data","lexicographically");
        System.out.println(new LexicographicSorting().sort(input));
    }
}



