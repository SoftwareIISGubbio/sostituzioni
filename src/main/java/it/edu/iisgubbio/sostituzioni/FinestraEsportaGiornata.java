package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
        Sheet sheet = workbook.createSheet("Sostituzioni");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        
        //crea gli stili delle celle (grassetto; testoPiccolo; testoNormale)
        CellStyle styleGrassetto = workbook.createCellStyle(); 
        CellStyle styleTestoPiccolo = workbook.createCellStyle(); 
        CellStyle styleTestoNormale = workbook.createCellStyle(); 
        
        //imposta il font in grassetto con dimenzione caratteri 10
        XSSFFont grassetto= (XSSFFont) workbook.createFont(); 
        grassetto.setFontHeightInPoints((short)10); 
        grassetto.setBold(true); 
        styleGrassetto.setFont(grassetto);
        
        //imposta il font con dimenzione 8
        XSSFFont testoPiccolo= (XSSFFont) workbook.createFont(); 
        testoPiccolo.setFontHeightInPoints((short)8); 
        styleTestoPiccolo.setFont(testoPiccolo);
        
        //imposta il font con una dimenzione 10
        XSSFFont testoNormale= (XSSFFont) workbook.createFont(); 
        testoNormale.setFontHeightInPoints((short)10); 
        styleTestoNormale.setFont(testoNormale);
        
        //le prime due righe del foglio
        Row header1 = sheet.createRow(0);
        Row header2 = sheet.createRow(1);
        
        //crea il colore giallo rgb(238,205,0)
        XSSFCellStyle styleColorato=(XSSFCellStyle) workbook.createCellStyle(); 
        byte[] rgb = new byte[3];
        rgb[0] = (byte) 238; // rosso
        rgb[1] = (byte) 205; // verde
        rgb[2] = (byte) 0; // blu
        XSSFColor giallo = new XSSFColor(rgb); 
        
        //colora la riga header1 di giallo
        styleColorato.setFillForegroundColor(giallo); 
        styleColorato.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        //imposta il font grassetto
        styleColorato.setFont(grassetto);
        header1.setRowStyle(styleColorato);
        
        Cell scrittaDocente = header1.createCell(0);
        scrittaDocente.setCellValue("SOSTITUZIONI");
        scrittaDocente.setCellStyle(styleGrassetto);
        scrittaDocente.setCellStyle(styleColorato);
        
        Cell scrittaDocenteAssente = header2.createCell(0);
        scrittaDocenteAssente.setCellValue("DOCENTE ASSENTE");
        scrittaDocenteAssente.setCellStyle(styleGrassetto);
        
        //array list che contiene tutte le righe tranne le prime 2 (header1; header2)
        ArrayList<Row> righeProf = new ArrayList<Row>();
        
    	// Scrive l'intestazione del foglio, scrivendo le ORE nella prima riga (header1)
    	//  e CLASSE SUPPLENTE MOTIVO nella seconda riga (header2)
        for(int j=0; j<8; j++) {
			Cell ora = header1.createCell(j*3+1);
			ora.setCellValue(j+1+"° ORA");
			ora.setCellStyle(styleColorato);
			
			Cell scrittaClasse = header2.createCell(j*3+1);
			scrittaClasse.setCellValue("Classe");
			scrittaClasse.setCellStyle(styleGrassetto);
			
			Cell supplente = header2.createCell(j*3+2);
			supplente.setCellValue("Supplente");
			supplente.setCellStyle(styleGrassetto);
			
			Cell motivo = header2.createCell(j*3+3);
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
					Row riga= sheet.createRow(count++);
					righeProf.add(riga);
				}
        	}
        }
        
        //scrive i nomi dei prof da sostituire nella prima colonna del foglio 
        for(int i=0; i<tutteSostituzioni.size(); i++) {
        	if(data.equals(tutteSostituzioni.get(i).getData())) {
        		for(int j=0;j<professoriDaSostituire.size();j++) {
        			
        			Cell prof = righeProf.get(j).createCell(0);
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
        				Cell classe = righeProf.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+1);
            			classe.setCellValue(tutteSostituzioni.get(i).classe);
            			classe.setCellStyle(styleTestoNormale);
            			//scrive il SUPPLENTE
            			Cell supplente = righeProf.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+2);
            			supplente.setCellValue(tutteSostituzioni.get(i).getNomeSostituto());
            			supplente.setCellStyle(styleTestoNormale);
            			//scrive il MOTIVO
            			Cell motivo = righeProf.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+3);
            			motivo.setCellValue(tutteSostituzioni.get(i).getMotivazione()+"");
            			motivo.setCellStyle(styleTestoPiccolo);
            		}
        		}
        		
        	}
        }
        for(int i=0;i<25; i++) { 
        	sheet.autoSizeColumn(i);
        }
        
        //imposta come file location la cartella dove vengono salvati i biglietti
        String fileLocation = tfCartellaFile.getText()+File.separator+
        		d.format(DateTimeFormatter.ofPattern("EEEE-yyyy-MMMM-dd"))+".xlsx";
        
        //scrive il file e lo chiude
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
        System.out.println("done "+fileLocation);
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
}
