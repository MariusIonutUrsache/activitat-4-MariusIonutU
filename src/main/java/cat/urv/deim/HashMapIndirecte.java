package cat.urv.deim;
import java.util.*;
import java.lang.IllegalArgumentException;
import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapIndirecte<K extends Comparable<K>, V> implements IHashMap<K, V>, Iterator<V>{

    private int Max_Elements;
    private int numElements;
    private float factorCarrega;
    private ArrayList<NodeHash<K,V>> Taula_Hash;
    private Iterator<V> it;

    public HashMapIndirecte(int Max_Elements)throws IllegalArgumentException{
        if(Max_Elements<=0)
            throw new IllegalArgumentException();
        this.Max_Elements = Max_Elements;
        Taula_Hash = new ArrayList<>(Collections.nCopies(Max_Elements, null));
        factorCarrega = 0;
        numElements = 0;
    }

    /* Procediment per insertar un element a la taula.
    /Si l'element ja existeix amb aquesta clau s'actualitza el valor d'aquest*/
    public void inserir(K Clau, V Valor){
        if(factorCarrega > 0.75f){
            redimensionar();
        }
        int i = Math.abs(Clau.hashCode()) % Max_Elements;
        NodeHash<K, V> nou = new NodeHash<>(Clau, Valor);
        NodeHash<K, V> act = Taula_Hash.get(i);
        if(act == null){
            Taula_Hash.set(i, nou);
        }
        else{
            while(act != null){
                if(act.getClau().equals(Clau)){
                    act.setValor(Valor);
                    return;
                }
                if(act.getSeg() == null){
                    act.setSeg(nou);
                    break;
                }
                act = act.getSeg();
            }
        }
        numElements++;
        factorCarrega = (float)numElements / Max_Elements;
    }

    // Obtenir llista amb tots els elements de K
    public V consultar(K Clau) throws ElementNoTrobat{
        int i = Math.abs(Clau.hashCode()) % Max_Elements;
        NodeHash<K, V> act = Taula_Hash.get(i);
        while(act != null){
            if(act.getClau().equals(Clau)){
                return act.getValor();
            }
            act = act.getSeg();
        }
        throw new ElementNoTrobat();
    }

    /* Esborrar element de la taula de Hash*/
    public void esborrar(K Clau) throws ElementNoTrobat{
        int i = Math.abs(Clau.hashCode()) % Max_Elements;
        NodeHash<K, V> act = Taula_Hash.get(i);
        NodeHash<K, V> Previ = null;
        while(act != null){
            if(act.getClau().equals(Clau)){
                if(Previ == null){
                    Taula_Hash.set(i, act.getSeg());
                }
                else{
                    Previ.setSeg(act.getSeg());
                }
                numElements--;
                factorCarrega = (float)numElements / Max_Elements;
                return;
            }
            Previ = act;
            act = act.getSeg();
        }
        throw new ElementNoTrobat();
    }

    /* Comprovar si l'element està a la taula Hash*/
    public boolean buscar(K Clau){
        int i = Math.abs(Clau.hashCode()) % Max_Elements;
        NodeHash<K, V> act = Taula_Hash.get(i);
        while(act != null){
            if(act.getClau().equals(Clau)){
                return true;
            }
            act = act.getSeg();
        }
        return false;
    }

    /* Comprovar si la taula te elements*/
    public boolean esBuida(){
        return numElements == 0;
    }

    /* Obtenir el numero d'elements de la llista*/
    public int numElements(){
        return this.numElements;
    }

    /* Obtenir les claus de la taula */
    public ILlistaGenerica<K> obtenirClaus(){
        ILlistaGenerica<K> claus = new LlistaOrdenada<K>();
        for(NodeHash<K, V> n : Taula_Hash){
            NodeHash<K, V> act = n;
            while(act != null){
                claus.inserir(act.getClau());
                act = act.getSeg();
            }
        }
        return claus;
    }

    /* Obtenir el factor de carrega actual de la taula */
    public float factorCarrega(){
        return this.factorCarrega;
    }

    /* Obtenir la mida actual de la taula estatica */
    public int midaTaula(){
        return Max_Elements;
    }

    /* Operacions per poder iterar pels elements de la taula
    /El recorregut es fa de forma ordenada segons la clau */
    public Iterator<V> iterator(){
        ArrayList<NodeHash<K, V>> ordenar = new ArrayList<>();
        for(NodeHash<K, V> Node_Actual : Taula_Hash){
            while (Node_Actual != null){
                insertarOrdenat(ordenar, Node_Actual);
                Node_Actual = Node_Actual.getSeg();
            }
        }
        ArrayList<V> valors = new ArrayList<>();
        for(NodeHash<K, V> Node_Actual : ordenar){
                valors.add(Node_Actual.getValor());
            }
        return valors.iterator();
    }

    public boolean hasNext(){
        if (it == null) {
            it = iterator();
        }
        return it.hasNext();
    }

    public V next() {
        if (it == null) {
            it = iterator();
        }
        try {
            return it.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    private void redimensionar(){
        int novaMida = Max_Elements * 2;
        ArrayList<NodeHash<K, V>> novaTaula = new ArrayList<>(novaMida);
        for(int i = 0; i < novaMida; i++){
            novaTaula.add(i, null);
        }
        for(NodeHash<K, V> n : Taula_Hash){
            NodeHash<K, V> act = n;
            while(act != null){
                if(act.getClau() != null){
                    int i = Math.abs(act.getClau().hashCode()) % novaMida;
                    NodeHash<K, V> seg = act.getSeg();
                    act.setSeg(null);
                    if(novaTaula.get(i) == null){
                        novaTaula.set(i, act);
                    }
                    else{
                        act.setSeg(novaTaula.get(i));
                        novaTaula.set(i, act);
                    }
                    act = seg;
                }
                else{
                    break;
                }
            }
        }
        Taula_Hash = novaTaula;
        Max_Elements = novaMida;
    }

    private void insertarOrdenat(ArrayList<NodeHash<K, V>> ord, NodeHash<K, V> node){
        int i = 0;
        while (i < ord.size() && node.getClau().compareTo(ord.get(i).getClau()) > 0){
            i++;
        }
        ord.add(i, node);
    }
}
