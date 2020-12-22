package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class TestScritturaGiornale {
	public static void main(String[] args) throws IOException {
		System.out.println(Ambiente.getFileGiornale());
		Sostituzione s1 = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli","2020-01-01");
		Sostituzione s2 = new Sostituzione(1, 4, "141", "4I", true, "Pallucca","2020-02-02");
		Giornale.scriviRecord(s1);
		Giornale.scriviRecord(s2);
	}
}