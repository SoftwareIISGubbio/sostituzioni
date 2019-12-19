package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroRecupero {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiLiberi;
		Ora oraCercata = new Ora(3, 2);

		docentiLiberi = FiltroRecupero.docentiRecupero(tuttiIDocenti,oraCercata);
		for (int i = 0; i < docentiLiberi.size(); i++) {
			System.out.println(docentiLiberi.get(i));
		}
	}
}
