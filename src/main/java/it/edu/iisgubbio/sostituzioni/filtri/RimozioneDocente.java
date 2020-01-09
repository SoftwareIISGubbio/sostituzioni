package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class RimozioneDocente {

	public static ArrayList<Docente> docentiRimozione(ArrayList<Docente> tutti, String docenteDaRimuovere) {
		ArrayList<Docente> risposta = new ArrayList<>();

		for (Docente d : tutti) {
			if (!d.nome.equals(docenteDaRimuovere)) {
				risposta.add(d);
			}

		}
		return risposta;
	}

}
