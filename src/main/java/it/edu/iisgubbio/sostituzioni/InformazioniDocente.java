package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InformazioniDocente extends Application {

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
	    Docente d = Elenchi.cercaDocentePerNome("giammarioli");
	    this.setDocente(d);
	}
	
	public void setDocente(Docente d) {
	    eNomeDocente.setText(d.nome);
        // FIXME: mettere altre informazioni
	}
	
    public void start(Stage x) {
        Scene scena = null;
        try {
            scena = new Scene(FXMLLoader.load(FinestraPrincipale.class.getResource("InformazioniDocente.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x.setScene(scena);
        x.setTitle("Informazioni docente");
        x.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}