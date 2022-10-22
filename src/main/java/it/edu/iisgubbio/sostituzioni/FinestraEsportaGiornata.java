package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/************************************************************************************************
* crea la finestra per esportare in un foglio .xlsx tutte le supplenze della giornata
***********************************************************************************************/
public class FinestraEsportaGiornata {
	@FXML
    //In questo metodo si impostano i valori della textField presente nella finestra
	void initialize() {
        try {
            tfCartellaFile.setText(Ambiente.getCartellaBiglietto().toString() );
        }catch(Exception x) {
            ; // non faccio nulla vuol dire che la proprietà non c'è e deve inserirla
        }
    }
	
	@FXML
    private void esportaGiornata(ActionEvent e) throws IOException {
        System.out.println("bu");
    	//prende la data dal dataPicker e la trasforma in una variabile string
        LocalDate d = dataPicker.getValue();
        String data=d+"";
        
        //array list contenente tutte le sotituzioni della giornata importate con il comando Giornale.leggiGiornale()
        ArrayList<Sostituzione> tutteSostituzioni = Giornale.leggiGiornale();
        
        //array list contenente la lista di tutti i professori (non duplicati) che sono stati sostituiti quel giorno 
        ArrayList<String> professoriDaSostituire = new ArrayList<String>();
        
        //crea il foglio sostituzioni
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetSostituzioni = workbook.createSheet("Sostituzioni");
        sheetSostituzioni.setColumnWidth(0, 6000);
        sheetSostituzioni.setColumnWidth(1, 4000);
        
        //crea gli stili delle celle (grassetto; testoPiccolo; testoNormale)
        CellStyle styleGrassetto = workbook.createCellStyle(); 
        CellStyle styleTestoPiccolo = workbook.createCellStyle(); 
        CellStyle styleTestoNormale = workbook.createCellStyle(); 
        
        //stile delle celle con allineamento sia verticale che orizzontale
        CellStyle styleVerticale = workbook.createCellStyle();
        styleVerticale = workbook.createCellStyle();
        styleVerticale.setVerticalAlignment(VerticalAlignment.CENTER);
        styleVerticale.setAlignment(HorizontalAlignment.CENTER);
        
        //imposta il font in grassetto con dimenzione caratteri 10
        XSSFFont grassetto= (XSSFFont) workbook.createFont(); 
        grassetto.setFontHeightInPoints((short)10); 
        grassetto.setBold(true); 
        styleGrassetto.setFont(grassetto);
        
        //imposta il font con dimenzione 8
        XSSFFont testoPiccolo= (XSSFFont) workbook.createFont(); 
        testoPiccolo.setFontHeightInPoints((short)8); 
        styleTestoPiccolo.setFont(testoPiccolo);
        styleTestoPiccolo.setAlignment(HorizontalAlignment.CENTER);
        
        //imposta il font con una dimenzione 10
        XSSFFont testoNormale= (XSSFFont) workbook.createFont(); 
        testoNormale.setFontHeightInPoints((short)10); 
        styleTestoNormale.setFont(testoNormale);
        styleTestoNormale.setAlignment(HorizontalAlignment.CENTER);
        
        //le prime due righe del foglio sostituzioni
        Row header1Sost = sheetSostituzioni.createRow(0);
        Row header2Sost = sheetSostituzioni.createRow(1);
        
        //crea il colore giallo rgb(238,205,0)
        XSSFCellStyle styleColorato=(XSSFCellStyle) workbook.createCellStyle(); 
        XSSFCellStyle styleColoratoConBordi=(XSSFCellStyle) workbook.createCellStyle(); 
        byte[] rgb = new byte[3];
        rgb[0] = (byte) 238; // rosso
        rgb[1] = (byte) 205; // verde
        rgb[2] = (byte) 0; // blu
        XSSFColor giallo = new XSSFColor(rgb); 
        
        //bordi
        styleColoratoConBordi.setBorderTop(BorderStyle.MEDIUM);
        styleColoratoConBordi.setBorderBottom(BorderStyle.MEDIUM);
        styleColoratoConBordi.setBorderLeft(BorderStyle.MEDIUM);
        styleColoratoConBordi.setBorderRight(BorderStyle.MEDIUM);
        
        //colora la riga header1 di giallo
        styleColorato.setFillForegroundColor(giallo); 
        styleColorato.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        //imposta il font grassetto
        styleColorato.setFont(grassetto);
        
        styleColoratoConBordi.setFillForegroundColor(giallo); 
        styleColoratoConBordi.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        //imposta il font grassetto
        styleColoratoConBordi.setFont(grassetto);
        
        header1Sost.setRowStyle(styleColorato);
        
        Cell scrittaDocente = header1Sost.createCell(0);
        scrittaDocente.setCellValue("SOSTITUZIONI");
        scrittaDocente.setCellStyle(styleGrassetto);
        scrittaDocente.setCellStyle(styleColorato);
        
        Cell scrittaDocenteAssente = header2Sost.createCell(0);
        scrittaDocenteAssente.setCellValue("DOCENTE ASSENTE");
        scrittaDocenteAssente.setCellStyle(styleGrassetto);
        
        //array list che contiene tutte le righe tranne le prime 2 (header1; header2)
        ArrayList<Row> righeProfDaSost = new ArrayList<Row>();
        
    	// Scrive l'intestazione del foglio, scrivendo le ORE nella prima riga (header1)
    	//  e CLASSE SUPPLENTE MOTIVO nella seconda riga (header2)
        for(int j=0; j<8; j++) {
			Cell ora = header1Sost.createCell(j*3+1);
			ora.setCellValue(j+1+"° ORA");
			ora.setCellStyle(styleColorato);
			
			Cell scrittaClasse = header2Sost.createCell(j*3+1);
			scrittaClasse.setCellValue("Classe");
			scrittaClasse.setCellStyle(styleGrassetto);
			
			Cell supplente = header2Sost.createCell(j*3+2);
			supplente.setCellValue("Supplente");
			supplente.setCellStyle(styleGrassetto);
			
			Cell motivo = header2Sost.createCell(j*3+3);
			motivo.setCellValue("Motivo");
			motivo.setCellStyle(styleGrassetto);
		}
        
        // aggiunge nell'array list "professoriDaSostituire" il nome del docente 
    	// da sostituire nel giorno selezionato;
    	// e aggiunge nell'array list "righeProf" le righe tante quante sono i prof.
        int count=2;
        for(int i=0; i<tutteSostituzioni.size(); i++) {
        	if(data.equals(tutteSostituzioni.get(i).getData())) {
        		if(!professoriDaSostituire.contains(tutteSostituzioni.get(i).getNomeDocenteDaSostituire())) {
					
        			professoriDaSostituire.add(tutteSostituzioni.get(i).getNomeDocenteDaSostituire());
					Row riga= sheetSostituzioni.createRow(count++);
					righeProfDaSost.add(riga);
				}
        	}
        }
        
        //scrive i nomi dei prof da sostituire nella prima colonna del foglio 
        for(int i=0; i<tutteSostituzioni.size(); i++) {
        	if(data.equals(tutteSostituzioni.get(i).getData())) {
        		for(int j=0;j<professoriDaSostituire.size();j++) {
        			
        			Cell prof = righeProfDaSost.get(j).createCell(0);
        			prof.setCellValue(professoriDaSostituire.get(j));
        			prof.setCellStyle(styleTestoNormale);
        		}	
        	}
        }
        
        //scrive nelle colonne apposite CLASSE; SUPPLENTE; MOTIVO nella
        //stessa riga del prof da sostituire
        for(int i=0; i<tutteSostituzioni.size(); i++) {
        	if(data.equals(tutteSostituzioni.get(i).getData())) {
        		for(int j=0;j<professoriDaSostituire.size();j++) {
        			if(professoriDaSostituire.get(j).equals(tutteSostituzioni.get(i).getNomeDocenteDaSostituire())) {
        				//scrive la CLASSE
        				Cell classe = righeProfDaSost.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+1);
            			classe.setCellValue(tutteSostituzioni.get(i).classe);
            			classe.setCellStyle(styleTestoNormale);
            			//scrive il SUPPLENTE
            			Cell supplente = righeProfDaSost.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+2);
            			supplente.setCellValue(tutteSostituzioni.get(i).getNomeSostituto());
            			supplente.setCellStyle(styleTestoNormale);
            			//scrive il MOTIVO
            			Cell motivo = righeProfDaSost.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+3);
            			motivo.setCellValue(tutteSostituzioni.get(i).getMotivazione()+"");
            			motivo.setCellStyle(styleTestoPiccolo);
            		}
        		}
        		
        	}
        }
        //ridimenziona tutte le colonne
        for(int i=0;i<25; i++) { 
        	sheetSostituzioni.autoSizeColumn(i);
        }
        //contiene tutti i supplenti
        ArrayList<String> professoriSupplenti = new ArrayList<String>();
        
        //nuovo foglio per i biglietti
        Sheet sheetBiglietti = workbook.createSheet("Biglietti");
        sheetBiglietti.setColumnWidth(0, 6000);
        sheetBiglietti.setColumnWidth(1, 4000);
        
        //inserisce tutti i supplenti dentro l'arrayList professoriSupplenti
        count=0;
        for(int i=0; i<tutteSostituzioni.size(); i++) {
        	if(data.equals(tutteSostituzioni.get(i).getData())) {
        		if(!professoriSupplenti.contains(tutteSostituzioni.get(i).getNomeSostituto())) {
					
        			professoriSupplenti.add(tutteSostituzioni.get(i).getNomeSostituto());
				}
        	}
        }
        
        //for che crea tutti i biglietti
        int spazioRiga=0;
        for(int i=0;i<professoriSupplenti.size();i++) {
        	sheetBiglietti.addMergedRegion(new CellRangeAddress(i*6+spazioRiga,i*6+spazioRiga,0,7));
        	
        	//scrive il titolo del biglietto 
        	CellRangeAddress celleBiglietto = new CellRangeAddress(i*6+spazioRiga, i*6+spazioRiga+5, 0, 7);
        	Row header1Biglietti = sheetBiglietti.createRow(i*6+spazioRiga);
        	Cell scrittaSupplente = header1Biglietti.createCell(0);
            scrittaSupplente.setCellValue("Sostituzioni per "+d.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            									+" per "+professoriSupplenti.get(i));
            
            //le righe che compongono il foglio
            Row header2Biglietti = sheetBiglietti.createRow(i*6+1+spazioRiga);
            Row rigaClasse = sheetBiglietti.createRow(i*6+3+spazioRiga);
            Row rigaAula = sheetBiglietti.createRow(i*6+4+spazioRiga);
            Row rigaMotivo = sheetBiglietti.createRow(i*6+5+spazioRiga);
            //aggiunge le scritte ore
            for(int j=0; j<8; j++) {
    			Cell ora = header2Biglietti.createCell(j);
    			ora.setCellValue(j+1+"° ORA");
    			ora.setCellStyle(styleColoratoConBordi);

            }
            //scive gli orari delle ore
            Row header3Biglietti = sheetBiglietti.createRow(i*6+2+spazioRiga);
            //1° ora
            Cell orario = header3Biglietti.createCell(0);
            orario.setCellValue("8:00");
            orario.setCellStyle(styleColoratoConBordi);
            //2° ora
            Cell orario1 = header3Biglietti.createCell(1);
            orario1.setCellValue("8:55");
            orario1.setCellStyle(styleColoratoConBordi);
            //3° ora
            Cell orario2 = header3Biglietti.createCell(2);
            orario2.setCellValue("10:00");
            orario2.setCellStyle(styleColoratoConBordi);
            //4° ora
            Cell orario3 = header3Biglietti.createCell(3);
            orario3.setCellValue("10:55");
            orario3.setCellStyle(styleColoratoConBordi);
            //5° ora
            Cell orario4 = header3Biglietti.createCell(4);
            orario4.setCellValue("11:55");
            orario4.setCellStyle(styleColoratoConBordi);
            //6° ora
            Cell orario5 = header3Biglietti.createCell(5);
            orario5.setCellValue("12:45");
            orario5.setCellStyle(styleColoratoConBordi);
            //7° ora
            Cell orario6 = header3Biglietti.createCell(6);
            orario6.setCellValue("14:30");
            orario6.setCellStyle(styleColoratoConBordi);
            //8° ora
            Cell orario7 = header3Biglietti.createCell(7);
            orario7.setCellValue("15:30");
            orario7.setCellStyle(styleColoratoConBordi);
            
            //aggiunge un meno a tutte le ore del biglietto
            for(int j=0;j<8;j++) {
            	Cell classe = rigaClasse.createCell(j);
    			classe.setCellValue("-");
    			classe.setCellStyle(styleVerticale);
            }
            
            //la parte che aggiunge sul foglio il nome della classe dove in quel
            //ora sta insegnando
            ArrayList<Docente> tuttiDocenti = Ambiente.getDocenti();
            for(int j=0;j<tuttiDocenti.size();j++) {
            	if(tuttiDocenti.get(j).nome.equals(professoriSupplenti.get(i))) {
            		ArrayList<OraLezione> tutteOre = tuttiDocenti.get(j).oreLezione;
            		for(int k=0;k<tutteOre.size();k++) {
            			if((tutteOre.get(k).giorno+"").equals(d.getDayOfWeek().getValue()+"")) {
            				Cell classe = rigaClasse.createCell(tutteOre.get(k).orario-1);
                			classe.setCellValue(tutteOre.get(k).classe);
                			classe.setCellStyle(styleVerticale);
            			}
            			
            		}
            		
            	}
            }
            
            //scrive le sostituzioni che ha fatto il supplente in quella giornata 
            for(int j=0;j<tutteSostituzioni.size();j++) {
    			if(professoriSupplenti.get(i).equals(tutteSostituzioni.get(j).getNomeSostituto())) {
    				//aggiunge il colore rosso
    		        byte[] rgbRosso = new byte[3];
    		        rgbRosso[0] = (byte) 255; // rosso
    		        rgbRosso[1] = (byte) 51; // verde
    		        rgbRosso[2] = (byte) 0; // blu
    		        XSSFColor rosso = new XSSFColor(rgbRosso); 
    				
    		        //lo stile della sostituzione nel biglietto
    				CellStyle colore = workbook.createCellStyle(); 
    				colore.setFillForegroundColor(rosso); 
    		        colore.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
    		        colore.setFont(testoNormale);
    		        colore.setAlignment(HorizontalAlignment.CENTER);
    		        colore.setFont(grassetto);
    				
    				//scrive la CLASSE
    				Cell classe = rigaClasse.createCell((tutteSostituzioni.get(j).orario-1));
        			classe.setCellValue(tutteSostituzioni.get(j).classe);
        			classe.setCellStyle(colore);
        			//scrive l'AULA
        			Cell aula = rigaAula.createCell((tutteSostituzioni.get(j).orario-1));
        			aula.setCellValue(tutteSostituzioni.get(j).aula);
        			aula.setCellStyle(colore);
        			//scrive il MOTIVO
        			Cell motivo = rigaMotivo.createCell((tutteSostituzioni.get(j).orario-1));
        			motivo.setCellValue(tutteSostituzioni.get(j).getMotivazione()+"");
        			motivo.setCellStyle(colore);
        		}
    		}
            spazioRiga++;
            
            //disegna i bordi del biglietto
            RegionUtil.setBorderTop(BorderStyle.MEDIUM, celleBiglietto, sheetBiglietti);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, celleBiglietto, sheetBiglietti);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM, celleBiglietto, sheetBiglietti);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, celleBiglietto, sheetBiglietti);

            //controlla se le celle sono vuote e se si le unisce
            for(int j=0;j<8;j++) {
            	Cell c=rigaMotivo.getCell(j);
            	if(c == null || c.getCellType() == CellType.BLANK) {
            		sheetBiglietti.addMergedRegion(new CellRangeAddress(i*6+spazioRiga+2, i*6+spazioRiga+4, j, j));
            		
            	}
            }
  
        }
        //ridimenziona tutte le colonne 
        for(int i=0;i<25; i++) { 
        	sheetBiglietti.autoSizeColumn(i);
        }

        //imposta come file location la cartella dove vengono salvati i biglietti
        String fileLocation = tfCartellaFile.getText()+File.separator+"Giornata-"+
        		d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".xlsx";
        
        //scrive il file e lo chiude
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
        System.out.println("done "+fileLocation);
        
        //scrive sulla label "scrittaNomeFile" il nome del file xlsx che è stato salvato
        scrittaNomeFile.setText("Salvata nel file: Giornata-"+
        		d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".xlsx");
    }
    @FXML
    private void gestioneSelezioneCartellaFile (ActionEvent e) {
        // creare FileChooser
        DirectoryChooser selettoreCartella = new DirectoryChooser();
        // indicare cartella di partenza
        String cartellaPartenza = System.getProperties().getProperty("user.home");
        selettoreCartella.setInitialDirectory(new File(cartellaPartenza));
        // aprire la finestra e recuperare file selezionato
        File fileSelezionato = selettoreCartella.showDialog( null );
        // metto il nome sulla casella
        tfCartellaFile.setText( fileSelezionato.toString() );
    }
    @FXML
	DatePicker dataPicker;
    @FXML
    TextField tfCartellaFile;
    @FXML
	Button selezionaCartellaFile;
    @FXML
    Label scrittaNomeFile;
}
