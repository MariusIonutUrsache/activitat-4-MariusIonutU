package cat.urv.deim;


public class LlistaOrdenada<E extends Comparable<E>> extends LlistaBase<E> {
    /*Cambiem el implements ILlistaGenerica<E> per extends LlistaBase<E> */
    //Metode per insertar un element a la llista. No importa la posicio on s'afegeix l'element
    public void inserir(E e){
        Node<E> inserir = new Node<E>(e);
        Node<E> actual = inici;

        while(actual.getSeguent() != null && actual.getSeguent().getDades().compareTo(e) <= 0){
            actual = actual.getSeguent();
        }
        inserir.setSeguent(actual.getSeguent());
        actual.setSeguent(inserir);
    }

}
