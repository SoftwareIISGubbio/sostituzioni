package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class Elenchi {
    public static ArrayList<Docente> docenti = new ArrayList<>();
    
    static {
        docenti = LettoreFile.leggiXML();
    }
}
