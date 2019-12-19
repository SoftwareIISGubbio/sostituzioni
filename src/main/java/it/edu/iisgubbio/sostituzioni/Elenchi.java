package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;
import java.util.HashSet;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class Elenchi {
    public static ArrayList<Docente> docenti = new ArrayList<>();
    
    static {
        docenti = LettoreFile.leggiXML();
        // leggo ulteriori informazioni dei docenti dal file XML
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
        
        
    }
    
    public static String[] getNomiClassi(){
        HashSet<String> insiemeNomiDiClassi = new HashSet<>();
        for(Docente docente: docenti) {
            for(OraLezione ol: docente.oreLezione) {
                insiemeNomiDiClassi.add(ol.classe);
            }
        }
        return insiemeNomiDiClassi.toArray( new String[0] );
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
