package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class TestFiltroOreBuche {

	public static void main(String[] args) {
		ArrayList<Docente> tuttiIDocenti = Ambiente.getDocenti();
		ArrayList<Docente> docentiConOraBuca;
		Ora oraNecessaria = new Ora(3 , 5);

		docentiConOraBuca = FiltroOreBuche.docentiOreBuche(tuttiIDocenti, oraNecessaria);
		for (int i = 0; i < docentiConOraBuca.size(); i++) {
			System.out.println(docentiConOraBuca.get(i));
		}
		
	}

}
