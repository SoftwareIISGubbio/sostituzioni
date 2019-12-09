package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;


public class TestExcel {

	public static void main(String[] args) {
		ArrayList<Docente> lista = LettoreFile.leggiExcel();
		
		for(int i = 0; i<lista.size(); i++) {
			System.out.println(lista.get(i));
		}
	}
}
