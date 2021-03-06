package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
/**
 * Questo programma serve a gestire le letture o scritture delle sostituzioni in un file.
 * @author Loris
 */

public class Giornale {    
    /** 
     * @param x è la sostituzione con le vari informazioni(giorno,ora,aula,classe,compresenza e nome docente)
     */
	public static void scriviRecord(Sostituzione x) {
		//serve per scrivere la sostituzione in un file
		BufferedWriter out = null;
		try
		{
			out = new BufferedWriter(new FileWriter(Ambiente.getFileGiornale(), true));
			out.write(x+"\n"); // qui scriviamo la sostituzione x e con "\n" si manda a capo
			out.close();
		} catch (Exception e) {
		    // FIXME: se si stampa su console non lo vede nessuno
			e.printStackTrace();
			// in caso dà errori, il programma deve stampare gli errori in console
		}
		Ambiente.aggiornaOreSvolteDocenti(); // Esistono altri modi molto più eleganti di farlo
	}
	
	public static ArrayList<Sostituzione> leggiGiornale() throws IOException {
		//serve per leggere da un file
		BufferedReader reader = new BufferedReader(new FileReader(Ambiente.getFileGiornale()));
		//prendiamo il file che vogliamo leggere
		ArrayList<Sostituzione> risposta=new ArrayList<>();
		String line;
		while((line=reader.readLine())!=null) {
		    // evitiamo problemi con le linee vuote
		    if(line.trim().length()>0) {
		        Sostituzione x = new Sostituzione(line);
		        //dentro l'ArrayList risposta aggiungiamo la variabile creata precedente x
		        risposta.add(x);
		    }
		}
		
		reader.close();
		//chiudiamo il file
		return risposta;
		//ritorna tutte le righe che abbiamo letto
	}
}

