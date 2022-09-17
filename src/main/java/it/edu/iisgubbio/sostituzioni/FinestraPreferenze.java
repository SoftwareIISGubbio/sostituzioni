package it.edu.iisgubbio.sostituzioni;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FinestraPreferenze {
    @FXML
    Button pSelezionaFileTabella;
    @FXML
    Button pSelezionaFileGiornale;
    @FXML
    Button pSelezionaCartellaBiglietto;
    @FXML
    Button pApplica;
    @FXML
    TextField cFileTabella;
    @FXML
    TextField cFileGiornale;
    @FXML
    TextField cCartellaBiglietto;
    
    @FXML
    /********************************************************************************************
     * In questo metodo si impostano i valori di alcuni oggetti presenti nella finestra
     *******************************************************************************************/   
    void initialize() {
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
        try {
            cCartellaBiglietto.setText(Ambiente.getCartellaBiglietto().toString() );
        }catch(Exception x) {
            ; // non faccio nulla vuol dire che la proprietà non c'è e deve inserirla
        }
    }
    
    @FXML
    private void gestioneApplica() {
        String fileTabella = cFileTabella.getText();
        String fileGiornale = cFileGiornale.getText();
        String cartellaBiglietto = cCartellaBiglietto.getText();
        Ambiente.setFileOrarioExcel( new File(fileTabella) );
        Ambiente.setFileGiornale( new File(fileGiornale) );
        Ambiente.setCartellaBiglietto( new File(cartellaBiglietto) );
        Ambiente.salvaProprieta();
        Stage stage = (Stage) pSelezionaFileGiornale.getScene().getWindow();
        stage.close();
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
    
    @FXML
    private void gestioneSelezioneCartellaBiglietto(ActionEvent e) {
        // creare FileChooser
        DirectoryChooser selettoreCartella = new DirectoryChooser();
        // indicare cartella di partenza
        String cartellaPartenza = System.getProperties().getProperty("user.home");
        selettoreCartella.setInitialDirectory(new File(cartellaPartenza));
        // aprire la finestra e recuperare file selezionato
        File fileSelezionato = selettoreCartella.showDialog( null );
        // metto il nome sulla casella
        cCartellaBiglietto.setText( fileSelezionato.toString() );
    }
}
