package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroAPagamento {

	public static ArrayList<Docente> docentiAPagamento(ArrayList<Docente> tutti, Ora oraDaPagare) {
		ArrayList<Docente> risposta = new ArrayList<>();

		for (Docente d : tutti) {
			for (Ora o : d.oreAPagamento) {
				if (o.giorno == oraDaPagare.giorno && o.orario == oraDaPagare.orario) {

					risposta.add(d);
				}

			}

		}
		return risposta;
	}

}
