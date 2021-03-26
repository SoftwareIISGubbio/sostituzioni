package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroOreAdiacenti {
	/**
	 * @param tutti          rappresenta un elendo dei docenti
	 * @param ora rappresenta l' ora da ricercare
	 * @return tutti i docenti con un ora libera nell'ora richiesta
	 */
	public static ArrayList<Docente> docentiOccupatiOreAdiacenti(ArrayList<Docente> tutti, Ora ora) {
		// lista dei docenti che sono occupati nelle ore adiacenti
		ArrayList<Docente> risposta = new ArrayList<>();
		// cerco tutti i docenti con un'ora disponibile nell'ora richiesta
		// ma occupati in quelle adiacenti
		for (Docente d : tutti) {
			// Se il docente lavora già nell'ora richiesta viene saltato
			if (d.lavoraNellOra(ora)) {
				continue;
			}
			
			// Se il docente lavora nell'ora precedente o in quella successiva viene
			// considerato come disponibile per la sostituzione
			if (ora.orario + 1 <= 8
					&& d.lavoraNellOra(new Ora(ora.giorno, ora.orario + 1))
					|| ora.orario - 1 >= 1
							&& d.lavoraNellOra(new Ora(ora.giorno, ora.orario - 1))) {
				risposta.add(d);
			}
		}

		return risposta;

	}
}
