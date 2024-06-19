package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class Vertex<K extends Comparable<K>, V, E> {
    private K ID;
    private V Dades;
    private IHashMap<K, Aresta<K, E>> Arestes;

    public Vertex(K ID, V Dades){
        this.ID = ID;
        this.Dades = Dades;
        this.Arestes = new HashMapIndirecte<>(10);
    }

    public K getIDVertex(){
        return this.ID;
    }

    public void setIDVertex(K ID){
        this.ID = ID;
    }

    public V getDadesVertex(){
        return this.Dades;
    }

    public void setDadesVertex(V Dades){
        this.Dades = Dades;
    }


    /* Un Vertex és responsable de gestionar les connexions (arestes) que té amb altres vèrtexs.
     * Una Aresta només representa una connexió entre dos vèrtexs, amb la informació del pes i el destí.
     * Per aquest motiu les funcions de gestionar les arestes estan millor situades a la classe Vertex que a la classe Aresta.
     */
    public void afegirAresta(K desti, E pes){
        Arestes.inserir(desti, new Aresta<>(desti, pes));
    }

    public void esborrarAresta(K desti){
        try {
            Arestes.esborrar(desti);
        } catch (ElementNoTrobat e) {
            /* Gestiona l'element no trobat si es necessari */
        }
    }

    public E consultarPesAresta(K desti){
        try {
            Aresta<K, E> aresta = Arestes.consultar(desti);
            return aresta.getPes();
        } catch (ElementNoTrobat e) {
            return null;
        }
    }

    public boolean existeixAresta(K desti){
        return Arestes.buscar(desti);
    }

    public int numArestes(){
        return Arestes.numElements();
    }

    public ILlistaGenerica<K> obtenirClausArestes(){
        return Arestes.obtenirClaus();
    }
}
