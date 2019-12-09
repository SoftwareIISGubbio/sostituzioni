package it.edu.iisgubbio.sostituzioni;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.HandlerSAX;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class LettoreFile {
	private static byte posFoglio = 4;
	public final static byte rigaGiorni = 3;
	public final static byte rigaOre = 4;
	
	public static ArrayList<Docente> leggiExcel() {
		ArrayList<Docente> lista = new ArrayList<>();
		
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(NomiFile.fileOrario));
			Sheet foglio = libro.getSheetAt(posFoglio);
			
			for(int i = rigaOre+1; i==5; i++) {//foglio.getRow(i)!= null && foglio.getRow(i).getCell(1)!= null; i++) {
				Cell cella = foglio.getRow(i).getCell(1);
				Docente docente = new Docente(cella.getStringCellValue());
				ArrayList<OraLezione> listaOre = new ArrayList<>();
				docente.oreLezione = listaOre;
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
					
					OraLezione ora = new OraLezione(giorno, orario);
					String contenuto = foglio.getRow(i).getCell(j).getStringCellValue();
					if(contenuto.equals("R")){
						docente.oraARecupero = new Ora(giorno, orario);
						ora.classe = "NO";
					}else {
						ora.classe = contenuto;
					}
					
					
				}
				lista.add(docente);
			}
			
			libro.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static ArrayList<Docente> leggiXML(){
		ArrayList<Docente> docenti = new ArrayList<>();
		
		try {
			FileInputStream is = new FileInputStream(NomiFile.fileOrarioXml);
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        HandlerSAX handlerEdoardo = new HandlerSAX(docenti);
	        saxParser.parse(is, handlerEdoardo);  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return docenti;
	}

}
