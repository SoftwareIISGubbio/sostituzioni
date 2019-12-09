package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class TestFiltroClasse {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiClasse;

		docentiClasse = FiltroClasse.docentiDellaClasse(tuttiIDocenti, "1i");
		for (int i = 0; i < docentiClasse.size(); i++) {
			System.out.println(docentiClasse.get(i));
		}
	}

}
