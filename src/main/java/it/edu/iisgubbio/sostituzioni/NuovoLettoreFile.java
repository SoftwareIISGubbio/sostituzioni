package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Cell;
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
	
	// XXX: magari funziona ma se ad un certo punto si leggessero ancora più colonne?
	private static final String nomiColonne[] = {"a","b","c","d","e","f","g","h","i","j","k",
	        "l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
	        "aa","ab","ac","ad","ae","af","ag","ah","ai","aj"};


	/**
	 * legge il file excel e restituisce la lista degli insegnanti
	 * 
	 * @param percorso del file
	 * 
	 * @return un arraylist con dentro tutti i docenti
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static ArrayList<Docente> leggiExcel(File percorso) throws FileNotFoundException, IOException {
	    ArrayList<Docente> curricolari = leggiDocentiCurricolari(percorso);
	    ArrayList<Docente> sostegno = leggiProfSostegno(percorso);
	 
	    // un professore di sostegno può essere anche curricolare
	    Docente trovato;
	    for(Docente candidato: sostegno) {
	        trovato = null;
	        for(Docente x: curricolari) {
	            if(x.nome.toLowerCase().equalsIgnoreCase(candidato.nome)) {
	                trovato=x;
	                break;
	            }
	        }
	        if(trovato==null) {
	            curricolari.add(candidato);
	        } else {
	            for(OraLezione ol: candidato.oreLezione) {
	                trovato.oreLezione.add(ol);
	            }
	        }
	    }
	    leggiGruppi(percorso, curricolari);
	    Collections.sort(curricolari);
	    Docente dSinistro, dDestro;

	    // a questo punto posso calcolare le ore di compresenza
	    for(int iSinistro=0; iSinistro<curricolari.size(); iSinistro++) {
	        dSinistro = curricolari.get(iSinistro);
	        for(int iDestro=iSinistro+1; iDestro<curricolari.size(); iDestro++) {
	            dDestro = curricolari.get(iDestro);
	            for(OraLezione ol: dSinistro.oreLezione) {
	                OraLezione oraDaCercare = dDestro.getOra(ol);
	                if(oraDaCercare!=null) {
	                    ol.compresenza = true;
	                    oraDaCercare.compresenza = true;
	                }
	            }
	        }
	    }
	    return curricolari;
	}
	
    /** 
     * @param colonna che si sta elaborando 
     * @return il numero del giorno (da 1 a 5) 
     * */
	private static final int giorno(int colonna) {
	    return 1 + ((colonna-1) / 9 );
	}
	
	/** 
	 * @param colonna che si sta elaborando 
	 * @return l'ora di lezione (da 1 a 8 ) 
	 * */
	private static final int ora(int colonna) {
	    int ora = (colonna-1) % 9 + 1;
        if(ora>6) { 
            ora--;
        }
        return ora;
	}
	
	/**
	 * @param nome della classe 
	 * @return nome senza spazi e tutto maiuscolo
	 */
	public static final String uniformaNomeClasse(String nome) {
	    return nome.replaceAll(" ", "").toUpperCase();
	}
	
	/** la uso per fare trim dappertutto quando leggo */
	private static final String leggiCella(Sheet foglio, int riga, int colonna) {
	    try {
	        // righe vuote danno sempre "" come risultato
	        if(foglio.getRow(riga)==null) {
	            return "";
	        }
    	    Cell cella = foglio.getRow(riga).getCell(colonna);
    	    if(cella!=null) {
    	        return cella.getStringCellValue().trim();
    	    } else {
    	        return "";
    	    }
	    }catch(Exception ex) {
	        ex.printStackTrace();
	        throw new RuntimeException("Non riesco a leggere al cella del foglio \""+
	                foglio.getSheetName()+
	                "\" alla cella "+nomiColonne[colonna]+(riga+1) );
	    }
	}
	
	/**
     * Riempie un arraylist di docenti curricolari
     * 
     * @param listaDocenti
	 * @throws IOException 
	 * @throws FileNotFoundException 
     */
	public static ArrayList<Docente> leggiDocentiCurricolari(File percorso) throws FileNotFoundException, IOException {
	    ArrayList<Docente> lista = new ArrayList<Docente>();
	    try(FileInputStream in = new FileInputStream(percorso);
	        Workbook libro = new XSSFWorkbook(in) ){
    		
    		Sheet foglio = libro.getSheet(FOGLIO_DOCENTI);
    		// indice degli insegnanti (righe)
    		int i = PRIMO_INSEGNANTE;
    		String contenuto;
    
    		// scorro fino a quando arriva alla fine degli insegnanti
    		while ((contenuto = leggiCella(foglio,i,COLONNA_INSEGNANTE)).length() != 0) {
    			Docente d = new Docente(contenuto);
    			// scorre le ore dell'orario(colonne)
    			for (int j = 1; j < COLONNA_FINALE_ORARIO; j++) {
    				// controlla se a quell'ora il professore lavora
    			    String aula = leggiCella(foglio, i + 1, j);
    			    String classe = uniformaNomeClasse( leggiCella(foglio,i,j) );
    				if ( aula.length() != 0 || classe.length() != 0 ) {
    					int giorno = giorno(j);
    					int orario = ora(j);
    
    					// boolean compresenza = compresenza(foglio, i, j, classe, aula);
    					boolean compresenza=false;
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
    						
    					default:
    						d.oreLezione.add(new OraLezione(giorno, orario, aula, classe, compresenza));
    						
    					}
    				}
    			}
    
    			i += 2;
    			lista.add(d);
    		}
	    }
		return (lista);
	}
	
	/**
	 * inserisce il gruppo di ciascun docente
	 * 
	 * @param percorso
	 * @param lista dei docenti in cui inserire i gruppi
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void leggiGruppi(File percorso, ArrayList<Docente> lista) throws FileNotFoundException, IOException {
	    try(FileInputStream in = new FileInputStream(percorso);
	        Workbook libro = new XSSFWorkbook(in) ){
    	    Sheet foglioInformazioni = libro.getSheet(FOGLIO_INFORMAZIONI);
    	    int i = 1;
    	    while (foglioInformazioni.getRow(i) != null) {
    	        if(foglioInformazioni.getRow(i).getCell(0)!= null 
    	                && leggiCella(foglioInformazioni, i, 0).length()>0) {
    	            String nome = leggiCella(foglioInformazioni, i, 0);
    	            String gruppo = leggiCella(foglioInformazioni, i, 1);
    	            int oreDaRecuperare = (int) foglioInformazioni.getRow(i).getCell(2).getNumericCellValue();
    	            boolean trovato = false;
    	            for(Docente d: lista) {
    	                if(d.nome.equals(nome)) {
    	                    d.gruppo = gruppo;
    	                    d.oreDaRecuperare = oreDaRecuperare;
    	                    trovato = true;
    	                    break;
    	                }
    	            }
    	            if(!trovato) {
    	                Ambiente.addProblema("il docente \""+nome+"\" è presente soltanto nel foglio "+FOGLIO_INFORMAZIONI);
    	            }
    	        }
    	        i++;
    	    }
	    }
	}

	/**
	 * Controlla se un insegnante ha la compresenza 
	 * cioè se lavora nella stessa classe, stessa stanza non funziona
	 * perché ad esempio in palestra possono trovarsi più classi contemporaneamente
	 * 
	 * @param foglio           da dove si prende la tabella
	 * @param indiceInsegnante riga dell'insegnante che si deve controllare
	 * @param indiceOra        colonna dell'insegnante che si deve controllare
	 * @param classe           aula dove avviene la possibile compresenza
	 * @param aula             aula dove avviene la possibile compresenza
	 * @return true se c'è la compresenza, falso altriemnti
	 */
	public static boolean compresenza(Sheet foglio, int indiceInsegnante, int indiceOra, String classe, String aula) {
		boolean risposta = false;
		int i = PRIMO_INSEGNANTE;
		String classeAttuale;

		while (( leggiCella(foglio, i, COLONNA_INSEGNANTE) ).length() != 0) {
			if (i != indiceInsegnante) {
		        classeAttuale = uniformaNomeClasse( leggiCella(foglio, i, indiceOra) );
				if (classeAttuale.equals(classe)) {
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static ArrayList<Docente> leggiProfSostegno(File percorso) throws FileNotFoundException, IOException {
		Workbook libro;
		ArrayList<Docente> listaDocenti = new ArrayList<Docente>();
		
		libro = new XSSFWorkbook(new FileInputStream(percorso));
		Sheet foglio = libro.getSheet(FOGLIO_SOSTEGNO);
		int i = PRIMO_INSEGNANTE;
		String contenuto;
		String classe,annotazione;
		// scorro fino a quando arriva alla fine degli insegnanti
		while((contenuto = leggiCella(foglio, i, COLONNA_INSEGNANTE)).length() != 0) {
			contenuto = leggiCella(foglio, i, COLONNA_INSEGNANTE);
			Docente d = new Docente(contenuto);
			d.gruppo = "sostegno";
			// scorre le ore dell'orario(colonne)
			for (int j = 1; j < COLONNA_FINALE_ORARIO; j++) {
			    // per uniformare tolgo gli spazi e metto in minuscolo il nome della classe
			    classe = foglio.getRow(i).getCell(j)!=null 
			            ? uniformaNomeClasse( leggiCella(foglio, i, j))
			            : "";
			    annotazione = foglio.getRow(i+1).getCell(j)!=null
			            ? leggiCella(foglio, i+1, j) // replaceAll(" ", "")
			            : "";
			    // controlla se a quell'ora il professore lavora
				if (classe.length() != 0 || annotazione.length() != 0) {
					int giorno = giorno(j);
					int orario = ora(j);
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
					}
				}
			}
			i += 2;
			listaDocenti.add(d);
		}
		return(listaDocenti);
	}

}
