package it.edu.iisgubbio.sostituzioni.filtri;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import it.edu.iisgubbio.sostituzioni.FinestraEccezione;
import it.edu.iisgubbio.sostituzioni.Giornale;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

/****************************************************************************
 * Esclude chi è già impegnato per quell'ora in quel giorno di calendario per
 * altra sostituzione
 ***************************************************************************/
public class FiltroImpegantoPerSostituzione {
	
    // creare questo oggetto usa un mare di tempo in proporzione
    // quindi lo creo una volta per tutte qui
    // in generale da misure approssimative la formattazione delle date
    // con questo oggetto non è velocissima
    static DateTimeFormatter formattatoreData = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    
	/************************************************************************
	 * @param tutti è un elenco di docenti da filtrare
	 * @param data la data in cui deve avvenire la sostituzione
	 * @return tutti i docenti liberi in quel momento
	 ***********************************************************************/
	public static ArrayList<Docente> docentiSenzaSostituzioni(ArrayList<Docente> tutti, LocalDate data, Ora oraDaSostituire) {
	    String dataRichiesta = formattatoreData.format(data); 
	    ArrayList<Sostituzione> tutteLeSostituzioni = new ArrayList<>();
	    HashSet<String> sostituzioniNellOra = new HashSet<>();
	    ArrayList<Docente> docentiNonOccupatiInSostituzioni = new ArrayList<>();
	    
	    try {
            tutteLeSostituzioni = Giornale.leggiGiornale();
        } catch (IOException e) {
            FinestraEccezione fe = new FinestraEccezione(e);
            fe.showAndWait();
        }
	    
	    for(Sostituzione s: tutteLeSostituzioni) {
	        if(s.getData().equals(dataRichiesta) && s.orario==oraDaSostituire.orario) {
	            sostituzioniNellOra.add(s.getNomeSostituto());
	        }
	    }
	    
		for (Docente d : tutti) {
			if ( !sostituzioniNellOra.contains(d.nome) ) {
				docentiNonOccupatiInSostituzioni.add(d);
			}
		}
        
		return docentiNonOccupatiInSostituzioni;
	}
	
}
