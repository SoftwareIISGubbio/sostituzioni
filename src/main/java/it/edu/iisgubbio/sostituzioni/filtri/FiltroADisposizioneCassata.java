package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;


/**
 * Questo filtro serve per trovare i docenti che hanno messo l'ora a disposizione
 * @author Cristian
 */

public class FiltroADisposizioneCassata {
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraCercata rappresenta l' ora a dispozizione da cercare
	 * @return tutti i docenti che hanno l'ora libera e che deve essere pagata
	 */
	public static ArrayList<Docente> docentiADisposizioneCassata(ArrayList<Docente> tutti, Ora oraCercata) {
		//lista dei docenti che hanno l'ora libera
		ArrayList<Docente> risposta = new ArrayList<>();
		//viene cercato il docente che è libero nell'ora richiesta
		for (Docente d : tutti) {
			//se il giorno e l'orario libero del docente è uguale al giorno e l'orario cercato, il docente viene aggiunto ad un ArrayList 
			if (d.oraADisposizioneCassata!=null && d.oraADisposizioneCassata.giorno == oraCercata.giorno && d.oraADisposizioneCassata.orario == oraCercata.orario) {
			    risposta.add(d);
			}
		}
		//ritorna un ArrayList di docenti
		return risposta;
	}

}
