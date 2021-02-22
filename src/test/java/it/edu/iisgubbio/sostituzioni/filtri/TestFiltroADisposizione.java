package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroADisposizione {
    public static void main(String[] args) {
        ArrayList<Docente> tuttiIDocenti = Ambiente.getDocenti();
        ArrayList<Docente> docentiADisposizione;
        Ora oraCercata = new Ora(4, 4);

        docentiADisposizione = FiltroADisposizione.docentiADisposizione(tuttiIDocenti, oraCercata);
        for (int i = 0; i < docentiADisposizione.size(); i++) {
            System.out.println(docentiADisposizione.get(i));
        }
    }
}
