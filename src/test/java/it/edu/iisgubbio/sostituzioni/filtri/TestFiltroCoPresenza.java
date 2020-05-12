package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class TestFiltroCoPresenza {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Ambiente.docenti;
		ArrayList<Docente> docentiCoPresenza;
		OraLezione classeCercata = new OraLezione(1, 1, null, "4i", false);
		long inizio = System.currentTimeMillis();
		docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, classeCercata);
		long fine = System.currentTimeMillis();
		System.out.println("tempo impiegato: "+(fine-inizio)+"msec");

		System.out.println("I docenti totali sono: " + tuttiIDocenti.size());
		System.out.println("I docenti in copresenza: " + docentiCoPresenza.size());

		for (int i = 0; i < docentiCoPresenza.size(); i++) {
			System.out.println(docentiCoPresenza.get(i));

		}

	}

}
