package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import it.edu.iisgubbio.sostituzioni.filtri.FiltroClasse;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroCoPresenza;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroLibero;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroRecupero;
import it.edu.iisgubbio.sostituzioni.filtri.RimozioneDocente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/************************************************************************************************
 * classe principale del programma, crea la finestra per la ricerca delle sostituzioni
 ***********************************************************************************************/
public class FinestraPrincipale extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

	@FXML
	DatePicker data;
	@FXML
	ComboBox<String> cmbOra;
	@FXML
	ComboBox<String> nomeProf;
	@FXML
	ComboBox<String> cmbClasse;
	@FXML
	ListView<String> lista;
	@FXML
	ImageView ivAttenzione;
	@FXML
	Button pSalva;

	/********************************************************************************************
	 * Creo la finestra principale, non posso impostare qui i valori perché 
	 * la finestra viene caricata da un file FXML
	 *******************************************************************************************/
	public void start(Stage x) {
		Scene scena = null;
		try {
			scena = new Scene(FXMLLoader.load(FinestraPrincipale.class.getResource("FinestraPrincipale.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		x.setScene(scena);
		x.setTitle("Sostituzioni");
		x.show();
	}

	@FXML
	/********************************************************************************************
	 * In questo metodo si impostano i valori di alcuni oggetti presenti nella finestra
	 *******************************************************************************************/	
	void initialize() {
		// Ciclo per scorrere le classi e li inserisce alla combobox
		String[] classi = Ambiente.getNomiClassi();
		for (int i = 1; i <= 8; i++) {
			String n = "" + i;
			cmbOra.getItems().add(n);
		}
		for (int j = 0; j < classi.length; j++) {
			cmbClasse.getItems().add(classi[j]);
		}
		// Ciclo per scorrere l'elenco dei professori e li inserisce alla combobox

		for (int j = 0; j < Ambiente.docenti.size(); j++) {
			nomeProf.getItems().add(Ambiente.docenti.get(j).nome);
			
		}
		
		if(Ambiente.getProblemi().length()==0) {
		    ivAttenzione.setVisible(false);
		}
	}
	
	@FXML
	/********************************************************************************************
	 * apre la finestra che contiene tutti i problemi riscontrati in fase di lettura del file
	 *******************************************************************************************/
    private void gestioneInformazioniDocente(ActionEvent e) {
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(getClass().getResource("InformazioniDocente.fxml").openStream()) );
            InformazioniDocente controller = (InformazioniDocente) fxmlLoader.getController();
            s.setScene(scena);
            s.setTitle("info docente");
            s.show();
            Docente d = Ambiente.cercaDocentePerNome( nomeProf.getValue() );
            controller.setDocente(d);
        } catch (IOException x) {
            x.printStackTrace();
        }
	}
	
	@FXML
	/********************************************************************************************
	 * apre la finestra che contiene le informazioni del software
	 *******************************************************************************************/
	private void finestraInfoSoftaware(ActionEvent e) {
		Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(FinestraPrincipale.class.getResource("InfoProgramma.fxml"));
            scena = new Scene(  fxmlLoader.load(getClass().getResource("InfoProgramma.fxml").openStream()) );
            s.setScene(scena);
            s.setTitle("Informazioni Sowtware");
            s.show();
        } catch (IOException x) {
            x.printStackTrace();
        }
	}
	
	private OraLezione leggiOraLezione() {
	    LocalDate d = data.getValue();
	    OraLezione oraDaSostituire = new OraLezione();
        oraDaSostituire.giorno = d.getDayOfWeek().getValue();
        oraDaSostituire.orario = Integer.parseInt(cmbOra.getValue());
        oraDaSostituire.classe = cmbClasse.getValue();
        return oraDaSostituire;
	}
	
	@FXML
	/********************************************************************************************
	 * effettua la ricerca dei docenti in base ai dati richiesti
	 *******************************************************************************************/
	private void gestioneCercaDocenteDisponibile(ActionEvent e) {
		String docenteAssente = nomeProf.getValue();
		OraLezione oraDaSostituire = leggiOraLezione();
		System.out.println(oraDaSostituire);
		
		ArrayList<Docente> tuttiIDocenti = Ambiente.docenti;
		tuttiIDocenti = RimozioneDocente.docentiRimozione(tuttiIDocenti, docenteAssente);
		
		ArrayList<Docente> docentiCoPresenza;
		docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, oraDaSostituire);
		for (int i = 0; i < docentiCoPresenza.size(); i++) {
			System.out.println(docentiCoPresenza.get(i));
			lista.getItems().add(docentiCoPresenza.get(i).nome+"   (copresenza)");
		}
		
		ArrayList<Docente> docentiRecupero;
		docentiRecupero=FiltroRecupero.docentiRecupero( tuttiIDocenti, oraDaSostituire);
		for (int i = 0; i < docentiRecupero.size(); i++) {
			lista.getItems().add(docentiRecupero.get(i).nome+"   (recupero)");
		}
		
		ArrayList<Docente> docentiDellaClasse;
		docentiDellaClasse = FiltroClasse.docentiDellaClasse(tuttiIDocenti, oraDaSostituire.classe);
		ArrayList<Docente> docentiLiberiClasse = FiltroLibero.docentiLiberi(docentiDellaClasse, oraDaSostituire);
		for (int i = 0; i < docentiLiberiClasse.size(); i++) {
			lista.getItems().add(docentiLiberiClasse.get(i).nome+"   (della classe)");
		}
		
		ArrayList<Docente> docentiLiberi = FiltroLibero.docentiLiberi(tuttiIDocenti, oraDaSostituire);
		for (int i = 0; i < docentiLiberi.size(); i++) {
			lista.getItems().add(docentiLiberi.get(i).nome+"   (liberi)");
		}

	}
	
	@FXML
	/********************************************************************************************
	 * mostra una finestra con l'elenco dei problemi rilevati in fase di lettura dei file
	 *******************************************************************************************/
	private void mostraProblemi(Event e) {
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(getClass().getResource("FinestraProblemi.fxml").openStream()) );
            s.setScene(scena);
            s.setTitle("Problemi nei dati");
            s.show();
        } catch (IOException x) {
            x.printStackTrace();
        }
	}
	
	@FXML
	private void finestraPreferenze(Event e) {
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(getClass().getResource("Preferenze.fxml").openStream()) );
            s.setScene(scena);
            s.setTitle("Preferenze");
            s.show();
        } catch (IOException x) {
            x.printStackTrace();
        }
	}
	
	@FXML
	private void gestioneSalva(ActionEvent e) {
	    OraLezione oraDaSostituire = leggiOraLezione();
	    
	    Sostituzione s = new Sostituzione(oraDaSostituire.giorno, 
	            oraDaSostituire.orario, oraDaSostituire.aula, 
	            oraDaSostituire.classe, oraDaSostituire.compresenza, "pluto");
	    Alert dialogoAllerta = new Alert(AlertType.CONFIRMATION, 
	            s.getDescrizione());
	    Optional<ButtonType> risposta = dialogoAllerta.showAndWait();
	    if(risposta.isPresent() && risposta.get() == ButtonType.OK) {
	        // TODO
	    }
	
	}
}