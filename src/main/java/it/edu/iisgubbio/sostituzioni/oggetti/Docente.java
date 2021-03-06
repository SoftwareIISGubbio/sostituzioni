package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

/************************************************************************************************
 * Rappresenta un docente con tutte le caratteristiche del suo orario
 ***********************************************************************************************/
public class Docente implements Comparable<Docente>{
	public String nome;
	public ArrayList<OraLezione> oreLezione;
	public Ora oraARecupero;
	public ArrayList<Ora> oreADisposizioneCassata;
	public ArrayList<Ora> oreADisposizioneGattapone;
	public ArrayList<Ora> oreAPagamento;
	public ArrayList<Ora> orePotenziamento;
	public String gruppo; // una cosa tipo "lettere" o "matematica" ...
	public int oreDaRecuperare;
	public int oreRecuperate;

	public Docente(String nome) {
		this.nome = nome;
		gruppo = "";
		oreLezione = new ArrayList<>();
		oreAPagamento = new ArrayList<Ora>();
		orePotenziamento = new ArrayList<Ora>();
		oreADisposizioneCassata = new ArrayList<>();
		oreADisposizioneGattapone = new ArrayList<>();
		oreRecuperate = 0;
	}
	
    @Override
    public int compareTo(Docente altro) {
        return nome.compareTo(altro.nome);
    }

	public String toString() {
		String risposta;
		risposta = nome;
		risposta = risposta + "\n  ora a recupero: " + oraARecupero;
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
        risposta = risposta + "\n  ore a disposizione Gattapone: ";
        for (int i = 0; i < oreADisposizioneGattapone.size(); i++) {
            risposta = risposta + oreADisposizioneGattapone.get(i)+" ";
        }
        risposta = risposta + "\n  ore di lezione ";
		for (int i = 0; i < oreLezione.size(); i++) {
			risposta = risposta + "\n  " + oreLezione.get(i);
		}
		return risposta;
	}
	
	public int oreOccupateNellaMattina(int giorno) {
	    int n=0;
	    for(OraLezione ol: oreLezione) {
	        if(ol.giorno==giorno) {
	            n++;
	        }
	    }
	    return n;
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
	 * @return true se il docente lavora nell'ora specificata nella classe richiesta
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
	
	/********************************************************************************************
	 * confronta per giorno, ora e classe
	 * @param oraCercata
	 * @return l'ora di questo docente che combacia con quella cercata o null se non esiste
	 *******************************************************************************************/
    public OraLezione getOra(OraLezione oraCercata) {
        String classiCercate[] = oraCercata.classe.replaceAll("[\\[\\]\\(\\)]", "").split("-");
        for (int i = 0; i < oreLezione.size(); i++) {
            if (oreLezione.get(i).giorno == oraCercata.giorno 
                    && oreLezione.get(i).orario == oraCercata.orario) {
                String mieClassi[] = oreLezione.get(i).classe.replaceAll("[\\[\\]\\(\\)]", "").split("-");
                    // && oreLezione.get(i).classe.equals(oraCercata.classe)) {
                for(String c1: classiCercate) {
                    for(String c2: mieClassi) {
                        if(c1.equals(c2)) {
                            return oreLezione.get(i);
                        }
                    }
                }
            }
        }
        return null;
    }
	
	public String[] descriviGiornata(int giorno) {
	    String risposta[];
	    // siccome alcuni insegnanti hanno ore il pomeriggio degli altri giorni
	    risposta = new String[8];
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
