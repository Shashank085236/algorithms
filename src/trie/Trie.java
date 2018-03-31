package trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private TrieNode root;
    private List<String> keys;
    private int size; 
    
    public Trie(int size) {
    	this.size = size;
    	root = new TrieNode();
    	keys = new ArrayList<>();
    }

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
                for(int count = 0; count < child.occurance(); count++)
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
        private TrieNode[] children = new TrieNode[size];

        public String key() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
            occurance++;
        }

        public int occurance() {
            return occurance;
        }

        public TrieNode[] children() {
            return children;
        }

    }

}