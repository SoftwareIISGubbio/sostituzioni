package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/****************************************************************************
 * 
 * Mantiene tutti i dati necessari all'applicazione per funzionare,
 * carica tutte le informazioni in faase di avvio:
 * - posizione dei file di input e giornale
 * - elenco dei docenti con relativo orario
 * - elenco delle classi
 * - messaggi di errore (problemi in fase di avvio)
 * 
 * @author classe4i_2019-20
 ***************************************************************************/
public class Ambiente {
    /** docenti con relative informazioni, letti dal foglio Excel e da un file 
     * XML prodotto da FET */
    public static ArrayList<Docente> docenti = new ArrayList<>();
    /** vettore contenente nomi di tutte le classe */
    private static String[]nomiClassi;
    /** contiene un elenco di descrizioni dei problemi riscontrati in fase di avvio  */
    private static String problemi = "";
    /** contiene i percorsi dei file Excell, FET e del giornale */
    private static Properties proprieta = new Properties();
    /** il file delle proprietà si trova in questa posizione */
    private static final String percorso = System.getProperties().getProperty("user.home")+
            File.separator+".sostituzioni.proprieta"; 

    /************************************************************************
     * Salava i diversi percorsi nel file delle preferenze
     ***********************************************************************/
    public static void salvaProprieta() {
        System.out.println("proprietà in: "+percorso);
        
        try( FileOutputStream uscita = new FileOutputStream(percorso) ){
            proprieta.store(uscita, "proprieta del programma dell'orario");
        } catch (Exception e) {
            Alert dialogoAllerta = new Alert(AlertType.ERROR, 
                    "Un problema ha impedito il salvataggio delle preferenze: "+e.getMessage() );
            dialogoAllerta.showAndWait();
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * @return il File in cui è contenuto il giornale
     ***********************************************************************/
    public static File getFileGiornale() {
        // TODO: se il file non esiste sarebbe il caso di crearlo
        return new File(proprieta.getProperty("fileGiornale"));
    }

    /************************************************************************
     * @param fileGiornale il File in cui è contenuto il giornale
     ***********************************************************************/
    public static void setFileGiornale(File fileGiornale) {
        proprieta.put("fileGiornale", fileGiornale.toString());
    }

    /************************************************************************
     * @return il File in cui è contenuta la tabella Excel con i dati
     ***********************************************************************/
    public static File getFileOrarioExcel() {
        return new File(proprieta.getProperty("fileOrarioExcel"));
    }

    /************************************************************************
     * @param fileOrarioExcel il File in cui è contenuta la tabella 
     * Excel con i dati
     ***********************************************************************/
    public static void setFileOrarioExcel(File fileOrarioExcel) {
        proprieta.put("fileOrarioExcel", fileOrarioExcel.toString());
    }

    /************************************************************************
     * @return il File in cui sono contenuti i dati esportati da FET
     ***********************************************************************/
    public static File getFileOrarioFET() {
        return new File(proprieta.getProperty("fileOrarioFET"));
    }

    /************************************************************************
     * @param fileOrarioFET il File in cui sono contenuti i dati esportati 
     * da FET
     ***********************************************************************/
    public static void setFileOrarioFET(File fileOrarioFET) {
        proprieta.put("fileOrarioFET", fileOrarioFET.toString());
    }
    
    /************************************************************************
     * @return l'elenco dei nomi delle classi
     ***********************************************************************/
    public static String[] getNomiClassi(){
        return nomiClassi;
    }
    
    /************************************************************************
     * @return una unica stringa con tutti i problemi riscontrati in fase
     *      di avvio elencati uno per linea
     ***********************************************************************/
    public static String getProblemi() {
        return problemi;
    }
    
    public static void aggiornaOreSvolteDocenti() {
        ArrayList<Sostituzione> records;
        try {
            records = Giornale.leggiGiornale(Ambiente.getFileGiornale());
            // carico ogni singola ora su ogni docente, la strategia è penosa ma
            // visto il carico dovrebbe essere sopportabile
            for(Sostituzione s: records) {
                for(Docente docente: docenti) {
                    if(docente.nome.equals(s.getNomeSostituto())){
                        // TODO: controlla che la sostituzione sia su singola ora
                        docente.oreRecuperate++;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block, tocca fa di meglio
            e.printStackTrace();
        }
    }
    
    /********************************************************************************************
     * Questo codice viene eseguito al momento del caricamento della classe in memoria
     * TODO: forse sarebbe il caso di farlo diventare un normale oggetto e togliere tutte
     * le proprietà static
     *******************************************************************************************/
    static {
        // le preferenze vengono caricate in automatico al caricamento della classe
        System.out.println("proprietà in: "+percorso);
        
        try( FileInputStream entrata = new FileInputStream(percorso) ){
            proprieta.load(entrata);
        } catch (Exception e) {
            problemi += "impossibile leggere i nomi dei file, aggiornale le preferenze e ricaricare il programma\n";
            // in fase di avvio non si può aprire una finestra con i messaggi di errore
            e.printStackTrace();
        }
        
        // leggo le informazioni dei docenti dal file XML
        docenti = LettoreFile.leggiXML();
        // leggo ulteriori informazioni dei docenti dal file xlsx
        ArrayList<Docente> informazioniExcel = LettoreFile.leggiExcel();
        
        for(Docente daExcel : informazioniExcel) {
            Docente presente = cercaDocentePerNome(daExcel.nome);
            if(presente!=null) {
                presente.gruppo = daExcel.gruppo;
                presente.oraARecupero = daExcel.oraARecupero;
                presente.oreADisposizioneCassata = daExcel.oreADisposizioneCassata;
                presente.oraADisposizioneGattapone = daExcel.oraADisposizioneGattapone;
                presente.oraARecupero = daExcel.oraARecupero;
                presente.oreAPagamento = daExcel.oreAPagamento;
                presente.orePotenziamento = daExcel.orePotenziamento;
                presente.oreDaRecuperare = daExcel.oreDaRecuperare;
                // le ore recuperate vengono lette direttamente dal giornale
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
        aggiornaOreSvolteDocenti();
    }    
    
    /********************************************************************************************
     * 
     * @param nome del docente da cercare
     * @return il docente cercato o null se non l'ha trovato
     *******************************************************************************************/
    public static Docente cercaDocentePerNome(String nome) {
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
