package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class FinestraPrincipale extends Application {

	@FXML DatePicker data;
	@FXML MenuButton txtOra;
	@FXML MenuButton txtClasse;
	@FXML Button verifica;
	

	public void start(Stage x) {

		Scene scena = null;
		try {
			scena = new Scene(FXMLLoader.load(FinestraPrincipale.class.getResource("FinestraPrincipale.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		x.setScene(scena);
		x.setTitle("Main Page");
		x.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
    @FXML
    void initialize() {
        System.out.println("questo metodo serve per inizializzare gli elementi dell'interfaccia");
    }
	
	@FXML
	private void gestioneClickPulsante(ActionEvent e){
		System.out.println(data.getValue());
		System.out.println(txtOra.getItems());

	}
	
}