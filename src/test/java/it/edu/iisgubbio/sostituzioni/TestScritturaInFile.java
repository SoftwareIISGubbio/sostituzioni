package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class TestScritturaInFile {
	public static void main(String[] args) throws IOException {
		System.out.println(NomiFile.fileGiornale);
		Sostituzione s1 = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli");
		Sostituzione s2 = new Sostituzione(1, 4, "141", "4I", true, "Pallucca");
		ScritturaInFile.scriviRecord(s1);
		ScritturaInFile.scriviRecord(s2);
	}
}