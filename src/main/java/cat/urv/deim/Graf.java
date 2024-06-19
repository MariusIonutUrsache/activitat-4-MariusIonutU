package cat.urv.deim;

import cat.urv.deim.exceptions.*;
import java.util.*;

public class Graf<K extends Comparable<K>, V, E> implements IGraf<K, V, E> {

    private IHashMap<K, Vertex<K, V, E>> vertexs;
    private int Max_Elements;

    public Graf(int n){
        this.vertexs = new HashMapIndirecte<>(n);
        this.Max_Elements = n;
    }

    public void inserirVertex(K key, V value){
        if(vertexs.numElements() < Max_Elements){
            Vertex<K, V, E> ins = new Vertex<>(key, value);
            vertexs.inserir(key, ins);
        }
        else{
            throw new RuntimeException("La capacitat màxima ha estat assolida.");
        }
    }

    public V consultarVertex(K key) throws VertexNoTrobat {
        try{
            Vertex<K, V, E> vertex = vertexs.consultar(key);
            return vertex.getDadesVertex();
        } catch (ElementNoTrobat e){
            throw new VertexNoTrobat();
        }
    }

    public void esborrarVertex(K key) throws VertexNoTrobat {
        try{
            vertexs.esborrar(key);
        } catch (ElementNoTrobat e){
            throw new VertexNoTrobat();
        }

        try {
            ILlistaGenerica<K> claus = vertexs.obtenirClaus();
            Iterator<K> it = claus.iterator();
            while(it.hasNext()){
                K altraClau = it.next();
                Vertex<K, V, E> vertex = vertexs.consultar(altraClau);
                if (vertex != null){
                    vertex.esborrarAresta(key);
                }
            }
        } catch (ElementNoTrobat e){
            // Gestiona l'error si és necessari
        }
    }

    public boolean esBuida(){
        return vertexs.esBuida();
    }

    public int numVertex(){
        return vertexs.numElements();
    }

    public ILlistaGenerica<K> obtenirVertexIDs(){
        return vertexs.obtenirClaus();
    }

    public void inserirAresta(K Vertex_1, K Vertex_2 , E pes) throws VertexNoTrobat {
        Vertex<K, V, E> vertex1 = obtenirVertex(Vertex_1);
        if(Vertex_1.equals(Vertex_2)){
            vertex1.afegirAresta(Vertex_2, pes);
        } else {
            Vertex<K, V, E> vertex2 = obtenirVertex(Vertex_2);
            vertex1.afegirAresta(Vertex_2, pes);
            vertex2.afegirAresta(Vertex_1, pes);
        }
    }

    /* Procediment exactament com el anterior pero ara afegeix com a pes el valor null */
    public void inserirAresta(K Vertex_1, K Vertex_2) throws VertexNoTrobat {
        inserirAresta(Vertex_1, Vertex_2, null);
    }

    /* Procediment per saber si una aresta existeix a partir dels vertexs que connecta */
    public boolean existeixAresta(K Vertex_1, K Vertex_2) throws VertexNoTrobat {
        Vertex<K, V, E> v = obtenirVertex(Vertex_1);
        return v.existeixAresta(Vertex_2);
    }

    /* Procediment per a obtenir el pes d'una aresta a partir dels vertexs que connecta */
    public E consultarAresta(K Vertex_1, K Vertex_2) throws VertexNoTrobat, ArestaNoTrobada {
        Vertex<K, V, E> v = obtenirVertex(Vertex_1);
        E pes = v.consultarPesAresta(Vertex_2);
        if(pes == null && !v.existeixAresta(Vertex_2)){
            throw new ArestaNoTrobada();
        }
        return pes;
    }

    /* Procediment per a esborrar una aresta a partir dels vertexs que connecta */
    public void esborrarAresta(K Vertex_1, K Vertex_2) throws VertexNoTrobat, ArestaNoTrobada {
        Vertex<K, V, E> vertex1 = obtenirVertex(Vertex_1);
        Vertex<K, V, E> vertex2 = obtenirVertex(Vertex_2);
        if(!vertex1.existeixAresta(Vertex_2)){
            throw new ArestaNoTrobada();
        }
        vertex1.esborrarAresta(Vertex_2);
        vertex2.esborrarAresta(Vertex_1);
    }

    /* Procediment per numerar quantes arestes te el graf en total*/
    public int numArestes(){
        int arestes = 0;
        try {
            ILlistaGenerica<K> claus = vertexs.obtenirClaus();
            Iterator<K> Iterador_Vertex = claus.iterator();
            while (Iterador_Vertex.hasNext()) {
                K clau = Iterador_Vertex.next();
                Vertex<K, V, E> vertex = vertexs.consultar(clau);
                ILlistaGenerica<K> arestesDelVertex = vertex.obtenirClausArestes();
                Iterator<K> Iterador_Arestes = arestesDelVertex.iterator();
                while (Iterador_Arestes.hasNext()) {
                    K Clau_Adjacent = Iterador_Arestes.next();
                    if (Clau_Adjacent.equals(clau)) {
                        arestes += 1;
                    } else if (clau.compareTo(Clau_Adjacent) < 0) {
                        arestes += 1;
                    }
                }
            }
        } catch (ElementNoTrobat e) {
            // Gestiona l'error si és necessari
        }
        return arestes;
    }

    /* Procediments auxiliars per al graf*/
    /* Procediments per a saber si un vertex te veins */
    public boolean vertexAillat(K Vertex_1) throws VertexNoTrobat {
        Vertex<K, V, E> v = obtenirVertex(Vertex_1);
        return (v.numArestes() == 0);
    }

     /*Quants veins te un vertex*/
    public int numVeins(K Vertex_1) throws VertexNoTrobat {
        Vertex<K, V, E> v = obtenirVertex(Vertex_1);
        return (v.numArestes());
    }

    /*Obtenir tots les ID dels vertexs veins d'un vertex*/
    public ILlistaGenerica<K> obtenirVeins(K Vertex_1) throws VertexNoTrobat {
        Vertex<K, V, E> v = obtenirVertex(Vertex_1);
        return v.obtenirClausArestes();
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // Metodes OPCIONALS - Si es fa la part obligatoria la nota maxima sera un 8
    // Si s'implementen aquests dos metodes correctament es podra obtenir fins a 2 punts addicionals
    /* NO SOLUCIONAT */


    public ILlistaGenerica<K> obtenirNodesConnectats(K Vertex_1) throws VertexNoTrobat {
        // Implementació del mètode opcional
        return null;
    }

    public ILlistaGenerica<K> obtenirComponentConnexaMesGran() {
        // Implementació del mètode opcional
        return null;
    }

    public Vertex<K, V, E> obtenirVertex(K v) throws VertexNoTrobat {
        try {
            return vertexs.consultar(v);
        } catch (ElementNoTrobat e) {
            throw new VertexNoTrobat();
        }
    }
}
