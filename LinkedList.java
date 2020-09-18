import java.util.Iterator;

//References used to implement range for-each loop
//https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html
//https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
public class LinkedList<E> implements Iterable<E> {
    public class LinkedListNode {
        E data;
        LinkedListNode nextNode;

        LinkedListNode(E e) {
            data = e;
        }
    }

    public class LinkedListIterator implements Iterator<E> {
        LinkedListIterator(LinkedListNode node) {
            currentNode = node;
        }

        LinkedListNode currentNode;

        public boolean hasNext() {
            return currentNode.nextNode != null;
        }

        public E next() {
            currentNode = currentNode.nextNode;
            if (currentNode == null)
                return null;
            return currentNode.data;
        }

        public void insertAfter(E e) {
            LinkedListNode next = currentNode.nextNode;
            currentNode.nextNode = new LinkedListNode(e);
            currentNode.nextNode.nextNode = next;
            if (next == null) {
                last = currentNode.nextNode;
            }
            length++;
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
        else if (index == length) {
            insert(element);
            return;
        }
        LinkedListNode previousNode = head;
        for (int i = 0; i < index; i++) {
            if (previousNode == null)
                return;
            previousNode = previousNode.nextNode;
        }
        LinkedListNode oldNextNode = previousNode.nextNode;
        previousNode.nextNode = new LinkedListNode(element);
        previousNode.nextNode.nextNode = oldNextNode;
        if (oldNextNode == null) {
            last = previousNode.nextNode;
        }
        length++;
    }

    public E get(int index) {
        if (index > length)
            return null;
        else if (index == length)
            return last.data;
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
        if (previousNode.nextNode == null) {
            last = previousNode;
        }
        length--;
        return data;
    }

    public LinkedListIterator iterator() {
        return new LinkedListIterator(head);
    }

    public void clear() {
        head = new LinkedListNode(null);
        last = head;
        length = 0;
    }

}
