package it.edu.iisgubbio.sostituzioni;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.HandlerSAX;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Legge il documento XML (prodotto da FET) e il file Excel
 * inserisce le informazioni in una ArrayList di Docente
 * 
 * @author 4i
 */
public class LettoreFile {
	private final static byte RIGA_ORE = 4;
	private static final String FOGLIO_DOCENTI = "docenti";
	private static final String FOGLIO_INFORMAZIONI = "informazioni";
	
	/**
	 * Legge il documento Excel con le informazioni dei professori
	 * @return lista dei professori
	 */
	public static ArrayList<Docente> leggiExcel() {
		ArrayList<Docente> lista = new ArrayList<>();
		try {
		    // apro il file excel
			Workbook libro = new XSSFWorkbook(new FileInputStream(
			        Ambiente.getFileOrarioExcel()
			));
			// il file è composto da più fogli, a noi serve il foglio con le
			// informazioni sui docenti
			Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
			if(foglio==null) {
                throw new Exception("Non esiste il foglio \""+FOGLIO_DOCENTI+"\" nel file "+Ambiente.getFileOrarioExcel());
            }
			// chiama la funzione che legge le informazioni
			calcolaOrario(RIGA_ORE+1, foglio, lista, null);
			// 
			foglio = libro.getSheet(FOGLIO_INFORMAZIONI);
			if(foglio==null) {
			    throw new Exception("Non esiste il foglio \""+FOGLIO_INFORMAZIONI+"\" nel file "+Ambiente.getFileOrarioExcel());
			}
			aggiungiInformazioniDocenti(foglio, lista);
			libro.close();
		} catch (Exception e) {
            Alert dialogoAllerta = new Alert(AlertType.ERROR, e.getMessage() );
            dialogoAllerta.showAndWait();
			e.printStackTrace();
			System.exit(1);
		}
		return lista;
	}
	
	/**
	 * Legge il file xml
	 * @return lista dei professori
	 */
	
	public static ArrayList<Docente> leggiXML(){
		ArrayList<Docente> lista = new ArrayList<>();	
		try {
			FileInputStream is = new FileInputStream(
			        Ambiente.getFileOrarioFET()
			);
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        HandlerSAX handlerEdoardo = new HandlerSAX(lista);
	        saxParser.parse(is, handlerEdoardo);  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return lista;
	}


	/***
	 * Legge le ore di lezione e quelle speciali dei professori dal file Excel
	 * Legge dalla riga specificata fino alla prima riga vuota
	 * @param riga di partenza del foglio (da 0 a N-1)
	 * @param foglio da leggere
	 * @param lista di professori
	 * @param materia (facoltativo, usata per i prof di sostegno)
	 * @return indice in cui si ferma il ciclo
	 */
	private static int calcolaOrario(int partenza, Sheet foglio, ArrayList<Docente> lista, String materia) {
		int i;
		//il ciclo si ripete finche' non incontra una riga vuota
		for( i = partenza;foglio.getRow(i)!=null&&foglio.getRow(i).getCell(1)!=null;i++) {
			Docente docente = new Docente(foglio.getRow(i).getCell(1).getStringCellValue().trim().toUpperCase());
			ArrayList<OraLezione> listaOre = new ArrayList<>();
			docente.oreLezione = listaOre;
			for(int j = 2; j<35; j++) {
				int giorno;
				int orario = 0;
				//a seconda della colonna identifica il giorno
				if(j<=10) giorno = 1;
				else if(j<=16) giorno = 2;
				else if(j<=22) giorno = 3;
				else if(j<=28) giorno = 4;
				else  giorno = 5;
				
				String stringaOra = foglio.getRow(RIGA_ORE).getCell(j).getStringCellValue();

				// trova la posizione dell'ora confrontando con i valori presenti in Ora
				for (int k = 1; k < Ora.nomiOre.length; k++) {
					if(Ora.nomiOre[k].startsWith(stringaOra)) {
						orario = k;
						break;
					}
				}

				OraLezione ora = new OraLezione(giorno, orario);
				ora.materia = materia;
				String contenuto = foglio.getRow(i).getCell(j).getStringCellValue();
				
				//controlla le ore speciali e le assegna al professore
				if(contenuto.equals("R")) {
					docente.oraARecupero = new Ora(giorno,orario);
				}else if(contenuto.equals("PAGAM")) {
					docente.oreAPagamento.add(new Ora(giorno, orario));
				}else if(contenuto.equals("POT")) {
					docente.orePotenziamento.add(new Ora(giorno, orario));
				}else if(contenuto.equals("D")) {
					docente.oreADisposizioneCassata.add( new Ora(giorno, orario) );
				}else {
					if(contenuto.contains("®")){
						docente.oraARecupero = new Ora(giorno,orario);
					}
					ora.classe = contenuto.replaceAll(" ", "").replaceAll("®", "").toLowerCase();
					if(ora.classe.length()>0) {
					    listaOre.add(ora);
					}
				}
				
			}
			lista.add(docente);
		}
		return i;
	}
	
	
	/**
	 * Legge i professori di sostegno e di potenziamento dal file Excel
	 * @param lista di professori
	 */
	public static void leggiProfSostegno(ArrayList<Docente> lista) {
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(
			        Ambiente.getFileOrarioExcel()
			));
			Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
			int i = calcolaOrario(RIGA_ORE+lista.size()+4, foglio, lista, "sostegno");
			
			calcolaOrario(i+5, foglio, lista, "potenziamento");
			
			libro.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * il foglio da cui legge deve avere informazioni a partire dalla seconda riga, 
	 * la prima riga vuota è considerata lo stop.
	 * le colonne sono in ordine: nome, gruppo, oreDaSvolgereNell'anno
	 * @param foglio da cui leggere le informazioni
	 * @param listaDocenti in cui inserire le informazioni
	 */
	private static void aggiungiInformazioniDocenti(Sheet foglio, ArrayList<Docente> listaDocenti) {
        //il ciclo si ripete finche' non incontra una riga vuota
        for( int i = 1; foglio.getRow(i)!=null&&foglio.getRow(i).getCell(1)!=null; i++) {
            String nome = foglio.getRow(i).getCell(0).getStringCellValue().trim().toUpperCase();
            String gruppo = foglio.getRow(i).getCell(1).getStringCellValue().trim().toUpperCase();
            int oreDaRecuperare;
            try {
                oreDaRecuperare = (int) foglio.getRow(i).getCell(2).getNumericCellValue();
            }catch (Exception e) {
                // TODO: forse si può far di meglio
                oreDaRecuperare = -1;
            }
            boolean preso = false;
            for(Docente d: listaDocenti) {
                if(d.nome.equals(nome)) {
                    d.gruppo=gruppo;
                    d.oreDaRecuperare = oreDaRecuperare;
                    preso = true;
                    break;
                }
            }
            if(!preso) {
                // FIXME deve scriverlo nel log
                System.out.println(nome+" non esiste");
            }
        }
    }
}
