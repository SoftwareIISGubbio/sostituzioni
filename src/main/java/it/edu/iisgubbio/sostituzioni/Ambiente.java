package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
    private static final String PERCORSO_FILE_PROPRIETA = 
            System.getProperties().getProperty("user.home")+ File.separator+".sostituzioni.proprieta"; 
    
    public static final String VERSIONE = leggiVersione();
    public static final String DATA_COMPILAZIONE = leggiDataCompilazione();

    /************************************************************************
     * Salava i diversi percorsi nel file delle preferenze
     ***********************************************************************/
    public static void salvaProprieta() {
        System.out.println("proprietà salvate in: "+PERCORSO_FILE_PROPRIETA);
        
        try( FileOutputStream uscita = new FileOutputStream(PERCORSO_FILE_PROPRIETA) ){
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
    
    /********************************************************************************************
     * @param p il problema da aggiungere all'elenco
     *******************************************************************************************/
    public static void addProblema(String p) {
        problemi += p + "\n";
    }
    
    /********************************************************************************************
     * ri-legge il giornale per calcolare le ore svolte da un docente, non è certo efficente
     * come strategia magari si può fare di meglio
     *******************************************************************************************/
    public static void aggiornaOreSvolteDocenti() {
        ArrayList<Sostituzione> records;
        try {
            records = Giornale.leggiGiornale();
            // prima di ricalcolare le ore svolte le azzero
            for(Docente docente: docenti) {
                docente.oreRecuperate = 0;
            }
            // carico ogni singola ora su ogni docente, la strategia è penosa ma
            // visto il carico dovrebbe essere sopportabile
            for(Sostituzione s: records) {
                for(Docente docente: docenti) {
                    if(docente.nome.equals(s.getNomeSostituto())){
                        docente.oreRecuperate++;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert dialogoAllerta = new Alert(AlertType.ERROR, "Il file del giornale \""+getFileGiornale()+"\" è corrotto, puliscilo e riavvia il programma o impostane uno nuovo");
            dialogoAllerta.showAndWait();
            impostaPreferenzeEEsci();
        }
    }
    
    /********************************************************************************************
     * Mostra la finestra per impostare le preferenze, le salva e esce dal programma
     *******************************************************************************************/
    public static void impostaPreferenzeEEsci() {
        // se non sono riuscito ad aprire il file mostro le preferenze 
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(Ambiente.class.getResource("Preferenze.fxml").openStream()) );
            s.setScene(scena);
            s.setTitle("Preferenze");
            s.showAndWait();
            System.exit(0);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    
    /********************************************************************************************
     * Legge il contenuto di un file (una sola riga)
     * @param nome del file da leggere
     * @param predefinito valore di default
     * @return il testo contenuto nel file o il valore predefinito in caso di errore
     *******************************************************************************************/
    private static String leggiInteroFile(String nome, String predefinito) {
        try {
            InputStream is = FinestraPrincipale.class.getResourceAsStream( nome );
            InputStreamReader isr = new InputStreamReader(is);
            int numero;
            char buffer[] = new char[20];
            numero = isr.read(buffer);
            isr.close();
            is.close();
            return new String(buffer,0,numero);
        }catch(Exception ex) {
            return predefinito;
        }
    }
    
    /********************************************************************************************
     * Legge il numero di versione dal file versione.txt contenuto nel pacchetto
     * @return il numero di versione del programma
     *******************************************************************************************/
    private static String leggiVersione(){
        return leggiInteroFile("versione.txt", "V?.?");
    }
    
    /********************************************************************************************
     * Legge la data di compilazione dal file versione.txt contenuto nel pacchetto
     * @return la data di compilazione
     *******************************************************************************************/
    private static String leggiDataCompilazione(){
        return leggiInteroFile("dataCompilazione.txt", "data sconosciuta");
    }

    
    /********************************************************************************************
     * Questo codice viene eseguito al momento del caricamento della classe in memoria
     * TODO: forse sarebbe il caso di farlo diventare un normale oggetto e togliere tutte
     * le proprietà static
     *******************************************************************************************/
    static {
        // le preferenze vengono caricate in automatico al caricamento della classe
        System.out.println("proprietà lette da: "+PERCORSO_FILE_PROPRIETA);
        
        try( FileInputStream entrata = new FileInputStream(PERCORSO_FILE_PROPRIETA) ){
            proprieta.load(entrata);
        } catch (Exception e) {
            impostaPreferenzeEEsci();
        }
        
        // leggo le informazioni dei docenti dal file XML
        try( FileInputStream entrata = new FileInputStream(PERCORSO_FILE_PROPRIETA) ){
            proprieta.load(entrata);
            docenti = NuovoLettoreFile.leggiExcel( getFileOrarioExcel() );
        } catch (Exception e) {
            e.printStackTrace();
            Alert dialogoAllerta = new Alert(AlertType.ERROR, "Il file excel contiene errori che non consentono di analizzarlo, indicane uno funzionante nelle preferenze (errore: "+e.getMessage()+")");
            dialogoAllerta.showAndWait();
            impostaPreferenzeEEsci();
        }
        
        // creo elenco con nomi di classi e controllo se c'è qualche nome strano
        HashSet<String> insiemeNomiDiClassi = new HashSet<>();
        String nomeClasse;
        for(Docente docente: docenti) {
            for(OraLezione ol: docente.oreLezione) {
                nomeClasse = ol.classe;
                // ci facciamo andar bene:
                //   [\\[(]? → una parentesi quadra o tonda aperta se c'è
                //   [1-5] → un numero da 1 a 5
                //   [a-z] → una lettera
                //   [\\-1-5a-z]* → una sequenza di uno o più lettere numeri e "-" 
                //   [\\])]? → una parentesi quadra o tonda chiusa se c'è
                // uno o più delle cose sopra eventualmente separate da "-"
                if( nomeClasse.matches("([\\[(]?[1-5][A-Z][\\-1-5A-Z]*[\\])]?-?)+") || nomeClasse.equals("gatt")){
                    insiemeNomiDiClassi.add(nomeClasse);
                } else {
                    // FIXME: cosa è questa sigla "VP" ?
                	if(!nomeClasse.equals("VP") ) {
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
