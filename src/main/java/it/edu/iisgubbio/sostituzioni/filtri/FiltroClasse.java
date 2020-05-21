package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

/**
 * Questo filtro serve per trovare tutti i docenti della classe che ha bisogno della sostituzione
 * @author Bianca
 */

public class FiltroClasse {
	/**
	 * 
	 * @param tutti  rappresenta un elenco di docenti
	 * @param classe rappresenta la classe da ricercare
	 * @return tutti i docenti che lavorano in quella classe
	 */
	public static ArrayList<Docente> docentiDellaClasse(ArrayList<Docente> tutti, String classe) {
		// lista docenti della classe
		ArrayList<Docente> risposta = new ArrayList<>();
		// cerca il docente che lavora in quella classe nella lista di tutti i docenti
		for (Docente d : tutti) {
			if (d.lavoraNellaClasse(classe)) {
				risposta.add(d);
			}
		}
		return risposta;
	}
}
