package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione.Motivo;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Questo programma serve a gestire le letture o scritture delle sostituzioni in un file.
 * @author Loris
 */

public class Giornale {
    private static final int INDICE_NOME_SOSTITUO = 0;
    private static final int INDICE_MOTIVAZIONE = 1;
    private static final int INDICE_CLASSE = 2;
    private static final int INDICE_GIORNO = 3;
    private static final int INDICE_ORARIO = 4;
    private static final int INDICE_AULA = 5;
    private static final int INDICE_COPRESENZA = 6;
    private static final int NUMERO_CAMPI = 7;
    
    /** 
     * @param x è la sostituzione con le vari informazioni(giorno,ora,aula,classe,compresenza e nome docente)
     */
	public static void scriviRecord(Sostituzione x) {
		//serve per scrivere la sostituzione in un file
		BufferedWriter out = null;
		try
		{
			//se il codice che abbiamo scritto dentro il try dà problemi, il programma esegue ciò che è scritto sul catch
			out = new BufferedWriter(new FileWriter(Ambiente.getFileGiornale(), true));
			String v[] = new String[NUMERO_CAMPI];
			v[INDICE_NOME_SOSTITUO] = x.getNomeSostituto();
			v[INDICE_MOTIVAZIONE] = x.getMotivazione().toString();
			v[INDICE_CLASSE] = x.classe==null ? "?" : x.classe;
			v[INDICE_GIORNO] = ""+x.giorno;
			v[INDICE_ORARIO] = ""+x.orario;
			v[INDICE_AULA] = x.aula;
			v[INDICE_COPRESENZA] = ""+x.compresenza;
			out.write(CSV.toCSV(v)+"\n"); // qui scriviamo la sostituzione x e con "\n" si manda a capo
			out.close();
			
		} catch (Exception e) {
		    // FIXME: se si stampa su console non lo vede nessuno
			e.printStackTrace();
			// in caso dà errori, il programma deve stampare gli errori in console
		}
		Ambiente.aggiornaOreSvolteDocenti(); // Esitono altri modi molto più eleganti di farlo
	}
	
	public static ArrayList<Sostituzione> leggiGiornale() throws IOException {
	    try {
    		//serve per leggere da un file
    		BufferedReader reader = new BufferedReader(new FileReader(Ambiente.getFileGiornale()));
    		//prendiamo il file che vogliamo leggere
    		ArrayList<Sostituzione> risposta=new ArrayList<>();
    		String line;
    		while((line=reader.readLine())!=null) {
    		    // evitiamo problemi con le linee vuote
    		    if(line.trim().length()>0) {
    		        String v[] = CSV.fromCSV( line );
    		        int giorno = Integer.parseInt( v[INDICE_GIORNO] ); 
    		        int orario = Integer.parseInt( v[INDICE_ORARIO]); 
    		        String aula = v[INDICE_AULA];
    		        String classe = v[INDICE_CLASSE];
    		        boolean compresenza = v[INDICE_COPRESENZA].equals("true");
    		        String nomeSostituto = v[INDICE_NOME_SOSTITUO];
    		        
    		        Sostituzione x = new Sostituzione(giorno, orario, aula, classe, compresenza, nomeSostituto);
    		        x.setMotivazione( Motivo.valueOf(v[INDICE_MOTIVAZIONE]) );
    		        
    		        //dentro l'ArrayList risposta aggiungiamo la variabile creata precedente x
    		        risposta.add(x);
    		    }
    		}
    		
    		reader.close();
    		//chiudiamo il file
    		return risposta;
    		//ritorna tutte le righe che abbiamo letto
	    } catch (FileNotFoundException fnf){
	        System.err.println("************************************************");
	        Alert dialogoAllerta = new Alert(AlertType.ERROR, "il file del giornale \""+Ambiente.getFileGiornale()+"\" non esiste!");
	        dialogoAllerta.showAndWait();
	        Ambiente.impostaPreferenzeEEsci();
	    }
	    return null;
	}
}

