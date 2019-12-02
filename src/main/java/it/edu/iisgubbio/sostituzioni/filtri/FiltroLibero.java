package it.edu.iisgubbio.sostituzioni.filtri;

import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

import java.util.ArrayList;


import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class FiltroLibero {

	
    public static ArrayList<Docente> docentiLiberi(ArrayList<Docente> tutti, Ora ora){
    	ArrayList<Docente> risposta = new ArrayList<>();
		for(Docente d: tutti) {
			if( !d.lavoraNellOra(ora) ) {
				risposta.add(d);
			}
		} 
		return risposta;
	}
    

}
