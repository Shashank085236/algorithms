package PriorityQueue;

/**
 * Priority Queue using binary heap.
 *
 * Heap property: parent is always bigger than both left and right children
 * insert and delete in O(log n) time.
 * <img src="Max-Heap.png" />
 * - children of node at k are at (2k) and (2k+1)
 * - first element starts with position 1 in array.
 */
public class PriorityQueue {
    BinaryMaxHeap maxHeap;
    public PriorityQueue() {
        maxHeap = new BinaryMaxHeap(10);
    }

    public void insert(int value){
        maxHeap.add(value);
    }

    public int get(){
        return maxHeap.deleteMax();
    }

    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        pq.insert(5);
        pq.insert(6);
        pq.insert(1);
        pq.insert(3);
        pq.insert(7);
        pq.insert(4);

        System.out.println(pq.get());
        System.out.println(pq.get());
        System.out.println(pq.get());
        System.out.println(pq.get());
        System.out.println(pq.get());
        System.out.println(pq.get());

    }
}

class BinaryMaxHeap {

    final private Integer[] heap;
    private int size = 0;

    public BinaryMaxHeap(int MAX_SIZE){
        heap = new Integer[MAX_SIZE];
    }


    public void add(int element){
        heap[++size] = element;
        promote(size);
    }

    /**
     *  this operation gets max element from heap and restores heap order.
     */
    public int deleteMax(){
        int max = heap[1];
        exchange(1,size--);
        demote(1);
        heap[size+1] = null;
        return max;
    }

    /**
     *  Promotion in heap: When child key becomes larger than parent,
     *  exchange keys of child and parent repeatdly till heap order is restored.
     *
     *  k = current index of child
     *  k/2 = index of parent
     */
    private void promote(int k){
        while(k > 1 && (heap[k/2]) < heap[k]){
            exchange(k,k/2);
            k = k/2;
        }
    }

    /**
     * Demotion in heap: when parent becomes smaller than any or both of child
     * k = index of parent.
     * exchange key of parent with larger child repeatdly till heap order is restored.
     */
    private void demote(int k){
        while((heap[k] < heap[2*k] || heap[k] < heap[(2*k)+1]) && k < size){
            if(heap[k] < heap[2*k]){
                exchange(k,2*k);
                k = 2*k;
            }else{
                exchange(k,(2*k)+1);
                k = (2*k)+1;
            }
        }
    }


    private void exchange(int index1, int index2){
        heap[index1] = heap[index1] + heap[index2];
        heap[index2] = heap[index1] - heap[index2];
        heap[index1] = heap[index1] - heap[index2];
    }
}
