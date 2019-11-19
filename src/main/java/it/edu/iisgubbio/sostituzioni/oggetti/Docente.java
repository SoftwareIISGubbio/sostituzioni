package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

public class Docente {
	private String nome;
	private ArrayList<OraLezione> oreLezione;
	private Ora oraARecupero;
	private Ora oraADisposizioneCassata;
	private Ora oraADisposizioneGattapone;
	private ArrayList<Ora> oreAPagamento;
	private ArrayList<Ora> orePotenziamento;
	
	public Docente(String nome) {
		this.nome = nome;
		oreLezione = new ArrayList<>();
		oreAPagamento = new ArrayList<Ora>();
		orePotenziamento = new ArrayList<Ora>();
	}
	
	public void addOraLezione( OraLezione ol ) {
		oreLezione.add(ol);
	}
	
	public void addOraPagamento( Ora op ) {
		oreAPagamento.add(op);
	}
	
	public void addOraPotenziamento( Ora op) {
		orePotenziamento.add(op);
	}
	
	public Ora getOraARecupero() {
		return oraARecupero;
	}

	public void setOraARecupero(Ora oraARecupero) {
		this.oraARecupero = oraARecupero;
	}

	public Ora getOraADisposizioneCassata() {
		return oraADisposizioneCassata;
	}

	public void setOraADisposizioneCassata(Ora oraADisposizioneCassata) {
		this.oraADisposizioneCassata = oraADisposizioneCassata;
	}

	public Ora getOraADisposizioneGattapone() {
		return oraADisposizioneGattapone;
	}

	public void setOraADisposizioneGattapone(Ora oraADisposizioneGattapone) {
		this.oraADisposizioneGattapone = oraADisposizioneGattapone;
	}
	
	public String toString() {
		String risposta;
		risposta = nome;
		for(int i=0; i<oreLezione.size() ;i++) {
			risposta = risposta + "\n  "+oreLezione.get(i);
		}
		return risposta;
	}
	
}
