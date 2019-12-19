package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroRecupero {

	public static ArrayList<Docente> docentiRecupero(ArrayList<Docente> tutti, Ora oraDaRecuperare) {
		ArrayList<Docente> risposta = new ArrayList<>();
		
		for (Docente d : tutti) {
			if(d.oraARecupero!=null) {
				if (d.oraARecupero.giorno == oraDaRecuperare.giorno && d.oraARecupero.orario == oraDaRecuperare.orario ) {

					risposta.add(d);
				}
			}

		}
		return risposta;
	}

}
