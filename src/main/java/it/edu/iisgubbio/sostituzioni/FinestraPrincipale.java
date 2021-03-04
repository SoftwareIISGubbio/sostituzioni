package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import it.edu.iisgubbio.sostituzioni.filtri.FiltroADisposizione;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroAPagamento;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroClasse;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroCoPresenza;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroGruppo;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroLibero;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroOreBuche;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroPotenziamento;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroRecupero;
import it.edu.iisgubbio.sostituzioni.filtri.RimozioneDocente;
import it.edu.iisgubbio.sostituzioni.gui.FabbricaDiCaselleOraLezione;
import it.edu.iisgubbio.sostituzioni.gui.FabbricaDiCaselleSostituzione;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione.Motivo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/************************************************************************************************
 * classe principale del programma, crea la finestra per la ricerca delle
 * sostituzioni
 ***********************************************************************************************/
public class FinestraPrincipale extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	ComboBox<String> nomeProf;
	@FXML
	DatePicker data;
	@FXML
	ListView<OraLezione> listaOreLezione;

	@FXML
	ImageView ivAttenzione;
	@FXML
	ListView<Sostituzione> listaSostituzioniPossibili;

	@FXML
	TextArea taDescrizioneSostituzione;
	@FXML
	Label lDescrizioneOreLezione;

	@FXML
	WebView ww;
	
	@FXML
	CheckBox cbOraRecupero;

	/********************************************************************************************
	 * Creo la finestra principale, non posso impostare qui i valori perché la
	 * finestra viene caricata da un file FXML
	 *******************************************************************************************/
	public void start(Stage x) {
		Scene scena = null;
		try {
			scena = new Scene(FXMLLoader.load(FinestraPrincipale.class.getResource("FinestraPrincipale.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		x.setScene(scena);
		x.setTitle("Sostituzioni " + Ambiente.VERSIONE);
		x.show();
	}

	@FXML
	/********************************************************************************************
	 * In questo metodo si impostano i valori di alcuni oggetti presenti nella
	 * finestra
	 *******************************************************************************************/
	void initialize() {
		// scorrere l'elenco dei professori e li inserisce alla combobox
		for (int j = 0; j < Ambiente.getDocenti().size(); j++) {
			nomeProf.getItems().add(Ambiente.getDocenti().get(j).nome);
		}

		Tooltip tt = new Tooltip();
		tt.setAnchorX(0);
		tt.setAnchorY(0);
		nomeProf.setTooltip(tt);
		new ComboBoxAutoComplete<String>(nomeProf);

		if (Ambiente.getProblemi().length() == 0) {
			ivAttenzione.setVisible(false);
		}

		listaSostituzioniPossibili.setCellFactory(new FabbricaDiCaselleSostituzione());
		listaOreLezione.setCellFactory(new FabbricaDiCaselleOraLezione());

		// XXX: è possibile che questa cosa si possa fare dal file FXML
		// ma siccome non trovo come quindi la metto qui
		listaSostituzioniPossibili.getSelectionModel().selectedItemProperty()
				.addListener(e -> gestioneSelezioneListaSostituzioni());
		listaOreLezione.getSelectionModel().selectedItemProperty().addListener(e -> gestioneListaOreLezione());
	}

	private void puliziaRisultati() {
		listaSostituzioniPossibili.getItems().clear();
		ww.getEngine().loadContent("<p>uh?</p>");
	}

	@FXML
	/********************************************************************************************
	 * apre la finestra con le informazioni del docente selezionato
	 *******************************************************************************************/
	private void gestioneInformazioniDocente(ActionEvent e) {
		Stage s = new Stage();
		Scene scena;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			scena = new Scene(fxmlLoader.load(getClass().getResource("InformazioniDocente.fxml").openStream()));
			FinestraInformazioniDocente controller = (FinestraInformazioniDocente) fxmlLoader.getController();
			s.setScene(scena);
			s.setTitle("info docente");
			s.show();
			Docente d = Ambiente.cercaDocentePerNome(nomeProf.getValue());
			controller.setDocente(d);
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	@FXML
	/********************************************************************************************
	 * apre la finestra con le informazioni del docente selezionato
	 *******************************************************************************************/
	private void gestioneStatistiche(ActionEvent e) {
		Stage s = new Stage();
		Scene scena;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			scena = new Scene(fxmlLoader.load(getClass().getResource("FinestraStatistiche.fxml").openStream()));
			s.setScene(scena);
			s.setTitle("statistiche docenti");
			s.show();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	@FXML
	/********************************************************************************************
	 * mostra una finestra con l'elenco dei problemi rilevati in fase di lettura dei
	 * file
	 *******************************************************************************************/
	private void mostraProblemi(Event e) {
		Stage s = new Stage();
		Scene scena;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			scena = new Scene(fxmlLoader.load(getClass().getResource("FinestraProblemi.fxml").openStream()));
			s.setScene(scena);
			s.setTitle("Problemi nei dati");
			s.show();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	@FXML
	/********************************************************************************************
	 * apre la finestra che contiene le informazioni del software
	 *******************************************************************************************/
	private void finestraInfoSoftware(ActionEvent e) {
		Stage s = new Stage();
		Scene scena;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(FinestraPrincipale.class.getResource("InfoProgramma.fxml"));
			scena = new Scene(fxmlLoader.load(getClass().getResource("InfoProgramma.fxml").openStream()));
			s.setScene(scena);
			s.setTitle("Informazioni Sowtware");
			s.show();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	@FXML
	/********************************************************************************************
	 * apre la finestra delle preferenze
	 *******************************************************************************************/
	private void finestraPreferenze(Event e) {
		Stage s = new Stage();
		Scene scena;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			scena = new Scene(fxmlLoader.load(getClass().getResource("Preferenze.fxml").openStream()));
			s.setScene(scena);
			s.setTitle("Preferenze");
			s.show();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	@FXML
	private void gestioneSalva(ActionEvent e) throws IOException, URISyntaxException {
		// recupero l'indice dell'elemento selezionato
		int indiceSelezionato = listaSostituzioniPossibili.getSelectionModel().getSelectedIndex();
		// recupero l'oggetto selezionato usando il suo indice
		Sostituzione s = listaSostituzioniPossibili.getItems().get(indiceSelezionato);
		
		Alert dialogoAllerta = new Alert(AlertType.CONFIRMATION, s.getDescrizione() + "\nLa sostituzione " + (cbOraRecupero.isSelected() ? "" : "NON ") + "verrà considerata come 'ora di recupero'.");

		Optional<ButtonType> risposta = dialogoAllerta.showAndWait();
		if (risposta.isPresent() && risposta.get() == ButtonType.OK) {
			s.setRecupero(cbOraRecupero.isSelected());
			Giornale.scriviRecord(s);
		}
	}

	@FXML
	private void gestioneSelezioneListaSostituzioni() {
		int indiceSelezionato = listaSostituzioniPossibili.getSelectionModel().getSelectedIndex();
		if (indiceSelezionato > -1) {
			Sostituzione sostituzioneScelta = listaSostituzioniPossibili.getItems().get(indiceSelezionato);
			Docente sostituto = Ambiente.cercaDocentePerNome(sostituzioneScelta.getNomeSostituto());
			String orarioGiornaliero[] = sostituto.descriviGiornata(sostituzioneScelta.giorno);
			String orarioGiornalieroModificato[] = orarioGiornaliero.clone();
			LocalDate d = data.getValue();
			String aulaSostituzione = sostituzioneScelta.aula == null || sostituzioneScelta.aula.isEmpty() ? "aula non specificata"
					: "aula " + sostituzioneScelta.aula;

			orarioGiornalieroModificato[sostituzioneScelta.orario - 1] = "<big>" + sostituzioneScelta.classe
					+ "</big>\n" + "[<small>" + aulaSostituzione + "</small>]";

			String descrizione = """
					<style>
					          p {
					              margin: 0.25em 0;
					          }

						table, th, td {
							font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
							border: 0.5px solid black;
							border-collapse: collapse;
						}

						th, td {
							padding: 0.1em 0.3em 0.1em 0.3em;
							border: 1px solid gray;
						}

						th {
							background-color: #ffc000;
							font-weight: normal;
						}

						td {
							text-align: center;
							vertical-align: middle;
						}

						small {
							font-size: 0.85em;
						}

						#oraDaSostituire {
							background-color: #ff3300;
							font-weight: bold;
						}
					</style>""" + "<p>Per il giorno " + d.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy"))
					+ "</p>" + "<p>" + "<p>Vista l'assenza di " + sostituzioneScelta.getNomeDocenteDaSostituire()
					+ "</p>" + sostituzioneScelta.getNomeSostituto() + " lo sostituirà nella classe "
					+ sostituzioneScelta.classe + " [" + aulaSostituzione + "]</p>" + "<p>Motivazione della scelta: "
					+ sostituzioneScelta.getMotivazione() + "</p>" + """
							<table>
								<tr>""";
			for (int i = 1; i <= 8; i++) {
				descrizione += String.format("\n<th>%s°ORA</th>", i);
			}
			descrizione += """
					</tr>
					<tr>
						<th>08:00</th>
						<th>08:55</th>
						<th>10:00</th>
						<th>10:55</th>
						<th>11:55</th>
						<th>12:45</th>
						<th>14:30</th>
						<th>15:30</th>
					</tr>
					<tr>""";
			for (int i = 1; i <= 8; i++) {
				String contenuto;
				try {
					contenuto = orarioGiornalieroModificato[i - 1];
				} catch (IndexOutOfBoundsException e) {
					contenuto = "-";
				}

				if (sostituzioneScelta.orario == i) {
					descrizione += String.format("<td id=\"oraDaSostituire\">%s</td>", contenuto);
					continue;
				}
				descrizione += String.format("<td>%s</td>", contenuto);
			}
			// System.out
			// .println(Arrays.toString(orarioGiornalieroModificato) + "\n" +
			// Arrays.toString(orarioGiornaliero));
			descrizione += """
						</tr>
					</table>
					""";
			// costruisco il messaggio della sostituzione
			ww.getEngine().loadContent(descrizione);
		}
	}

	@FXML
	private void gestioneSelezioneDataEDocente() {
		puliziaRisultati();
		LocalDate d = data.getValue();
		String nomeDocenteAssente = nomeProf.getValue();
		if (nomeDocenteAssente != null && d != null) {
			lDescrizioneOreLezione.setText("Ore da sostituire per " + nomeDocenteAssente + "\n"
					+ d.format(DateTimeFormatter.ofPattern("EEE dd MMMM, yyyy")));
			int giornoDellaSettimana = d.getDayOfWeek().getValue();
			Docente docenteAssente = Ambiente.cercaDocentePerNome(nomeDocenteAssente);
			listaOreLezione.getItems().clear();
			for (OraLezione o : docenteAssente.oreLezione) {
				if (o.giorno == giornoDellaSettimana) {
					listaOreLezione.getItems().add(o);
				}
			}
		}
	}

	@FXML
	/********************************************************************************************
	 * al click su una ora di lezione effettua la ricerca dei docenti in base ai
	 * dati richiesti
	 *******************************************************************************************/
	private void gestioneListaOreLezione() {
		int indiceSelezionato = listaOreLezione.getSelectionModel().getSelectedIndex();
		if (indiceSelezionato > -1) {
			String docenteAssente = nomeProf.getValue();
			String gruppoDocenteAssente = Ambiente.cercaDocentePerNome(docenteAssente).gruppo;
			String nomeDocenteDaSostituire = nomeProf.getItems().get(nomeProf.getSelectionModel().getSelectedIndex());
			String testoData = data.getValue().toString();

			// costruisco l'oggetto che rappresenta l'ora da sostituire
			OraLezione oraLezioneSelezionata = listaOreLezione.getItems().get(indiceSelezionato);
			LocalDate d = data.getValue();
			OraLezione oraDaSostituire = new OraLezione();
			oraDaSostituire.giorno = d.getDayOfWeek().getValue();
			oraDaSostituire.orario = oraLezioneSelezionata.orario;
			oraDaSostituire.classe = oraLezioneSelezionata.classe;
			oraDaSostituire.aula = oraLezioneSelezionata.aula;
			// TODO: verificare perché ignoriamo campo copresenza

			// rimuovo vecchia ricerca
			puliziaRisultati();
			ArrayList<Docente> tuttiIDocenti = Ambiente.getDocenti();
			// man mano che inserisco insegnanti nella ListView a schermo li tolgo da da
			// questo ArrayList
			// in modo da non ripeter due volte lo stesso insegnante
			tuttiIDocenti = RimozioneDocente.docentiRimozione(tuttiIDocenti, docenteAssente);

			// --------------- recupero tutti gli eventuali docenti in compresenza
			// --------------
			ArrayList<Docente> docentiCoPresenza;
			docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, oraDaSostituire);
			for (int i = 0; i < docentiCoPresenza.size(); i++) {
				Docente sostituto = docentiCoPresenza.get(i);
				Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																			// sostituione
						oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, true, sostituto.nome,
						testoData);
				s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
				s.setMotivazione(Motivo.copresenza);
				listaSostituzioniPossibili.getItems().add(s);
				tuttiIDocenti.remove(sostituto);
			}

			// ------------------------------------ potenziamento
			// -------------------------------
			{ // creo un blocco di visibilità locale in modo da poter fare copia/incolla
				// sotto!
				ArrayList<Docente> docentiPotenziamento = FiltroPotenziamento.docentiPotenziamento(tuttiIDocenti,
						oraDaSostituire);
				// prima i docenti di potenziamento di materie affini
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiPotenziamento, gruppoDocenteAssente,
						true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.potenziamento_stesse_discipline);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// poi tutti gli altri
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiPotenziamento, gruppoDocenteAssente,
						false)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.potenziamento_altre_discipline);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
			}

			// ------------------------------- recupero
			// -----------------------------------------
			{ // creo un blocco di visibilità locale in modo da poter fare copia/incolla
				// sotto!
				ArrayList<Docente> docentiRecupero;
				docentiRecupero = FiltroRecupero.docentiRecupero(tuttiIDocenti, oraDaSostituire);
				// e della stessa classe
				for (Docente docente : FiltroClasse.docentiDellaClasse(docentiRecupero, oraDaSostituire.classe, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.recupero_stessa_classe);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi
				ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiRecupero,
						oraDaSostituire.classe, false);
				// altre classi ma stesso gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.recupero_altra_classe_stesso_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi, gruppi dversi
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.recupero_altra_classe_altro_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
			}

			// ----------------- recupero docenti con l'ora cercata "a disposizione"
			// ------------
			{ // creo un blocco di visibilità locale in modo da poter fare copia/incolla
				// sotto!
				ArrayList<Docente> docentiADisposizione;
				docentiADisposizione = FiltroADisposizione.docentiADisposizione(tuttiIDocenti, oraDaSostituire);
				// e della stessa classe
				for (Docente docente : FiltroClasse.docentiDellaClasse(docentiADisposizione, oraDaSostituire.classe,
						true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_disposizione_stessa_classe);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi
				ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiADisposizione,
						oraDaSostituire.classe, false);
				// altre classi ma stesso gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_disposizione_altra_classe_stesso_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi, gruppi dversi
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_disposizione_altra_classe_altro_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
			}

			// ----------------- recupero docenti con l'ora cercata "a pagamento"
			// ---------------
			{
				ArrayList<Docente> docentiAPagamento;
				docentiAPagamento = FiltroAPagamento.docentiAPagamento(tuttiIDocenti, oraDaSostituire);
				// e della stessa classe
				for (Docente docente : FiltroClasse.docentiDellaClasse(docentiAPagamento, oraDaSostituire.classe,
						true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_pagamento_stessa_classe);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// non della stessa classe
				ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiAPagamento,
						oraDaSostituire.classe, false);
				// altre classi ma stesso gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_pagamento_altra_classe_stesso_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi e altro gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.a_pagamento_altra_classe_e_altro_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
			}

			// ----------------- recupero docenti con l'ora cercata "buca"
			// -------------------
			{
				ArrayList<Docente> docentiOreBuche;
				docentiOreBuche = FiltroOreBuche.docentiOreBuche(tuttiIDocenti, oraDaSostituire);
				// e della stessa classe
				for (Docente docente : FiltroClasse.docentiDellaClasse(docentiOreBuche, oraDaSostituire.classe, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.ora_buca_della_classe);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// non della stessa classe
				ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiOreBuche,
						oraDaSostituire.classe, false);
				// altre classi ma stesso gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.ora_buca_altra_classe_stesso_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
				// altre classi e altro gruppo di materie
				for (Docente docente : FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)) {
					Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																				// sostituione
							oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, docente.nome,
							testoData);
					s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
					s.setMotivazione(Motivo.ora_buca_altra_classe_altro_gruppo);
					listaSostituzioniPossibili.getItems().add(s);
					tuttiIDocenti.remove(docente);
				}
			}

			// un elenco di tutti i docenti liberi della classe
			ArrayList<Docente> docentiDellaClasse;
			docentiDellaClasse = FiltroClasse.docentiDellaClasse(tuttiIDocenti, oraDaSostituire.classe, true);
			ArrayList<Docente> docentiLiberiClasse = FiltroLibero.docentiLiberi(docentiDellaClasse, oraDaSostituire);
			for (int i = 0; i < docentiLiberiClasse.size(); i++) {
				Docente sostituto = docentiLiberiClasse.get(i);
				Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																			// sostituione
						oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false, sostituto.nome,
						testoData);
				s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
				s.setMotivazione(Motivo.libero_della_classe);
				listaSostituzioniPossibili.getItems().add(s);
				tuttiIDocenti.remove(sostituto);
			}
			// alla fine tutti quelli liberi che si dividono in stesso gruppo e altro gruppo
			ArrayList<Docente> docentiLiberi = FiltroLibero.docentiLiberi(tuttiIDocenti, oraDaSostituire);
			ArrayList<Sostituzione> docentiLiberiAltroGruppo = new ArrayList<>();
			for (int i = 0; i < docentiLiberi.size(); i++) {
				Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la
																			// sostituione
						oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
						docentiLiberi.get(i).nome, testoData);
				s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
				if (docentiLiberi.get(i).gruppo.equals(gruppoDocenteAssente)) {
					s.setMotivazione(Motivo.libero_altra_classe_stesso_gruppo);
				} else {
					s.setMotivazione(Motivo.libero_altra_classe_altro_gruppo);
					// li salvo qui per inserirli dopo
					docentiLiberiAltroGruppo.add(s);
					continue;
				}
				listaSostituzioniPossibili.getItems().add(s);
			}
			for (Sostituzione sostituzione : docentiLiberiAltroGruppo) {
				listaSostituzioniPossibili.getItems().add(sostituzione);
			}
		}
	}
}