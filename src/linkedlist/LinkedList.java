package linkedlist;

class Node{

    public int data;
    public Node next;
    public Node(int data){
        this.data = data;
        this.next = null;
    }
}

public class LinkedList {

    public static Node createLinkedList(int size){
        Node head = null, tail = null;
        for(int i=1; i <= size; i++){
            Node n = new Node(i);
            if(head == null){
                head = tail = n;
            }
            else{
                tail.next = n;
            }
            tail = n;
        }
        return head;
    }

    public static void print(Node head){
        if(head == null){
            System.out.println("empty list.");
        }
        Node p = head;
        while(p != null) {
            System.out.print(p.data + " ---> ");
            p = p.next;
        }
        System.out.print("null");
    }

    public static Node reverseIterative(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node p1 = head;
        Node p2 = p1.next;
        Node p3 = p2.next;
        p1.next = null;
        while(p3 != null){
            p2.next = p1;
            //update pointers
            p1=p2;
            p2=p3;
            p3=p3.next;
        }
        p2.next = p1;
        return p2;

    }



    public static Node swapNodesInPair(Node head){
        if(head == null || head.next == null){
            return head;
        }

        Node p1 = head;
        Node p2 = p1.next;
        Node p3 = p2.next;

        p2.next = p1;
        p1.next = p3;
        head = p2; // this is important. Now head is p2

        p1.next = swapNodesInPair(p3); // this is important p1.next should point to head returned by recusrion!
        return head;
    }


    public static void main(String[] args) {
        Node head = createLinkedList(10);
        print(head);
        System.out.println();

        print(reverseIterative(head));
        System.out.println();

        head = createLinkedList(10);
        print(swapNodesInPair(head));

    }
}
