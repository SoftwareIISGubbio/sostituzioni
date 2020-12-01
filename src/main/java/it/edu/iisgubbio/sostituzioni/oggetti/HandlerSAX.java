package it.edu.iisgubbio.sostituzioni.oggetti;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerSAX extends DefaultHandler{
    
    ArrayList<Docente> docenti;
    Docente docenteAttuale = null;
    OraLezione oraAttuale = null;
    int giornoAttuale = 0;

    public HandlerSAX(ArrayList<Docente> ald) {
        this.docenti = ald;
    }
    
    /**
     * legge il file XML e a seconda del tag inserisce il giorno, la classe, la materia, e l'aula
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts){
        if(qName.equals("Teacher")){
            docenteAttuale = new Docente(atts.getValue("name").trim().toUpperCase());
            docenti.add(docenteAttuale);
        }
        if(qName.equals("Day")){
            String stringaGiorno = atts.getValue("name");
            
            for(int i = 1; i<Ora.nomiGiorni.length; i++) {
            	if(stringaGiorno.equals(Ora.nomiGiorni[i])) {
            		giornoAttuale = i;
            		break;
            	}
            }
            
        }
        if(qName.equals("Hour")){
            String ora = atts.getValue("name");
            oraAttuale = null; // mi serve perché l'ora "13:35" che compare sul file di fet non è in realtà una ora di lezione
            for(int i = 1; i<Ora.nomiOre.length; i++) {
            	if(Ora.nomiOre[i].startsWith(ora)) {
            		oraAttuale = new OraLezione(giornoAttuale, i);
            		break;
            	}
            }
            if(oraAttuale==null) {
                // System.err.println("Non riconosco l'ora \""+ora+"\"");
            }
        }
        if(qName.equals("Subject")){
            if(oraAttuale==null) {
                System.err.println("ora nulla con Subject \""+atts.getValue("name")+"\"");
            } else {
                oraAttuale.materia = atts.getValue("name");
            }
        }
        if(qName.equals("Students")){
            if(oraAttuale==null) {
                System.err.println("ora nulla per Students \""+atts.getValue("name")+"\"");
            } else {
                oraAttuale.classe = atts.getValue("name").trim().replaceAll(" ", "").toLowerCase();
            }
        }
        if(qName.equals("Room")){
            if(oraAttuale==null) {
                System.err.println("ora nulla per Room \""+atts.getValue("name")+"\"");
            } else {
                oraAttuale.aula = atts.getValue("name").trim();
            }
        } 
    }
    
    /**
     * controlla l'ultimo elemento del file
     */
    @Override
    public void endElement(String uri, String localName, String qName){
        if( qName.equals("Hour") && oraAttuale!=null && oraAttuale.materia!=null){
            docenteAttuale.oreLezione.add(oraAttuale);
        }
    }
    
}
