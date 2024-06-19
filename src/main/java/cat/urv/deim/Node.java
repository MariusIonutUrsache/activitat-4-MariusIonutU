package cat.urv.deim;

public class Node<E> {
    private Node<E> seguent;
    private E info;

    public Node(E e){
        seguent = null;
        info = e;
    }

    public void setSeguent(Node<E> n){
        this.seguent = n;
    }

    public E getDades(){
        return this.info;
    }

    public Node<E> getSeguent(){
        return this.seguent;
    }
}
