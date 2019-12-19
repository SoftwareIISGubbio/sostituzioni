package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.filtri.FiltroCoPresenza;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FinestraPrincipale extends Application {

	@FXML
	DatePicker data;
	@FXML
	ComboBox<String> cmbOra;
	@FXML
	ComboBox<String> nomeProf;
	@FXML
	ComboBox<String> cmbClasse;
	@FXML
	Button verifica;
	@FXML
	ListView<String> lista;

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
		// Ciclo per scorrere le classi e li inserisce alla combobox
		String[] classi = Elenchi.getNomiClassi();
		for (int i = 1; i <= 8; i++) {
			String n = "" + i;
			cmbOra.getItems().add(n);
		}
		for (int j = 0; j < classi.length; j++) {
			cmbClasse.getItems().add(classi[j]);
		}
		// Ciclo per scorrerel l'elenco dei professori e li inserisce alla combobox
		for (int i = 1; i <= 8; i++) {
			String n = "" + i;
			cmbOra.getItems().add(n);
		}

		for (int j = 0; j < Elenchi.docenti.size(); j++) {
			nomeProf.getItems().add(Elenchi.docenti.get(j).nome);
		}

	}

	@FXML
	private void gestioneClickPulsante(ActionEvent e) {
		//System.out.println(data.getValue());
		//System.out.println(cmbOra.getValue());
		//System.out.println(cmbClasse.getValue());
		LocalDate d = data.getValue();
		OraLezione oraDaSostituire = new OraLezione();
	//	System.out.println(d);
		oraDaSostituire.giorno = d.getDayOfWeek().getValue();
		oraDaSostituire.orario = Integer.parseInt(cmbOra.getValue());
		oraDaSostituire.classe = cmbClasse.getValue();
		//System.out.println("------------");
		System.out.println(oraDaSostituire);
		ArrayList<Docente> tuttiIDocenti = Elenchi.docenti;
		ArrayList<Docente> docentiCoPresenza;
		docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, oraDaSostituire);
		for (int i = 0; i < docentiCoPresenza.size(); i++) {
			System.out.println(docentiCoPresenza.get(i));
		}
		
	}
}