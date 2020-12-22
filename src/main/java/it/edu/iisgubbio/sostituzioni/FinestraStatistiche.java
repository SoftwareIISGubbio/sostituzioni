package it.edu.iisgubbio.sostituzioni;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.ResocontoDocente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FinestraStatistiche {

    @FXML
    Button pCopiaCSV;
    @FXML
    TableView<ResocontoDocente> tabella;
    
    @FXML
    TableColumn<String, ResocontoDocente> colonnaNome;
    @FXML
    TableColumn<String, ResocontoDocente> colonnaFatte;
    @FXML
    TableColumn<String, ResocontoDocente> colonnaDaFare;
    @FXML
    TableColumn<String, ResocontoDocente> colonnaResidue;
    
    @FXML
    void initialize() {
        // http://tutorials.jenkov.com/javafx/tableview.html riadattato per FXML
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaFatte.setCellValueFactory(new PropertyValueFactory<>("oreFatte"));
        colonnaDaFare.setCellValueFactory(new PropertyValueFactory<>("oreDaFare"));
        colonnaResidue.setCellValueFactory(new PropertyValueFactory<>("oreResidue"));
        tabella.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );


        try {
            ResocontoDocente resoconti[] = new ResocontoDocente[Ambiente.docenti.size()];
            int i=0;
            // creo un resoconto per ogni docente
            for(Docente d: Ambiente.docenti) {
                resoconti[i] = new ResocontoDocente(d.nome);
                resoconti[i].setOreFatte(d.oreRecuperate);
                resoconti[i].setOreDaFare( d.oreDaRecuperare );
                i++;
            }
            // https://www.codejava.net/java-core/collections/sorting-arrays-examples-with-comparable-and-comparator
            Arrays.sort(resoconti);
            for(ResocontoDocente rd: resoconti) {
                tabella.getItems().add( rd);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert dialogoAllerta = new Alert(AlertType.ERROR, 
                    "situazione imprevista, contatta uno sviluppatore: "+ex.getMessage());
            dialogoAllerta.showAndWait();
        }
    }
    
    @FXML
    private void gestoreAzioneCopiaCSV() {
        StringBuilder dati = new StringBuilder("nome,ore_fatte,ore_da_fare,ore_residue");
        for(ResocontoDocente rd: tabella.getItems()) {
            dati.append(rd.getNome()+","+rd.getOreFatte()+","+rd.getOreDaFare()+","+rd.getOreResidue()+"\n");
        }
        // https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
        String myString = dati.toString();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    @FXML
    private void chiudi(ActionEvent e) {
        // https://stackoverflow.com/questions/13567019/close-fxml-window-by-code-javafx
        // get a handle to the stage
        Stage stage = (Stage) tabella.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
