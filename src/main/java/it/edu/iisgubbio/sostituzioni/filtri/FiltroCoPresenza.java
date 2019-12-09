package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class FiltroCoPresenza {
	
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraDaSostituire ora in cui viene richiesta la sostituzione
	 * @return l'elenco di professori che sono in compresenza nella classe e nell'ora richiesta
	 */

	public static ArrayList<Docente> docentiCoPresenza(ArrayList<Docente> tutti, OraLezione oraDaSostituire) {
		ArrayList<Docente> risposta = new ArrayList<>();
		Ora ora = new Ora(oraDaSostituire.giorno, oraDaSostituire.orario);
		String classe = new String(oraDaSostituire.classe);

		for (Docente d : tutti) {
			if (d.lavoraNellaClasseInOra(ora, classe)) {

				risposta.add(d);
			}

		}
		return risposta;
	}

}
