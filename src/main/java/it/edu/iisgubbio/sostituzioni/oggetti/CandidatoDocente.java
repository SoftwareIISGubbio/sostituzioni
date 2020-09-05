package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

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

