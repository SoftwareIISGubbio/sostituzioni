package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.filtri.RimozioneDocente;

public class TestRimozioneDocente {
	

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiRestanti;
		long inizio = System.currentTimeMillis();
		docentiRestanti = RimozioneDocente.docentiRimozione(tuttiIDocenti, "STIPA MASSIMO");
		long fine = System.currentTimeMillis();
		System.out.println("tempo impiegato: "+(fine-inizio)+"msec");

		System.out.println("I docenti totali sono: " + tuttiIDocenti.size());

		for (int i = 0; i < docentiRestanti.size(); i++) {
			System.out.println(docentiRestanti.get(i).nome);

		}

	}

}
