package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class FiltroClasse {
	/**
	 * 
	 * @param tutti  rappresenta un elenco di docenti
	 * @param classe rappresenta la classe da ricercare
	 * @return tutti i docenti che lavorano in quella classe
	 */
	public static ArrayList<Docente> docentiDellaClasse(ArrayList<Docente> tutti, String classe) {
		ArrayList<Docente> risposta = new ArrayList<>();
		for (Docente d : tutti) {
			if (d.lavoraNellaClasse(classe)) {
				risposta.add(d);
			}
		}
		return risposta;
	}
}
