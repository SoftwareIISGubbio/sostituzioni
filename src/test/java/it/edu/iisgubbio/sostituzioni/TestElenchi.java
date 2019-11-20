package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class TestElenchi {

    public static void main(String[] args) {
        ArrayList<Docente> elencoDocenti = Elenchi.docenti;
        for(int i=0; i<elencoDocenti.size(); i++) {
            System.out.println(elencoDocenti.get(i));
        }
    }

}
