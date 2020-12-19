package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

/**
 * Legge il documento excel e inserisce le informazioni in un arraylist docenti
 * 
 * @author Riginel Andrei Ungureanu
 *
 */
public class NuovoLettoreFile {

	private static final String MARCATORE_ORA_RECUPERO = "R";
	private static final String MARCATORE_ORA_DISPOSIZIONE_CASSATA = "DC";
	private static final String MARCATORE_ORA_DISPOSIZIONE_GATTAPONE = "DG";
	private static final String MARCATORE_ORA_PAGAMENTO = "£";
	private static final String MARCATORE_ORA_POTENZIAMENTO = "POT";

	private final static byte COLONNA_FINALE_ORARIO = 43;
	private static final String FOGLIO_DOCENTI = "insieme_totale";
	private static final String FOGLIO_INFORMAZIONI = "informazioni";
	private final static String FOGLIO_SOSTEGNO = "sostegno";

	private final static byte PRIMO_INSEGNANTE = 5;
	private final static byte COLONNA_INSEGNANTE = 0;

	/**
	 * legge il file excel e restituisce la lista degli insegnanti
	 * 
	 * @param percorso del file
	 * 
	 * @return un arraylist con dentro tutti i docenti
	 */
	public static ArrayList<Docente> leggiExcel(File percorso) {
	    ArrayList<Docente> curricolari = leggiDocentiCurricolari(percorso);
	    ArrayList<Docente> sostegno = leggiProfSostegno(percorso);
	    
	    curricolari.addAll(sostegno);
	    return curricolari;
	}
	
	/**
     * Riempie un arraylist di docenti curricolari
     * 
     * @param listaDocenti
     */
	public static ArrayList<Docente> leggiDocentiCurricolari(File percorso) {
	    ArrayList<Docente> lista = new ArrayList<Docente>();
		try {
			Workbook libro = new XSSFWorkbook(new FileInputStream(percorso));
			Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
			// indice degli insegnanti (righe)
			int i = PRIMO_INSEGNANTE;
			String contenuto;

			// scorro fino a quando arriva alla fine degli insegnanti
			while ((contenuto = foglio.getRow(i).getCell(COLONNA_INSEGNANTE).getStringCellValue()).length() != 0) {
				// System.out.println(i + " " + contenuto);
				Docente d = new Docente(contenuto);
				// scorre le ore dell'orario(colonne)
				for (int j = 1; j < COLONNA_FINALE_ORARIO; j++) {
					// controlla se a quell'ora il professore lavora
					if (foglio.getRow(i + 1).getCell(j).getStringCellValue().length() != 0) {
						// calcolo il numero del giorno(da 1 a 5)
						int giorno = ((j - (j % 9) + 1) / 9) + 1;
						// calcolo l'ora di lezione(da 1 a 8 )
						int orario = j % 9 > 6 ? j % 9 : j % 9 + 1;
						// System.out.println(orario);
						String aula = foglio.getRow(i + 1).getCell(j).getStringCellValue();
						// per uniformare tolgo gli spazi e metto in minuscolo il nome della classe
						String classe = foglio.getRow(i).getCell(j).getStringCellValue().replaceAll(" ", "")
								.toLowerCase();
						boolean compresenza = compresenza(foglio, i, j, aula);
						switch (aula) {
						case MARCATORE_ORA_RECUPERO:
							d.oraARecupero = new Ora(giorno, orario);
							break;

						case MARCATORE_ORA_POTENZIAMENTO:
							d.orePotenziamento.add(new Ora(giorno, orario));
							break;

						case MARCATORE_ORA_PAGAMENTO:
							d.oreAPagamento.add(new Ora(giorno, orario));
							break;

						case MARCATORE_ORA_DISPOSIZIONE_GATTAPONE:
							d.oreADisposizioneGattapone.add(new Ora(giorno, orario));
							break;

						case MARCATORE_ORA_DISPOSIZIONE_CASSATA:
							d.oreADisposizioneCassata.add(new Ora(giorno, orario));
							break;

						case "":
							break;

						default:
							d.oreLezione.add(new OraLezione(giorno, orario, aula, classe, compresenza));
							// System.out.println("Prof "+ contenuto + " giorno " + giorno+ " orario "+
							// orario + " Compresenza: "+compresenza(foglio,i,j,aula));

						}

						// System.out.println(giorno +" " + (((j-(j%9)+ 1) /9)+1));
					}
				}

				i += 2;
				lista.add(d);
			}

			Sheet fGruppi = libro.getSheet(FOGLIO_INFORMAZIONI);
			i = 0;
			// FIXME non è detto che siano nello stesso ordine
			while (fGruppi.getRow(i + 1) != null) {
				String gruppo = fGruppi.getRow(i + 1).getCell(1).getStringCellValue();
				int oreARecupero = (int) fGruppi.getRow(i + 1).getCell(2).getNumericCellValue();
				lista.get(i).gruppo = gruppo;
				lista.get(i).oreDaRecuperare = oreARecupero;
				// System.out.println(contenuto);
				i++;
			}
			// System.out.println("FINITO!");
			// for( i = 0; i< lista.size();i++) {
			// Docente d = lista.get(i);
			// System.out.println(d.nome+","+d.gruppo + "," + d.oreDaRecuperare);
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (lista);
	}

	/**
	 * Controlla se un insegnante ha la compresenza
	 * 
	 * @param foglio           da dove si prende la tabella
	 * @param indiceInsegnante riga dell'insegnante che si deve controllare
	 * @param indiceOra        colonna dell'insegnante che si deve controllare
	 * @param aula             aula dove avviene la possibile compresenza
	 * @return true se c'è la compresenza, falso altriemnti
	 */
	public static boolean compresenza(Sheet foglio, int indiceInsegnante, int indiceOra, String aula) {
		boolean risposta = false;
		int i = PRIMO_INSEGNANTE;

		while ((foglio.getRow(i).getCell(COLONNA_INSEGNANTE).getStringCellValue()).length() != 0) {
			if (i != indiceInsegnante) {
				if (foglio.getRow(i + 1).getCell(indiceOra).getStringCellValue().equals(aula)) {
					risposta = true;
					break;
				}
			}
			i += 2;
		}

		return risposta;
	}

	/**
	 * Riempie un arraylist di docenti di sostegno
	 * 
	 * @param listaDocenti
	 */
	public static ArrayList<Docente> leggiProfSostegno(File percorso) {
		Workbook libro;
		ArrayList<Docente> listaDocenti = new ArrayList<Docente>();
		try {
			libro = new XSSFWorkbook(new FileInputStream(percorso));
			Sheet foglio = libro.getSheet(FOGLIO_SOSTEGNO);
			int i = PRIMO_INSEGNANTE;
			String contenuto;
			String classe,annotazione;
			// scorro fino a quando arriva alla fine degli insegnanti
			while (( foglio.getRow(i).getCell(COLONNA_INSEGNANTE)) != null) {
				// System.out.println(i + " " + contenuto);
				contenuto = foglio.getRow(i).getCell(COLONNA_INSEGNANTE).getStringCellValue();
				Docente d = new Docente(contenuto);
				d.gruppo = "sostegno";
				// scorre le ore dell'orario(colonne)
				for (int j = 1; j < COLONNA_FINALE_ORARIO; j++) {
				    // per uniformare tolgo gli spazi e metto in minuscolo il nome della classe
				    classe = foglio.getRow(i).getCell(j)!=null 
				            ? foglio.getRow(i).getCell(j).getStringCellValue().replaceAll(" ", "").toLowerCase()
				            : "";
				    annotazione = foglio.getRow(i+1).getCell(j)!=null
				            ? foglio.getRow(i+1).getCell(j).getStringCellValue().replaceAll(" ", "")
				            : "";
				    // controlla se a quell'ora il professore lavora
					if (classe.length() != 0 || annotazione.length() != 0) {
						// calcolo il numero del giorno(da 1 a 5)
						int giorno = ((j - (j % 9) + 1) / 9) + 1;
						// calcolo l'ora di lezione(da 1 a 8 )
						int orario = j % 9 > 6 ? j % 9 : j % 9 + 1;
						switch (annotazione) {
						case MARCATORE_ORA_RECUPERO:
							d.oraARecupero = new Ora(giorno, orario);
							break;
	                    case MARCATORE_ORA_DISPOSIZIONE_GATTAPONE:
	                        d.oreADisposizioneGattapone.add(new Ora(giorno, orario));
	                        break;
	                    case MARCATORE_ORA_DISPOSIZIONE_CASSATA:
	                        d.oreADisposizioneCassata.add(new Ora(giorno, orario));
	                        break;
	                    case MARCATORE_ORA_PAGAMENTO:
	                        d.oreAPagamento.add(new Ora(giorno, orario));
	                        break;
						default:
							OraLezione ora = new OraLezione(giorno, orario);
							ora.classe = classe;
							d.oreLezione.add(ora);
							// System.out.println("Prof "+ contenuto + " giorno " + giorno+ " orario "+
							// orario + " Compresenza: "+compresenza(foglio,i,j,aula));
						}
					}
				
				}

				i += 2;
				// System.out.println(i);
				// System.out.println("nome: " + d.nome);
				listaDocenti.add(d);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    // FIXME non è che conviene far propagare le eccezioni?
			e.printStackTrace();
		}
		return(listaDocenti);
	}

	public static void main(String[] args) {
		leggiExcel(new File("C:\\Users\\rigiu\\git\\sostituzioni\\datiDiProva\\fileDatiDocenti2020-tagliato.xlsx"));
		leggiProfSostegno(new File("C:\\Users\\rigiu\\git\\sostituzioni\\datiDiProva\\fileDatiDocenti2020-tagliato.xlsx"));
	}

}
