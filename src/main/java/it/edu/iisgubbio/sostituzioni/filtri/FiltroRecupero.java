package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroRecupero {
	/**
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraDaRecuperare ora in cui il docente è disponibile a fare recupero
	 * @return l'elenco dei professori che mettono a disposizione un'ora di recupero in un determinato giorno
	 * @author Bianca
	 */
	public static ArrayList<Docente> docentiRecupero(ArrayList<Docente> tutti, Ora oraDaRecuperare) {
		ArrayList<Docente> risposta = new ArrayList<>();
		// cerca i docenti nella lista di tutti i docenti
		for (Docente d : tutti) {
			// esclude i prof che non hanno ore di recupero
			if(d.oraARecupero!=null) {
				// se l'ora di recupero di un docente corrisponde con l'ora in un determinato giorno in cui c'è bisogno di recupero, allora aggiunge il docente alla lista
				if (d.oraARecupero.giorno == oraDaRecuperare.giorno && d.oraARecupero.orario == oraDaRecuperare.orario ) {

					risposta.add(d);
				}
			}

		}
		return risposta;
	}

}
