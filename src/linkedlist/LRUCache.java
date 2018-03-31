package linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * 1.The key to solve this problem is using a double linked list which enables us to quickly move nodes.
 2.The LRU cache is a hash table of keys and double linked nodes. The hash table makes the time of get() to be O(1).
 * The list of double linked nodes make the nodes adding/removal operations O(1).
 *
 * NOTE: ANY put operation must add node begining and if max_capacity reached, remove last element(which is least recently used.)
 *
 * diiference between LRU and LinkedHashMap:
 *  LinkedHashMap maintains linkedlist of keys for mainitaing insersion order while traversal along with hashmap for key value pair
 *  LRU: Maintains hashmap of key to linkedlist values. It focues on least recently used must go out once cache is full.
 *
 */
public class LRUCache {
    int MAX_CAPACITY = 10;
    Map<Integer, LRUNode> table = new HashMap<>(MAX_CAPACITY);
    LRUNode head,tail;

    public LRUCache(int MAX_CAPACITY){
        this.MAX_CAPACITY = MAX_CAPACITY;
        this.head = new LRUNode(0,0);
        this.tail = new LRUNode(0,0);
        head.setNext(tail);
        tail.setPre(head);
    }

    private void remove(int key){
        LRUNode node = table.get(key);
        if(null != node) {
            if(null != node.pre())
                node.pre().setNext(node.next());
            if(null != node.next())
                node.next().setPre(node.pre());
            table.remove(key);
        }

    }

    private void removeLast(){
        tail = tail.pre();
        tail.setNext(null);
    }

    private void addAtFirst(LRUNode node){
        //add at first
        table.put(node.key(),node);
        node.setNext(head.next());
        head = node;
    }

    public void put(int key, int value){

        LRUNode node = new LRUNode(key,value);
        // remove any existing node with same key.
        remove(node.key());
        // if out of capacity remove least recently used
        if(table.size() == MAX_CAPACITY){
            removeLast();
        }
       addAtFirst(node);

    }

    public int get(int key){
        LRUNode node = table.get(key);
        return (null == node)? Integer.MIN_VALUE : node.value();
    }

    public void display(){
        LRUNode p = head;
        while (p.next() != null){
            System.out.print(p.value()+" <==> ");
            p=p.next();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.put(1,100);
        cache.put(2,200);
        cache.put(3,300);
        cache.put(4,400);
        cache.put(5,500);
        cache.display();
        cache.put(2,2000);
        cache.display();
        cache.put(6,600);
        cache.display();
        cache.put(1,1000);
        cache.display();
    }

}

// a doublyLinkedList node
class LRUNode {
    int key;
    int value;
    LRUNode next;
    LRUNode pre;

    public LRUNode(int key, int value){
        this.key = key;
        this.value = value;
    }

    public LRUNode next() {
        return next;
    }

    public void setNext(LRUNode next) {
        this.next = next;
    }

    public LRUNode pre() {
        return pre;
    }

    public void setPre(LRUNode pre) {
        this.pre = pre;
    }

    public int key(){
        return key;
    }

    public int value(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
