package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GrafPersones {

    private Graf<Integer, Persona, Integer> graf;

    public GrafPersones(int n){
        this.graf = new Graf<>(n);
    }

    public GrafPersones(int n, String Arxiu_1, String Arxiu_2){
        this.graf = new Graf<>(n);
        carregarPersones(Arxiu_1);
        if(Arxiu_2 != null) {
            carregarAmistats(Arxiu_2);
        }
    }

    /* Procediments per a guardar persones */
    public void inserirPersona(Persona p) {
        graf.inserirVertex(p.getId_persona(), p);
    }

    public Persona consultarPersona(int id) throws ElementNoTrobat {
        try {
            return graf.consultarVertex(id);
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public void esborrarPersona(int id) throws ElementNoTrobat {
        try {
            graf.esborrarVertex(id);
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public int numPersones() {
        return graf.numVertex();
    }

    public boolean esBuida() {
        return graf.esBuida();
    }

    public ILlistaGenerica<Integer> obtenirPersonesIDs() {
        return graf.obtenirVertexIDs();
    }

    /*Guardem amistats*/
    public void inserirAmistat(Persona Persona_1, Persona Persona_2) throws ElementNoTrobat {
        try {
            graf.inserirAresta(Persona_1.getId_persona(), Persona_2.getId_persona());
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public void inserirAmistat(Persona Persona_1, Persona Persona_2, int intensitat) throws ElementNoTrobat {
        try {
            graf.inserirAresta(Persona_1.getId_persona(), Persona_2.getId_persona(), intensitat);
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public void esborrarAmistat(Persona Persona_1, Persona Persona_2) throws ElementNoTrobat {
        try {
            graf.esborrarAresta(Persona_1.getId_persona(), Persona_2.getId_persona());
        } catch (VertexNoTrobat | ArestaNoTrobada e) {
            throw new ElementNoTrobat();
        }
    }

    public boolean existeixAmistat(Persona Persona_1, Persona Persona_2) throws ElementNoTrobat {
        try {
            return graf.existeixAresta(Persona_1.getId_persona(), Persona_2.getId_persona());
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public int intensitatAmistat(Persona Persona_1, Persona Persona_2) throws ElementNoTrobat {
        try {
            Integer intensitat = graf.consultarAresta(Persona_1.getId_persona(), Persona_2.getId_persona());
            if (intensitat == null) {
                throw new ElementNoTrobat();
            }
            return intensitat;
        } catch (VertexNoTrobat | ArestaNoTrobada e) {
            throw new ElementNoTrobat();
        }
    }

    public int numAmistats() {
        return graf.numArestes();
    }

    public int numAmistats(Persona p) throws ElementNoTrobat {
        try {
            Vertex<Integer, Persona, Integer> vertex = graf.obtenirVertex(p.getId_persona());
            return vertex.numArestes();
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public boolean teAmistats(Persona p) {
        try {
            Vertex<Integer, Persona, Integer> vertex = graf.obtenirVertex(p.getId_persona());
            return (vertex.numArestes() > 0);
        } catch (VertexNoTrobat e) {
            return false;
        }
    }

    public ILlistaGenerica<Integer> obtenirAmistats(Persona p) throws ElementNoTrobat {
        try {
            Vertex<Integer, Persona, Integer> vertex = graf.obtenirVertex(p.getId_persona());
            return vertex.obtenirClausArestes();
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    /* Procediment que busca totes les persones del grup d'amistats de p (persona)
    / "Totes les conexions" de p. Al final retorna una llista amb les ID de les persones del grup */
    public ILlistaGenerica<Integer> obtenirGrupAmistats(Persona p) throws ElementNoTrobat {
        try {
            return graf.obtenirNodesConnectats(p.getId_persona());
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    /* Aquest metode busca el major nombre de persones que estan connectades entre si.
    / Al final retorna una llista amb les ID de les persones del grup */
    public ILlistaGenerica<Integer> obtenirGrupAmistatsMesGran() {
        return graf.obtenirComponentConnexaMesGran();
    }

    public void carregarPersones(String Arxiu) {
        try (BufferedReader br = new BufferedReader(new FileReader(Arxiu))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);
                    int edat = Integer.parseInt(data[1]);
                    String nom = data[2];
                    String cognom = data[3];
                    int al = Integer.parseInt(data[4]);
                    int pes = Integer.parseInt(data[5]);
                    Persona persona = new Persona(id, edat, nom, cognom, al, pes);
                    inserirPersona(persona);
                } catch (Exception e) {
                    // Manejar l'error si és necessari
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fitxer no trobat: " + Arxiu);
        } catch (IOException e) {
            System.out.println("Error d'entrada i sortida.");
        }
    }

    /*Procediment que carrega dades d'un fitxer de text,
     * crea objectes "Persona" basats en les dades llegides
     * després els insereix com a amistats al programa
     */
    public void carregarAmistats(String Arxiu) {
        try (BufferedReader br = new BufferedReader(new FileReader(Arxiu))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    int Identificador_1 = Integer.parseInt(data[0]);
                    int Identificador_2 = Integer.parseInt(data[1]);
                    int intensitat = Integer.parseInt(data[2]);
                    Persona Persona_1 = consultarPersona(Identificador_1);
                    Persona Persona_2 = consultarPersona(Identificador_2);
                    inserirAmistat(Persona_1, Persona_2, intensitat);
                } catch (ElementNoTrobat e) {
                    // Manejar l'error si és necessari
                } catch (Exception e) {
                    // Manejar l'error si és necessari
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fitxer no trobat: " + Arxiu);
        } catch (IOException e) {
            System.out.println("Error d'entrada i sortida.");
        }
    }
}
