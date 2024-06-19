package cat.urv.deim;
import cat.urv.deim.exceptions.*;
import java.util.Iterator;

public abstract class LlistaBase<E extends Comparable<E>> implements ILlistaGenerica<E> {

    protected Node<E> inici;

    public LlistaBase(){
        inici = new Node<E>(null);
        inici.setSeguent(null);
    }

    //Metode per insertar un element a la llista. No importa la posicio on s'afegeix l'element
    public abstract void inserir(E e);

    //Metode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat{
        if(!this.existeix(e))
            throw new ElementNoTrobat();
            Node<E> actual = inici.getSeguent();
            Node<E> previ = inici;
            while(actual != null){
            if(actual.getDades().equals(e)){
                previ.setSeguent(actual.getSeguent());
                return;
            }
            else
            previ = actual;
            actual = actual.getSeguent();
        }
    }

    //Metode per a consultar un element de la llista per posicio
    //La primera dada esta a la posicio 0
    public E consultar(int pos) throws PosicioForaRang{
        if((this.numElements() < pos + 1) || (pos < 0))
            throw new PosicioForaRang();
        Node<E> actual = inici.getSeguent();
        E consultar = null;
        int index = 0;
        while(actual != null){
            if(index == pos)
                consultar = actual.getDades();
            actual = actual.getSeguent();
            index++;
        }
        return(consultar);
    }

    //Metode per a comprovar en quina posicio esta un element a la llista
    //La primera dada esta a la posicio 0
    public int buscar(E e) throws ElementNoTrobat{
        if(this.esBuida() || !(this.existeix(e)))
            throw new ElementNoTrobat();
        int posicio = 0;
        int index = 0;
        Node<E> actual = inici.getSeguent();
        while(actual != null){
            if(actual.getDades().equals(e))
                posicio = index;
            actual = actual.getSeguent();
            index++;
        }
        return posicio;
    }

    //Metode per a comprovar si un element esta a la llista
    public boolean existeix(E e){
        boolean existeix = false;
        Node<E> actual = inici.getSeguent();
        while(actual != null){
            if(actual.getDades().equals(e))
                existeix = true;
            actual = actual.getSeguent();
        }
        return existeix;
    }

    //Metode per a comprovar si la llista te elements
    public boolean esBuida(){
        return(inici.getSeguent() == null);
    }

    //Metode per a obtenir el nombre d'elements de la llista
    public int numElements(){
        int index = 0;
        Node<E> actual = inici.getSeguent();
        while(actual != null){
            actual = actual.getSeguent();
            index++;
        }
        return index;
    }

    //Metode per a obtenir un array amb tots els elements
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> actual = inici.getSeguent();

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public E next() {
                E dades = actual.getDades();
                actual = actual.getSeguent();
                return dades;
            }
        };
    }

}
