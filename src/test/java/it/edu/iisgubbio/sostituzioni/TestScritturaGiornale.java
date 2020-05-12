package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class TestScritturaGiornale {
	public static void main(String[] args) throws IOException {
		System.out.println(Ambiente.getFileGiornale());
		Sostituzione s1 = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli");
		Sostituzione s2 = new Sostituzione(1, 4, "141", "4I", true, "Pallucca");
		Giornale.scriviRecord(s1);
		Giornale.scriviRecord(s2);
	}
}