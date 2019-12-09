package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

public class Docente {
	public String nome;
	public ArrayList<OraLezione> oreLezione;
	public Ora oraARecupero;
	public Ora oraADisposizioneCassata;
	public Ora oraADisposizioneGattapone;
	public ArrayList<Ora> oreAPagamento;
	public ArrayList<Ora> orePotenziamento;

	public Docente(String nome) {
		this.nome = nome;
		oreLezione = new ArrayList<>();
		oreAPagamento = new ArrayList<Ora>();
		orePotenziamento = new ArrayList<Ora>();
	}

	public String toString() {
		String risposta;
		risposta = nome;
		for (int i = 0; i < oreLezione.size(); i++) {
			risposta = risposta + "\n  " + oreLezione.get(i);
		}
		return risposta;
	}

	public boolean lavoraNellaClasse(String nomeClasse) {
		for (int i = 0; i < oreLezione.size(); i++) {
			if (oreLezione.get(i).classe.equals(nomeClasse)) {
				return true;
			}

		}
		return false;
	}

	public boolean lavoraNellOra(Ora ora) {
		for (int i = 0; i < oreLezione.size(); i++) {
			if (oreLezione.get(i).giorno == ora.giorno && oreLezione.get(i).orario == ora.orario) {
				return true;
			}
		}
		return false;
	}

	public boolean lavoraNellaClasseInOra(Ora ora, String classe) {
		for (int i = 0; i < oreLezione.size(); i++) {
			if (oreLezione.get(i).giorno == ora.giorno && oreLezione.get(i).orario == ora.orario
					&& oreLezione.get(i).classe.equals(classe)) {
				return true;
			}
		}
		return false;
	}

}
