package it.edu.iisgubbio.sostituzioni;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FinestraPreferenze {
    @FXML
    Button pSelezionaFileFET;
    @FXML
    Button pSelezionaFileTabella;
    @FXML
    Button pSelezionaFileGiornale;
    @FXML
    Button pApplica;
    @FXML
    TextField cFileFET;
    @FXML
    TextField cFileTabella;
    @FXML
    TextField cFileGiornale;
    
    @FXML
    /********************************************************************************************
     * In questo metodo si impostano i valori di alcuni oggetti presenti nella finestra
     *******************************************************************************************/   
    void initialize() {
        try {
            cFileFET.setText(Ambiente.getFileOrarioFET().toString());
        }catch(Exception x) {
            ; // non faccio nulla vuol dire che la proprietà non c'è e deve inserirla
        }
        try {
            cFileTabella.setText(Ambiente.getFileOrarioExcel().toString());
        }catch(Exception x) {
            ; // non faccio nulla vuol dire che la proprietà non c'è e deve inserirla
        }
        try {
            cFileGiornale.setText(Ambiente.getFileGiornale().toString());
        }catch(Exception x) {
            ; // non faccio nulla vuol dire che la proprietà non c'è e deve inserirla
        }
    }
    
    @FXML
    private void gestioneApplica() {
        String fileFET = cFileFET.getText();
        String fileTabella = cFileTabella.getText();
        String fileGiornale = cFileGiornale.getText();
        Ambiente.setFileOrarioFET( new File(fileFET) );
        Ambiente.setFileOrarioExcel( new File(fileTabella) );
        Ambiente.setFileGiornale( new File(fileGiornale) );
        Ambiente.salvaProprieta();
        Stage stage = (Stage) pSelezionaFileFET.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void gestioneSelezioneFileFET(ActionEvent e) {
        // creare FileChooser
        FileChooser selettoreFile = new FileChooser();
        // indicare cartella di partenza
        String cartellaPartenza = System.getProperties().getProperty("user.home");
        selettoreFile.setInitialDirectory(new File(cartellaPartenza));
        // creare filtro per estensione file
        selettoreFile.getExtensionFilters().addAll(new ExtensionFilter("File FET", "*.xml"));
        // aprire la finestra e recuperare file selezionato
        File fileSelezionato = selettoreFile.showOpenDialog( null );
        // metto il nome sulla casella
        cFileFET.setText( fileSelezionato.toString() );
    }
    
    @FXML
    private void gestioneSelezioneFileTabella(ActionEvent e) {
        // creare FileChooser
        FileChooser selettoreFile = new FileChooser();
        // indicare cartella di partenza
        String cartellaPartenza = System.getProperties().getProperty("user.home");
        selettoreFile.setInitialDirectory(new File(cartellaPartenza));
        // creare filtro per estensione file
        selettoreFile.getExtensionFilters().addAll(new ExtensionFilter("File dati Excel", "*.xlsx", "*.xlsm"));
        // aprire la finestra e recuperare file selezionato
        File fileSelezionato = selettoreFile.showOpenDialog( null );
        // metto il nome sulla casella
        cFileTabella.setText( fileSelezionato.toString() );
    }
    
    @FXML
    private void gestioneSelezioneFileGiornale(ActionEvent e) {
        // creare FileChooser
        FileChooser selettoreFile = new FileChooser();
        // indicare cartella di partenza
        String cartellaPartenza = System.getProperties().getProperty("user.home");
        selettoreFile.setInitialDirectory(new File(cartellaPartenza));
        // creare filtro per estensione file
        selettoreFile.getExtensionFilters().addAll(new ExtensionFilter("File di testo", "*.txt"));
        // aprire la finestra e recuperare file selezionato
        File fileSelezionato = selettoreFile.showOpenDialog( null );
        // metto il nome sulla casella
        cFileGiornale.setText( fileSelezionato.toString() );
    }
}
