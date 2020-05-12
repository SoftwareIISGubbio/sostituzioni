package it.edu.iisgubbio.sostituzioni;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FinestraProblemi {

    @FXML
    TextArea taDescrizioneProblemi;
    
    @FXML
    void initialize() {
        taDescrizioneProblemi.setText(Ambiente.getProblemi());
    }
    
    @FXML
    private void chiudi(ActionEvent e) {
        // https://stackoverflow.com/questions/13567019/close-fxml-window-by-code-javafx
        // get a handle to the stage
        Stage stage = (Stage) taDescrizioneProblemi.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
