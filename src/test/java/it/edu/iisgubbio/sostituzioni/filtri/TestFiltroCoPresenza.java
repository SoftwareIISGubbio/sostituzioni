package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class TestFiltroCoPresenza {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiCoPresenza;
		OraLezione classeCercata = new OraLezione(2, 3, null, "4i", false);
		docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, classeCercata);

		System.out.println("I docenti totali sono: " + tuttiIDocenti.size());
		System.out.println("I docenti in copresenza: " + docentiCoPresenza.size());

		for (int i = 0; i < docentiCoPresenza.size(); i++) {
			System.out.println(docentiCoPresenza.get(i));

		}

	}

}
