package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

/**
 * questo filtro serve per trovare tutti i docenti con l'ora buca nell'ora
 * ricercata
 * 
 * @author Nicola
 */

public class FiltroOreBuche {
	/**
	 * @param tutti    rappresenta un elendo dei docenti
	 * @param oreBuche é l'ora buca del docente che viene ricercata
	 * @return tutti i docenti con un ora libera nell'ora richiesta
	 */
	public static ArrayList<Docente> docentiOreBuche(ArrayList<Docente> tutti, Ora oreBuche) {
		// lista dei docenti che hanno l'ora libera
		ArrayList<Docente> risposta = new ArrayList<>();
		// definisco i parametri di oreBuche
		Ora ora = new Ora(oreBuche.giorno, oreBuche.orario);
		// cerco tutti i docenti con un ora buca nell'ora richiesta
		for (Docente d : tutti) {
			/*
			 * for (int m = 1; m <= 2; m++) { for (int p = 1; p <= 2; p++) { if (m == 2 && p
			 * == 2) { } else { if (!d.lavoraNellOra(ora)) { Ora oraPrima = new
			 * Ora(ora.giorno, ora.orario - m); Ora oraDopo = new Ora(ora.giorno, ora.orario
			 * - p); if (d.lavoraNellOra(oraPrima) && d.lavoraNellOra(oraDopo)) {
			 * risposta.add(d); }
			 * 
			 * } } }
			 * 
			 * }
			 */
			// definisco OraMenoUno che equivale all'ora prima dell'ora cercata per
			// verificare che sia un ora buca
			Ora OraMenoUno = new Ora(ora.giorno, ora.orario - 1);
			// definisco OraMenoDue che equivale a 2 ore prima dell'ora cercata per
			// verificare che sia un ora buca
			Ora OraMenoDue = new Ora(ora.giorno, ora.orario - 2);
			// definisco OraPiuUno che equivale all'ora dopo dell'ora cercata per verificare
			// che sia un ora buca
			Ora OraPiuUno = new Ora(ora.giorno, ora.orario + 1);
			// definisco OraPiuDue che equivale a 2 ore dopo dell'ora cercata per verificare
			// che sia un ora buca
			Ora OraPiuDue = new Ora(ora.giorno, ora.orario + 2);
			// controlliamo se il docente lavora nell'ora cercata
			if (!d.lavoraNellOra(ora)) {
				// controllo se lavora l'ora prima e l'ora dopo l'ora richiesta
				if (d.lavoraNellOra(OraMenoUno) && d.lavoraNellOra(OraPiuUno)
						|| d.lavoraNellOra(OraMenoDue) && d.lavoraNellOra(OraPiuUno)
						|| d.lavoraNellOra(OraMenoUno) && d.lavoraNellOra(OraPiuDue)) {
					if (!risposta.contains(d))
						risposta.add(d);
				}
			}

		}

		// riporto un ArrayList di docenti
		return risposta;

	}

}
