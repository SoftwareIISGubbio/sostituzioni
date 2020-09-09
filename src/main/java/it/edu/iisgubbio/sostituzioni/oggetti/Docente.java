package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

/************************************************************************************************
 * Rappresenta un docente con tutte le caratteristiche del suo orario
 ***********************************************************************************************/
public class Docente {
	public String nome;
	public ArrayList<OraLezione> oreLezione;
	public Ora oraARecupero;
	public Ora oraADisposizioneCassata;
	public Ora oraADisposizioneGattapone;
	public ArrayList<Ora> oreAPagamento;
	public ArrayList<Ora> orePotenziamento;
	public String gruppo; // una cosa tipo "lettere" o "matematica" ...
	public int oreDaRecuperare;

	public Docente(String nome) {
		this.nome = nome;
		oreLezione = new ArrayList<>();
		oreAPagamento = new ArrayList<Ora>();
		orePotenziamento = new ArrayList<Ora>();
	}

	public String toString() {
		String risposta;
		risposta = nome;
		risposta = risposta + "\n  ora a recupero: " + oraARecupero;
		risposta = risposta + "\n  ora a disp. Cassata: " + oraADisposizioneCassata;
		risposta = risposta + "\n  ora a disp. Gattapone: " + oraADisposizioneGattapone;
		risposta = risposta + "\n  ore a pagamento: ";
		for (int i = 0; i < oreAPagamento.size(); i++) {
		    risposta = risposta + oreAPagamento.get(i)+" ";
		}
		risposta = risposta + "\n  ore potenziamento: ";
        for (int i = 0; i < orePotenziamento.size(); i++) {
            risposta = risposta + orePotenziamento.get(i)+" ";
        }
		for (int i = 0; i < oreLezione.size(); i++) {
			risposta = risposta + "\n  " + oreLezione.get(i);
		}
		return risposta;
	}

	/********************************************************************************************
	 * @param nomeClasse 
	 * @return true se il docente ha un'ora di lezione nella classe specificata
	 *******************************************************************************************/
	public boolean lavoraNellaClasse(String nomeClasse) {
		for (int i = 0; i < oreLezione.size(); i++) {
			if (oreLezione.get(i).classe.equals(nomeClasse)) {
				return true;
			}
		}
		return false;
	}

	/********************************************************************************************
	 * 
	 * @param ora
	 * @return true se il docente lavora nell'ora specificata
	 *******************************************************************************************/
	public boolean lavoraNellOra(Ora ora) {
		for (int i = 0; i < oreLezione.size(); i++) {
			if (oreLezione.get(i).giorno == ora.giorno && oreLezione.get(i).orario == ora.orario) {
				return true;
			}
		}
		return false;
	}

	/********************************************************************************************
	 * @param ora
	 * @param classe
	 * @return true se il docente lavotra nell'ora specificata nella classe richiesta
	 *******************************************************************************************/
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
