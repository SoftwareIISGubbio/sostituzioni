package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

/************************************************************************************************
 * Rappresenta un docente con tutte le caratteristiche del suo orario
 ***********************************************************************************************/
public class Docente {
	public String nome;
	public ArrayList<OraLezione> oreLezione;
	public Ora oraARecupero;
	public ArrayList<Ora> oreADisposizioneCassata;
	public Ora oraADisposizioneGattapone;
	public ArrayList<Ora> oreAPagamento;
	public ArrayList<Ora> orePotenziamento;
	public String gruppo; // una cosa tipo "lettere" o "matematica" ...
	public int oreDaRecuperare;
	public int oreRecuperate;

	public Docente(String nome) {
		this.nome = nome;
		oreLezione = new ArrayList<>();
		oreAPagamento = new ArrayList<Ora>();
		orePotenziamento = new ArrayList<Ora>();
		oreADisposizioneCassata = new ArrayList<>();
		oreRecuperate = 0;
	}

	public String toString() {
		String risposta;
		risposta = nome;
		risposta = risposta + "\n  ora a recupero: " + oraARecupero;
		risposta = risposta + "\n  ora a disp. Gattapone: " + oraADisposizioneGattapone;
		risposta = risposta + "\n  ore a pagamento: ";
		for (int i = 0; i < oreAPagamento.size(); i++) {
		    risposta = risposta + oreAPagamento.get(i)+" ";
		}
		risposta = risposta + "\n  ore potenziamento: ";
        for (int i = 0; i < orePotenziamento.size(); i++) {
            risposta = risposta + orePotenziamento.get(i)+" ";
        }
        risposta = risposta + "\n  ore a disposizione Cassata: ";
        for (int i = 0; i < oreADisposizioneCassata.size(); i++) {
            risposta = risposta + oreADisposizioneCassata.get(i)+" ";
        }
        risposta = risposta + "\n  ore di lezione ";
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
	
	public String[] descriviGiornata(int giorno) {
	    String risposta[];
	    if(giorno==1) {
	        risposta = new String[8];
	    }else {
	        risposta = new String[6];
	    }
	    for(int i=0; i<risposta.length; i++) {
	        risposta[i] = "-";
	    }
	    for(OraLezione ol: oreLezione) {
	        if(ol.giorno==giorno) {
	            risposta[ol.orario-1] = ol.classe;
	        }
	    }
	    return risposta;
	}

}
