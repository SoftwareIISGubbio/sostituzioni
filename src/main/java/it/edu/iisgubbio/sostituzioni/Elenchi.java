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
        
        System.out.println(informazioniExcel.get(0));
        for(Docente daExcel : informazioniExcel) {
            Docente presente = cercaDocentePerNome(daExcel.nome);
            if(presente!=null) {
                presente.oraARecupero = daExcel.oraARecupero;
            }
        }
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
        String primaParte;
        int numeroCorrispondenze = 0; // devo contarle perché se combaviano in due non va bene
        for(Docente x: docenti) {
            // è possibile che il nome cercato sia più lungo di quello presente nell'elenco
            // perché nel file excel a volte vengono inseriti i nomi mentre nel file xml quasi mai
            primaParte = nome.substring(0, Math.min(x.nome.length(), nome.length() ));
            if(x.nome.toLowerCase().equals(primaParte.toLowerCase())) {
                trovato = x;
                numeroCorrispondenze++;
            }
        }
        if(numeroCorrispondenze==1) {
            return trovato;
        }
        return null;
    }
}
