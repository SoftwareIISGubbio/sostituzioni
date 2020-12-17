package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroADisposizione {
	
	public static ArrayList<Docente> docentiADisposizione(ArrayList<Docente> tutti, Ora oraCercata) {
		//lista dei docenti che hanno l'ora libera
		ArrayList<Docente> risposta = new ArrayList<>();
		//viene cercato il docente che è libero nell'ora richiesta
		for (Docente d : tutti) {
			for(Ora o : d.oreADisposizioneCassata) {
				//se il giorno e l'orario libero del docente è uguale al giorno e l'orario cercato, il docente viene aggiunto ad un ArrayList
				if (o.giorno == oraCercata.giorno && o.orario == oraCercata.orario) {
	                risposta.add(d);
	            }
			}
			for (Ora o : d.oreADisposizioneGattapone) {
				if (o.giorno == oraCercata.giorno && o.orario == oraCercata.orario) {
	                risposta.add(d);
	            }
			}
		}
		//ritorna un ArrayList di docenti
		return risposta;
	}

}