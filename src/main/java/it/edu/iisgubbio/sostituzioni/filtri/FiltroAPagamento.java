package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;


/**
 * Questo filtro serve per trovare i docenti che sono liberi nell'ora richiesta che devono essere pagati
 * @author Cristian
 */

public class FiltroAPagamento {
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraDaPagare rappresenta l' ora da pagare da ricercare
	 * @return tutti i docenti che hanno l'ora libera che deve essere pagata
	 */
	public static ArrayList<Docente> docentiAPagamento(ArrayList<Docente> tutti, Ora oraDaPagare) {
		//lista dei docenti che hanno l'ora libera
		ArrayList<Docente> risposta = new ArrayList<>();
		//viene cercato il docente che è libero nell'ora richiesta
		for (Docente d : tutti) {
			for (Ora o : d.oreAPagamento) {
				//se il giorno e l'orario libero del docente è uguale al giorno e l'orario cercato, il docente viene aggiunto ad un ArrayList 
				if (o.giorno == oraDaPagare.giorno && o.orario == oraDaPagare.orario) {
			
					risposta.add(d);
				}

			}

		}
		//ritorna un ArrayList di docenti
		return risposta;
	}

}
