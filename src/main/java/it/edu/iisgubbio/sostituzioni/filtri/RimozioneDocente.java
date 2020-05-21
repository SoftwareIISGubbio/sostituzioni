package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class RimozioneDocente {
	/**
	 * Questo filtro serve per rimuovere dall'elenco di tutti i docenti, il docente che è assente
	 * @param tutti rappresenta un elenco di docenti
	 * @param docenteDaRimuovere il docente mancante per cui c'è bisogno della sostituzione
	 * @return elenco dei docenti senza il docente assente
	 * @author Bianca
	 */
	public static ArrayList<Docente> docentiRimozione(ArrayList<Docente> tutti, String docenteDaRimuovere) {
		ArrayList<Docente> risposta = new ArrayList<>();
		// scorre l'elenco dei docenti 
		for (Docente d : tutti) {
			// se il nome del docente è diverso dal docente assente, viene aggiunto alla lista 
			if (!d.nome.equals(docenteDaRimuovere)) {
				risposta.add(d);
			}

		}
		return risposta;
	}

}
