package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

/**
 * Questo filtro serve per trovare i docenti in copresenza nell'ora della classe in cui c'è bisogno della sostituzione
 * @author Bianca
 */
public class FiltroCoPresenza {

	/**
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraDaSostituire ora in cui viene richiesta la sostituzione
	 * @return l'elenco dei professori che sono in copresenza nella classe e nell'ora richiesta
	 */

	public static ArrayList<Docente> docentiCoPresenza(ArrayList<Docente> tutti, OraLezione oraDaSostituire) {
		// lista dei docenti in copresenza
		ArrayList<Docente> risposta = new ArrayList<>();
		// ricerca del docente in copresenza tra tutti i docenti
		for (Docente d : tutti) {
			// se il docente è presente nell'ora inserita nella classe che necessita la sostituzione significa che è in copresenza 
		    if(d.getOra(oraDaSostituire)!=null) {
				risposta.add(d);
			}

		}
		return risposta;
	}

}
