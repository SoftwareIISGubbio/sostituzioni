package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;


/**
 * Questo filtro serve per trovare i docenti che hanno dato disponibilità nel plesso Cassata o Gattapone
 * @author Flavio
 */

public class FiltroADisposizione {
	/**
	 * 
	 * @param tutti rappresenta un elenco di docenti
	 * @param oraCercata rappresenta l' ora a dispozizione da cercare
	 * @return ArrayList di docenti che hanno dato la disponibilità nel plesso Cassata o Gattapone
	 */
	public static ArrayList<Docente> docentiADisposizione(ArrayList<Docente> tutti, Ora oraCercata) {
		//lista dei docenti che hanno l'ora libera
		ArrayList<Docente> risposta = new ArrayList<>();
		//viene cercato il docente che è libero nell'ora richiesta
		for (Docente d : tutti) {
			//Cerco i docenti con l'ora disponibile nel plesso Cassata
			for(Ora o : d.oreADisposizioneCassata) {
				//se il giorno e l'orario libero del docente è uguale al giorno e l'orario cercato, il docente viene aggiunto all'ArrayList
				if (o.giorno == oraCercata.giorno && o.orario == oraCercata.orario) {
	                risposta.add(d);
	            }
			}
			//Cerco i docenti con l'ora disponibile nel plesso Gattapone
			for (Ora o : d.oreADisposizioneGattapone) {
				//se il giorno e l'orario libero del docente è uguale al giorno e l'orario cercato, il docente viene aggiunto all'ArrayList
				if (o.giorno == oraCercata.giorno && o.orario == oraCercata.orario) {
	                risposta.add(d);
	            }
			}
		}
		//ritorna un ArrayList di docenti
		return risposta;
	}

}