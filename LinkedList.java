import java.util.Iterator;
public class LinkedList<E> implements Iterable<E>{
    public class LinkedListNode {
        E data;
        LinkedListNode nextNode;

        LinkedListNode(E e) {
            data = e;
        }
    }
    public class LinkedListIterator implements Iterator<E>{
        LinkedListIterator(LinkedListNode node){
            currentNode = node;
        }
        LinkedListNode currentNode;
        public boolean hasNext(){
            return currentNode.nextNode!=null;
        }
        public E next(){
            currentNode = currentNode.nextNode;
            return currentNode.data;
        }
    }

    LinkedListNode head = new LinkedListNode(null);
    LinkedListNode last = head;
    int length = 0;

    LinkedList(E[] elems) {
        LinkedListNode previousNode = head;
        for (E elem : elems) {
            LinkedListNode newNode = new LinkedListNode(elem);
            previousNode.nextNode = newNode;
            previousNode = newNode;
            length++;
        }
        last = previousNode;
    }

    LinkedList(int length) {
        LinkedListNode previousNode = head;
        for (int i = 0; i < length; i++) {
            LinkedListNode newNode = new LinkedListNode(null);
            previousNode.nextNode = newNode;
            previousNode = newNode;
        }
        last = previousNode;
        this.length = length;
    }

    LinkedList() {

    }

    public void insert(E element) {
        LinkedListNode newNode = new LinkedListNode(element);
        last.nextNode = newNode;
        last = newNode;
        length++;
    }

    public void insert(E element, int index) {
        if (index > length)
            return;
        LinkedListNode previousNode = head;
        for (int i = 0; i < index; i++) {
            if (previousNode == null)
                return;
            previousNode = previousNode.nextNode;
        }
        LinkedListNode oldNextNode = previousNode.nextNode;
        previousNode.nextNode = new LinkedListNode(element);
        previousNode.nextNode.nextNode = oldNextNode;
        length++;
    }

    public E get(int index) {
        if (index >= length)
            return null;
        LinkedListNode currentNode = head.nextNode;
        for (int i = 0; i < index; i++) {
            if (currentNode == null)
                return null;
            currentNode = currentNode.nextNode;
        }
        return currentNode.data;

    }

    public E remove(int index) {
        if (index >= length)
            return null;
        LinkedListNode previousNode = head;
        for (int i = 0; i < index; i++) {
            if (previousNode == null)
                return null;
            previousNode = previousNode.nextNode;
        }
        E data = previousNode.nextNode.data;
        previousNode.nextNode = previousNode.nextNode.nextNode;

        return data;
    }

    public LinkedListIterator iterator(){
        return new LinkedListIterator(head);
    }
}
