package it.edu.iisgubbio.sostituzioni.filtri;

import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

/**
 * Questo filtro serve per trovare i docenti liberi nell'ora richiesta
 * @author Bianca
 */
public class FiltroLibero {
	
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param ora rappresenta l' ora da ricercare
	 * @return tutti i docenti liberi in quell'ora
	 */
	public static ArrayList<Docente> docentiLiberi(ArrayList<Docente> tutti, Ora ora) {
		// lista docenti liberi
		ArrayList<Docente> risposta = new ArrayList<>();
		// cerca il docente libero nella lista di tutti i docenti
		for (Docente d : tutti) {
		// se un docente non lavora nell'ora inserita, lo aggiunge alla lista dei docenti liberi
			if (!d.lavoraNellOra(ora)) {
				risposta.add(d);
			}
		}
		return risposta;
	}

}
