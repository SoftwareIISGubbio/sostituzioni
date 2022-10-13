package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/************************************************************************************************
* crea la finestra per esportare in un foglio .xlsx tutte le supplenze della giornata
***********************************************************************************************/
public class FinestraEsportaGiornata {
    @FXML
    private void esportaGiornata(ActionEvent e) throws IOException {
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
        
        //le prime due righe del foglio
        Row header1 = sheet.createRow(0);
        Row header2 = sheet.createRow(1);
        
        Cell scrittaDocente = header1.createCell(0);
        scrittaDocente.setCellValue("SOSTITUZIONI");
        
        Cell scrittaDocenteAssente = header2.createCell(0);
        scrittaDocenteAssente.setCellValue("DOCENTE ASSENTE");
        
        //array list che contiene tutte le righe tranne le prime 2 (header1; header2)
        ArrayList<Row> righeProf = new ArrayList<Row>();
        
        /********************************************************************************************
    	 * Scrive l'intestazione del foglio, scrivendo le ORE nella prima riga (header1)
    	 * e CLASSE SUPPLENTE MOTIVO nella seconda riga (header2)
    	 *******************************************************************************************/
        for(int j=0; j<8; j++) {
			Cell ora = header1.createCell(j*3+1);
			ora.setCellValue(j+1+"° ORA");
			
			Cell scrittaClasse = header2.createCell(j*3+1);
			scrittaClasse.setCellValue("classe");
			
			Cell supplente = header2.createCell(j*3+2);
			supplente.setCellValue("supplente");

			Cell motivo = header2.createCell(j*3+3);
			motivo.setCellValue("motivo");
		}
        
        /********************************************************************************************
    	 * aggiunge nell'array list "professoriDaSostituire" il nome del docente 
    	 * da sostituire nel giorno selezionato;
    	 * e aggiunge nell'array list "righeProf" le righe tante quante sono i prof.
    	 *******************************************************************************************/
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
            			//scrive il SUPPLENTE
            			Cell supplente = righeProf.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+2);
            			supplente.setCellValue(tutteSostituzioni.get(i).getNomeSostituto());
            			//scrive il MOTIVO
            			Cell motivo = righeProf.get(j).createCell((tutteSostituzioni.get(i).orario-1)*3+3);
            			motivo.setCellValue(tutteSostituzioni.get(i).getMotivazione()+"");
            		}
        		}
        		
        	}
        }
        
        //imposta come file location la cartella dove vengono salvati i biglietti
        String fileLocation = Ambiente.getCartellaBiglietto()+"\\"+
        		d.format(DateTimeFormatter.ofPattern("EEEE-yyyy-MMMM-dd"))+".xlsx";
        
        //scrive il file e lo chiude
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
    @FXML
	DatePicker dataPicker;
}
