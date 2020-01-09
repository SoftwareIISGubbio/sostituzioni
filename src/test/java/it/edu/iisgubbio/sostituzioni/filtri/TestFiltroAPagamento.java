package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroAPagamento {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiLiberi;
		long inizio = System.currentTimeMillis();
		Ora oraCercata = new Ora(1, 7);
		docentiLiberi = FiltroAPagamento.docentiAPagamento(tuttiIDocenti,oraCercata);
		long fine = System.currentTimeMillis();
        System.out.println("tempo impiegato: "+(fine-inizio)+"msec");
        
		for (int i = 0; i < docentiLiberi.size(); i++) {
			System.out.println(docentiLiberi.get(i));
		}
	}

}
