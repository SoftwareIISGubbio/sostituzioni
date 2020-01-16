package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class Elenchi {
    public static ArrayList<Docente> docenti = new ArrayList<>();
    private static String[]nomiClassi;
    private static String problemi = "";
    
    /********************************************************************************************
     * Questo codice viene eseguito al momento del caricamento della classe in memoria
     * TODO: forse sarebbe il caso di farlo diventare un normale oggetto e togliere tutte
     * le proprietà static
     *******************************************************************************************/
    static {
        docenti = LettoreFile.leggiXML();
        // leggo ulteriori informazioni dei docenti dal file xlsx
        ArrayList<Docente> informazioniExcel = LettoreFile.leggiExcel();
        
        for(Docente daExcel : informazioniExcel) {
            Docente presente = cercaDocentePerNome(daExcel.nome);
            if(presente!=null) {
                presente.oraARecupero = daExcel.oraARecupero;
                presente.oraADisposizioneCassata = daExcel.oraADisposizioneCassata;
                presente.oraADisposizioneGattapone = daExcel.oraADisposizioneGattapone;
                presente.oraARecupero = daExcel.oraARecupero;
                presente.oreAPagamento = daExcel.oreAPagamento;
                presente.orePotenziamento = daExcel.orePotenziamento;
            }else {
            	docenti.add(daExcel);
            }
        }
        LettoreFile.leggiProfSostegno(docenti);
        
        // creo elenco con nomi di classi e controllo se c'è qualche nome strano
        HashSet<String> insiemeNomiDiClassi = new HashSet<>();
        String nomeClasse;
        for(Docente docente: docenti) {
            for(OraLezione ol: docente.oreLezione) {
                nomeClasse = ol.classe.toLowerCase();
                // ci facciamo andar bene:
                //   [\\[(]? → una parentesi quadra o tonda aperta se c'è
                //   [1-5] → un numero da 1 a 5
                //   [a-z] → una lettera
                //   [\\-1-5a-z]* → una sequenza di uno o più lettere numeri e "-" 
                //   [\\])]? → una parentesi quadra o tonda chiusa se c'è
                if( nomeClasse.matches("[\\[(]?[1-5][a-z][\\-1-5a-z]*[\\])]?") || nomeClasse.equals("gatt")){
                    insiemeNomiDiClassi.add(nomeClasse);
                } else {
                	if(!nomeClasse.equals("vp") ) {
                		problemi += "classe \""+nomeClasse+"\" di "+docente.nome+"("+ol+")\n";
                	}
                }
            }
        }
        nomiClassi = insiemeNomiDiClassi.toArray( new String[0] );
        Arrays.sort(nomiClassi);
    }
    
    public static String[] getNomiClassi(){
        return nomiClassi;
    }
    
    public static String getProblemi() {
        return problemi;
    }
    
    
    /********************************************************************************************
     * 
     * @param nome del docente da cercare
     * @return il docente cercato o null se non l'ha trovato
     *******************************************************************************************/
    static Docente cercaDocentePerNome(String nome) {
        // prima ricerca: per corrispondenza esatta
        for(Docente x: docenti) {
            if(x.nome.toLowerCase().equals(nome.toLowerCase())) {
                return x;
            }
        }
        
        // vedo se esiste un solo docente che inizia come quello passato
        Docente trovato = null;
        String split[] = nome.split(" ");
        String cognome = split[0];
        int numeroCorrispondenze = 0; // devo contarle perché se combaviano in due non va bene
        for(Docente x: docenti) {
        	String nomeDocente = x.nome.toLowerCase().trim().split(" ")[0];
            // è possibile che il nome cercato sia più lungo di quello presente nell'elenco
            // perché nel file excel a volte vengono inseriti i nomi mentre nel file xml quasi mai
            if(nomeDocente.equals(cognome.toLowerCase())) {
                trovato = x;
                numeroCorrispondenze++;
            }
        }
    	
        
        if(numeroCorrispondenze==1) {
            return trovato;
        }else if(numeroCorrispondenze == 2) {
        	String nomeDocente;
        	if(split.length > 2) {
        		nomeDocente = split[0]+" "+split[1];
        	}else {
        		nomeDocente = nome;
        	}
        	
        	for(Docente x: docenti) {
        		if(x.nome.toLowerCase().equals(nomeDocente.toLowerCase())) {
        			return x;
        		}
        	}
        }
        return null;
    }
}
