package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
			//se il codice che abbiamo scritto dentro il try dà problemi, il programma esegue ciò che è scritto sul catch
			out = new BufferedWriter(new FileWriter(Ambiente.getFileGiornale(), true));
			out.write(x+"\n");
			// qui scriviamo la sostituzione x e con "\n" si manda a capo
			out.close();
			// chiude il file
			
		} catch (Exception e) {
		    // FIXME: se si stampa su console non lo vede nessuno
			e.printStackTrace();
			// in caso dà errori, il programma deve stampare gli errori in console
		}
		Ambiente.aggiornaOreSvolteDocenti(); // Esitono altri modi molto più eleganti di farlo
	}
	
	public static ArrayList<Sostituzione> leggiGiornale(File fileGiornale) throws FileNotFoundException, IOException {
		//serve per leggere da un file
		BufferedReader reader = new BufferedReader(new FileReader(fileGiornale));
		//prendiamo il file che vogliamo leggere
		ArrayList<Sostituzione> risposta=new ArrayList<>();
		String line;
		while((line=reader.readLine())!=null) {
		// facciamo un while che continua finchè una riga è null, quindi abbiamo letto tutto ciò che è scritto nel file
		     Sostituzione x = new Sostituzione(line);
			// nella variabile appena creata x scriviamo tutta la riga che abbiamo appena letto
		     risposta.add(x);
			//dentro l'ArrayList risposta aggiungiamo la variabile creata precedente x
		}
		
		reader.close();
		//chiudiamo il file
		return risposta;
		//ritorna tutte le righe che abbiamo letto 
	}
}

