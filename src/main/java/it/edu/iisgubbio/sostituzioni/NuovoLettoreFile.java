package it.edu.iisgubbio.sostituzioni;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class NuovoLettoreFile {

	private static final String MARCATORE_ORA_RECUPERO = "R";
	private static final String MARCATORE_ORA_DISPOSIZIONE_CASSATA = "D";
	private static final String MARCATORE_ORA_DISPOSIZIONE_GATTAPONE = "GATT";
	private static final String MARCATORE_ORA_PAGAMENTO = "PAGAM";
	private static final String MARCATORE_ORA_POTENZIAMENTO = "POT";

	private final static byte RIGA_ORE = 4;
	private final static byte COLONNA_FINALE_ORARIO = 43;
	private static final String FOGLIO_DOCENTI = "Insieme_totale";
	private static final String FOGLIO_INFORMAZIONI = "informazioni";

	private final static byte PRIMO_INSEGNANTE = 5;
	private final static byte COLONNA_INSEGNANTE = 0;

	
	public static ArrayList<Docente> leggiExcel() {
		ArrayList<Docente> lista = new ArrayList<Docente>();
		try {
			Workbook libro = new XSSFWorkbook(
					new FileInputStream("C:\\Users\\rigiu\\git\\sostituzioni\\datiDiProva\\fileDatiDocenti2020.xlsx"));
			Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
			//indice degli insegnanti (righe)
			int i = PRIMO_INSEGNANTE;
			String contenuto;
			
			//scorro fino a quando arriva alla fine degli insegnanti
			while ((contenuto = foglio.getRow(i).getCell(COLONNA_INSEGNANTE).getStringCellValue()).length() != 0) {
				// System.out.println(i + " " + contenuto);
				Docente d = new Docente(contenuto);
				//scorre le ore dell'orario(colonne)
				for (int j = 1; j < COLONNA_FINALE_ORARIO; j++) {
					//controlla se a quell'ora il professore lavora
					if (foglio.getRow(i).getCell(j).getStringCellValue().length() != 0) {
						
						int giorno =  ((j-(j%9)+ 1) /9)+1;
						int orario = j%9;
						String aula = foglio.getRow(i+1).getCell(j).getStringCellValue();
						String classe = foglio.getRow(i).getCell(j).getStringCellValue();
						//temporaneo
						boolean compresenza = false;
						d.oreLezione.add(new OraLezione(giorno,orario,aula,classe,compresenza));
						//System.out.println(giorno +" " + (((j-(j%9)+ 1) /9)+1));
					}
				}
				i += 2;
				
				lista.add(d);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (lista);
	}

	public static void main(String[] args) {
		leggiExcel();
	}
}
