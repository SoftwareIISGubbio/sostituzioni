package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.Elenchi;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroCoPresenza;

public class CandidatoDocente extends Docente{
	
	public int livello;
	
	ArrayList<Docente> docentiOrdinati;
	
	public CandidatoDocente(String nome, int livello ){
		super(nome);
		this.livello=livello;

	}
	public void setLivello(int livello) {
		this.livello=livello;
		
	}
	
}

