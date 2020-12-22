package it.edu.iisgubbio.sostituzioni.oggetti;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione.Motivo;

public class TestSostituzioni {
	public static void main(String[] args) {
		Sostituzione giammarioli;
		giammarioli = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli","2020-12-22");
		giammarioli.motivazione = Motivo.a_disposizione_stessa_classe;
		String testo = giammarioli.toString();
		Sostituzione nuova = new Sostituzione(testo);
		System.out.println(giammarioli);
		System.out.println(nuova);
		
		// testo il tipo enumerato, se nopn stampa niente: bene
		for (Motivo m : Motivo.values()) { 
		    String e = "\"Giammarioli\",\""+m+"\",\"4I\",\"2\",\"3\",\"154\",\"true\",\"2020-12-22\"";
		    Sostituzione s = new Sostituzione(e);
	        String k = s.toString();
	        if(!k.equals(e)) {
	            System.out.println(e);
	            System.out.println(k);
	        }
		}
	}
}
