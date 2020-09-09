package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

/**
 * Questo filtro serve per trovare tutti i docenti in un dato gruppo
 * @author Edoardo
 */

public class FiltroGruppo {
	/**
	 * 
	 * @param tutti  rappresenta un elenco di docenti
	 * @param gruppo è il nome di un gruppo
	 * @param prendi se true prende i docenti dello stesso gruppo se false gli altri
	 * @return tutti i docenti che lavorano/non lavorano in quella classe
	 */
	public static ArrayList<Docente> docentiDelGruppo(ArrayList<Docente> tutti, String gruppo, boolean prendi) {
		// lista docenti della classe
		ArrayList<Docente> risposta = new ArrayList<>();
		// cerca il docente che lavora in quella classe nella lista di tutti i docenti
		if(prendi) {
    		for (Docente d : tutti) {
    			if (d.gruppo!=null && d.gruppo.equals(gruppo)) {
    				risposta.add(d);
    			}
    		}
		}else {
		    for (Docente d : tutti) {
                if (!d.gruppo.equals(gruppo)) {
                    risposta.add(d);
                }
            }
		}
		return risposta;
	}
}
