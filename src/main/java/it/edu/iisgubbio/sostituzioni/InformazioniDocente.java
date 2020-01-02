package it.edu.iisgubbio.sostituzioni;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class InformazioniDocente {

	@FXML
	Label eNomeDocente;
	@FXML
	ListView<String> lOreLezione;
	@FXML
    TextField cOraRecupero;
	@FXML
	TextField cOraDisposizioneCassata;
	@FXML
	TextField cOraDisposizioneGattapone;
	@FXML
	ListView<String> lOrePagamento;
	@FXML
    ListView<String> lOrePotenziamento;
	
	Docente docente;
	
	@FXML
	public void initialize() {
	}
	
	public void setDocente(Docente d) {
	    eNomeDocente.setText(d.nome);
        // FIXME: mettere altre informazioni
	}

}