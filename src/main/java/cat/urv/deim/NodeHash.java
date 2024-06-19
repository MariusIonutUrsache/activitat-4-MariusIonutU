package cat.urv.deim;
public class NodeHash<K extends Comparable<K>, V> {
    private K clau;
    private V valor;
    private NodeHash<K, V> n;

    public NodeHash(K c, V v){
        this.clau = c;
        this.valor = v;
        n = null;
    }

    public K getClau(){
        return this.clau;
    }

    public V getValor(){
        return this.valor;
    }

    public NodeHash<K, V> getSeg(){
        return this.n;
    }

    public void setClau(K n){
        this.clau = n;
    }

    public void setValor(V n){
        this.valor = n;
    }

    public void setSeg(NodeHash<K, V> n){
        this.n = n;
    }

    public int compareTo(NodeHash<K, V> other){
        return this.clau.compareTo(other.getClau());
    }
}
