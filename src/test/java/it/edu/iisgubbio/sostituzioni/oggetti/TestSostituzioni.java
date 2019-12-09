package it.edu.iisgubbio.sostituzioni.oggetti;


import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class TestSostituzioni {
	public static void main(String[] args) {
		Sostituzione giammarioli;
		giammarioli = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli");
		String testo = giammarioli.toString();
		Sostituzione nuova = new Sostituzione(testo);
		System.out.println(giammarioli);
		System.out.println(nuova);
	}
}
