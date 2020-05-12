package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroRecupero {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Ambiente.docenti;
		ArrayList<Docente> docentiLiberi;
		long inizio = System.currentTimeMillis();
		Ora oraCercata = new Ora(3, 2);
		docentiLiberi = FiltroRecupero.docentiRecupero(tuttiIDocenti,oraCercata);
		long fine = System.currentTimeMillis();
        System.out.println("tempo impiegato: "+(fine-inizio)+"msec");
        
		for (int i = 0; i < docentiLiberi.size(); i++) {
			System.out.println(docentiLiberi.get(i));
		}
	}
}
