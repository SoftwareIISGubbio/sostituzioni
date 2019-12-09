package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroLibero {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiLiberi;
		Ora oraCercata = new Ora(2, 1);

		docentiLiberi = FiltroLibero.docentiLiberi(tuttiIDocenti, oraCercata);
		for (int i = 0; i < docentiLiberi.size(); i++) {
			System.out.println(docentiLiberi.get(i));
		}
	}

}
