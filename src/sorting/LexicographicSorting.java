package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dwivesha on 3/30/2018.
 * An attempt to sort set of string(keys) lexicographically with TrieNode in linear time.
 * Assumption: all characters in key are in lowercase.
 */
public class LexicographicSorting {

    public List<String> sort(List<String> keys){
        Trie trie = new Trie();
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


class Trie{
    private TrieNode root = new TrieNode();
    private List<String> keys = new ArrayList<>();

    public void insert(String key){
        TrieNode current = root;
        for(int index = 0; index < key.length(); index++){
            char c = key.charAt(index);
            if(null == current.children()[(c - 'a')]){
                current.children()[(c - 'a')] = new TrieNode();
            }
            current =  current.children()[(c - 'a')];
        }
        current.setKey(key);
    }

    public List<String> DFS(){
        return DFS(root);
    }

    private List<String> DFS(TrieNode root) {
        if(null == root){
            return null;
        }

        for(TrieNode child : root.children()){
            if(null == child){
                continue;
            }

            if(null != child.key()){
                for(int count = 0; count <= child.occurance(); count++)
                    keys.add(child.key());
            }else {
                DFS(child);
            }
        }
        return keys;
    }

    class TrieNode {

        private String key;
        private int occurance;
        // q trie for each [a-z]
        private TrieNode[] children = new TrieNode[26];

        public String key() {
            return key;
        }

        public void setKey(String key) {
            if(null != key){
                //duplicate key found
                this.occurance++;
            }
            this.key = key;
        }

        public int occurance() {
            return occurance;
        }

        public TrieNode[] children() {
            return children;
        }

    }
}



