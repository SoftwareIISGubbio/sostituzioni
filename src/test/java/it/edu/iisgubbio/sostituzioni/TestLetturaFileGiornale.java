package it.edu.iisgubbio.sostituzioni;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class TestLetturaFileGiornale {
	public static void main(String[] args) {		
		try {
			ArrayList<Sostituzione> sostituzione= Giornale.leggiGiornale(NomiFile.fileGiornale);
			for (int i=0; i < sostituzione.size(); i++) {
	            System.out.println(sostituzione.get(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}