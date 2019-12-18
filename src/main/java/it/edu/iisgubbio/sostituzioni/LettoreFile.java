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

public class LettoreFile {
	private static byte posFoglio = 4;
	private final static byte rigaOre = 4;
	
	
	/**
	 * Legge il documento Excel
	 * @return lista dei professori
	 */
	
	public static ArrayList<Docente> leggiExcel() {
		ArrayList<Docente> lista = new ArrayList<>();
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(NomiFile.fileOrario));
			Sheet foglio = libro.getSheetAt(posFoglio);
			calcolaOrario(rigaOre+1, foglio, lista);
			leggiOreSpeciali(lista);
			leggiProfSostegno(lista);
			libro.close();
		} catch (Exception e) {
			e.printStackTrace();
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
			FileInputStream is = new FileInputStream(NomiFile.fileOrarioXml);
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        HandlerSAX handlerEdoardo = new HandlerSAX(lista);
	        saxParser.parse(is, handlerEdoardo);  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return lista;
	}

	/**
	 * Legge le ore speciali dal file Excel (Es. Ore a recupero)
	 * @param lista di professori
	 * @return lista dei docenti completa
	 */
	
	private static void leggiOreSpeciali(ArrayList<Docente> lista) {

		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(NomiFile.fileOrario));
			Sheet foglio = libro.getSheetAt(posFoglio);

			for(int i = rigaOre+1; foglio.getRow(i)!= null && foglio.getRow(i).getCell(1)!= null; i++) {
				Docente docente = lista.get(i-rigaOre-1);
				for(int j = 2; j<35; j++) {
					int giorno;
					int orario = 0;
					if(j<=10) giorno = 1;
					else if(j<=16) giorno = 2;
					else if(j<=22) giorno = 3;
					else if(j<=28) giorno = 4;
					else  giorno = 5;

					String stringaOra = foglio.getRow(rigaOre).getCell(j).getStringCellValue();

					for (int k = 1; k < Ora.nomiOre.length; k++) {
						if(Ora.nomiOre[k].startsWith(stringaOra)) {
							orario = k;
							break;
						}
					}
					String cella = foglio.getRow(i).getCell(j).getStringCellValue();
					if(cella.equals("R")) {
						docente.oraARecupero = new Ora(giorno,orario);
					}else if(cella.equals("PAGAM")) {
						docente.oreAPagamento.add(new Ora(giorno, orario));
					}else if(cella.equals("POT")) {
						docente.orePotenziamento.add(new Ora(giorno, orario));
					}else if(cella.equals("D")) {
						docente.oraADisposizioneCassata = new Ora(giorno, orario);
					}
					
					libro.close();
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/***
	 * Calcola le ore di lezione dei professori dal file Excel
	 * @param riga di partenza del foglio (da 0 a N-1)
	 * @param foglio da leggere
	 * @param lista di professori
	 * @return indice in cui si ferma il ciclo
	 */
	private static int calcolaOrario(int partenza, Sheet foglio, ArrayList<Docente> lista) {
		int i;
		//il ciclo si ripete finche' non incontra una riga vuota
		for( i = partenza;foglio.getRow(i)!=null&&foglio.getRow(i).getCell(1)!=null;i++) {
			Docente docente = new Docente(foglio.getRow(i).getCell(1).getStringCellValue().trim());
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
				
				String stringaOra = foglio.getRow(rigaOre).getCell(j).getStringCellValue();

				for (int k = 1; k < Ora.nomiOre.length; k++) {
					if(Ora.nomiOre[k].startsWith(stringaOra)) {
						orario = k;
						break;
					}
				}

				OraLezione ora = new OraLezione(giorno, orario);
				String contenuto = foglio.getRow(i).getCell(j).getStringCellValue();
				
				//controlla le ore speciali e le assegna al professore
				if(contenuto.equals("R")) {
					docente.oraARecupero = new Ora(giorno,orario);
				}else if(contenuto.equals("PAGAM")) {
					docente.oreAPagamento.add(new Ora(giorno, orario));
				}else if(contenuto.equals("POT")) {
					docente.orePotenziamento.add(new Ora(giorno, orario));
				}else if(contenuto.equals("D")) {
					docente.oraADisposizioneCassata = new Ora(giorno, orario);
				}else {
					ora.classe = contenuto;
					listaOre.add(ora);
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
	private static void leggiProfSostegno(ArrayList<Docente> lista) {
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(NomiFile.fileOrario));
			Sheet foglio = libro.getSheetAt(posFoglio);
			int i = calcolaOrario(rigaOre+lista.size()+5, foglio, lista);
			
			calcolaOrario(i+5, foglio, lista);

			libro.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}