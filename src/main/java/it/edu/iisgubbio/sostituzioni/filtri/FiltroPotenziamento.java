package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;


/**
 * Questo filtro serve per trovare i docenti di potenziamento disponibili
 * @author Cristian
 */

public class FiltroPotenziamento {
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param ora rappresenta l'ora cercata
	 * @return tutti i docenti che hanno un'ora di potenziamento nell'ora specificata
	 */
	public static ArrayList<Docente> docentiPotenziamento(ArrayList<Docente> tutti, Ora oraDaPagare) {
		//lista dei docenti che hanno l'ora di potenziamento
		ArrayList<Docente> risposta = new ArrayList<>();

		for (Docente d : tutti) {
			for (Ora o : d.orePotenziamento) {
				//se il giorno e l'orario di potenziamento del docente è uguale al giorno e l'orario cercato
			    // il docente viene aggiunto alla risposta
				if (o.giorno == oraDaPagare.giorno && o.orario == oraDaPagare.orario) {
					risposta.add(d);
				}
			}
		}
		return risposta;
	}

}
