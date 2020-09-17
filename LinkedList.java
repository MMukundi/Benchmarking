public class LinkedList<E> {
    class LinkedListNode{
        E data;
        LinkedListNode nextNode;
        LinkedListNode(E d){data=d;}
    }
    LinkedListNode head= new LinkedListNode(null);
    LinkedListNode last;
    int length=0;
    LinkedList(E[] elems){
        LinkedListNode previousNode = head;
        for(E elem: elems){
            LinkedListNode newNode = new LinkedListNode(elem);
            previousNode.nextNode = newNode;
            previousNode=newNode;
            length++;
        }
        last=previousNode;
    }

    public void insert(E element){
        LinkedListNode newNode = new LinkedListNode(element);
        last.nextNode = newNode;
        last=newNode; 
        length++;       
    }  
    public E get(int index){
        if(index>=length)return null;
        LinkedListNode currentNode = head.nextNode;
        for(int i=0; i < length;i++){
            if(currentNode==null)return null;
            currentNode = currentNode.nextNode;
        }
        return currentNode.data;

    }
    public E remove(int index){
        if(index>=length)return null;
        LinkedListNode previousNode = head;
        for(int i=0; i < length;i++){
            if(previousNode==null)return null;
            previousNode = previousNode.nextNode;
        }
        E data = previousNode.nextNode.data;
        previousNode.nextNode = previousNode.nextNode.nextNode;
        
        return data;
    }
}
