package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;
import java.util.HashSet;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class Elenchi {
    public static ArrayList<Docente> docenti = new ArrayList<>();
    
    static {
        docenti = LettoreFile.leggiXML();
        // FIXME: bisogna integrare le altre informazioni
    }
    
    public static String[] getNomiClassi(){
        HashSet<String> insiemeNomiDiClassi = new HashSet<>();
        for(Docente docente: docenti) {
            for(OraLezione ol: docente.oreLezione) {
                insiemeNomiDiClassi.add(ol.classe);
            }
        }
        return insiemeNomiDiClassi.toArray( new String[0] );
    }
}
