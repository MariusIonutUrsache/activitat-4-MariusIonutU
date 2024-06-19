package cat.urv.deim;

public class Aresta<K, E> {
    private K desti;
    private E pes;

    public Aresta(K desti, E pes) {
        this.desti = desti;
        this.pes = pes;
    }

    public K getDesti() {
        return desti;
    }

    public void setDesti(K desti) {
        this.desti = desti;
    }

    public E getPes() {
        return pes;
    }

    public void setPes(E pes) {
        this.pes = pes;
    }
}
