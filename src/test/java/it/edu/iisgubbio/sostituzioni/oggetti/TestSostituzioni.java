package it.edu.iisgubbio.sostituzioni.oggetti;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione.Motivo;

public class TestSostituzioni {
	public static void main(String[] args) {
		Sostituzione giammarioli;
		giammarioli = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli");
		String testo = giammarioli.toString();
		Sostituzione nuova = new Sostituzione(testo);
		System.out.println(giammarioli);
		System.out.println(nuova);
		
		// testo il tipo en umerato
		for (Motivo m : Motivo.values()) { 
		    String e = "CESARINI,"+m+",3c,1,1,190LAB GRAFICA,sostituzione";
		    Sostituzione s = new Sostituzione(e);
	        String k = s.toString();
	        if(!k.equals(e)) {
	            System.out.println(e);
	            System.out.println(k);
	        }
		}
	}
}
