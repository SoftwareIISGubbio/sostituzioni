package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
/****************************************************************************
 * Questa classe serve a gestire le letture o scritture delle sostituzioni 
 * in un file come giornale e siccome viene usata per leggere il giornale
 * frequentemente ne mantiene una copia in una specie di cache.
 *
 * @author Loris e altri
 ***************************************************************************/

public class Giornale {    
    
    private static ArrayList<Sostituzione> giornale = null;
    
    /************************************************************************
     * Scrivere una sostituzione nel file del giornale
     * 
     * @param x è la sostituzione da registrare su disco
     ************************************************************************/
	public static void scriviRecord(Sostituzione x) {
		BufferedWriter out = null;
		try {
		    leggiGiornale();  // il giornale deve essere letto da disco
			out = new BufferedWriter(new FileWriter(Ambiente.getFileGiornale(), true));
			out.write(x+"\n"); // qui scriviamo la sostituzione x e con "\n" si manda a capo
			out.close();
			giornale.add(x);
		} catch (Exception e) {
		    FinestraEccezione fe = new FinestraEccezione(e);
		    fe.showAndWait();
		}
		Ambiente.aggiornaOreSvolteDocenti(); // Esistono altri modi molto più eleganti di farlo
	}
	
	/************************************************************************
	 * Legge il giornale da disco o usa quello presente in memoria
	 * @return il giornale delle sostituzioni
	 * @throws IOException
	 ***********************************************************************/
	public static ArrayList<Sostituzione> leggiGiornale() throws IOException {
	    if(giornale==null) {
	        File fileGiornale = Ambiente.getFileGiornale();
	        if(!fileGiornale.exists()) {
	            // se il file non esiste lo creo vuoto
	            // https://stackoverflow.com/questions/1406473/simulate-touch-command-with-java
	            FileOutputStream fos = new FileOutputStream(fileGiornale);
	            fos.close();
	        }
	        // visto che non l'ho ancora letto leggo il giornale
    		BufferedReader reader = new BufferedReader(new FileReader(fileGiornale));
            giornale = new ArrayList<Sostituzione>();
    		String line;
    		while((line=reader.readLine())!=null) {
    		    // evitiamo problemi con le linee vuote
    		    if(line.trim().length()>0) {
    		        Sostituzione x = new Sostituzione(line);
    		        giornale.add(x);
    		    }
    		}
    		reader.close();
	    }
	    // TODO valutare se è il caso di ritornare una copia e non il giornale stesso
		return giornale;
	}
}

