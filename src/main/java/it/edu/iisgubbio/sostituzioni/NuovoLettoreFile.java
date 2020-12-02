package it.edu.iisgubbio.sostituzioni;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class NuovoLettoreFile {
    
    private static final String MARCATORE_ORA_RECUPERO = "R";
    private static final String MARCATORE_ORA_DISPOSIZIONE_CASSATA = "D";
    private static final String MARCATORE_ORA_DISPOSIZIONE_GATTAPONE = "GATT";
    private static final String MARCATORE_ORA_PAGAMENTO = "PAGAM";
    private static final String MARCATORE_ORA_POTENZIAMENTO = "POT";
	
	private final static byte RIGA_ORE = 4;
	private static final String FOGLIO_DOCENTI = "Insieme_totale";
	private static final String FOGLIO_INFORMAZIONI = "informazioni";
	
	private final static byte PRIMO_INSEGNANTE = 5;
	private final static byte  COLONNA_INSEGNANTE = 0;
	public static ArrayList<Docente> leggiExcel(){
		ArrayList<Docente> lista = new ArrayList<Docente>();
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream("C:\\Users\\rigiu\\git\\sostituzioni\\datiDiProva\\fileDatiDocenti.xlsx"));
			Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
			int i = PRIMO_INSEGNANTE;
			String contenuto;
			while((contenuto = foglio.getRow(i).getCell(COLONNA_INSEGNANTE).getStringCellValue()).length()!=0) {
				System.out.println(i + " " + contenuto);
				lista.add(new Docente(contenuto));
				i+=2;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return(lista);
	}
	public static void main(String[] args) {
		leggiExcel();
	}
}
