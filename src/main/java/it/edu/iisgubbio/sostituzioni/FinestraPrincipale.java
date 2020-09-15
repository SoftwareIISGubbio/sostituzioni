package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import it.edu.iisgubbio.sostituzioni.filtri.FiltroADisposizioneCassata;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroAPagamento;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroClasse;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroCoPresenza;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroGruppo;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroLibero;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroPotenziamento;
import it.edu.iisgubbio.sostituzioni.filtri.FiltroRecupero;
import it.edu.iisgubbio.sostituzioni.filtri.RimozioneDocente;
import it.edu.iisgubbio.sostituzioni.gui.FabbricaDiCaselle;
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
import javafx.scene.control.ButtonType;
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
 * classe principale del programma, crea la finestra per la ricerca delle sostituzioni
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
	ComboBox<String> cmbOra;
	@FXML
	ComboBox<String> cmbClasse;

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
	
	/********************************************************************************************
	 * Legge il numero di versione dal file versione.txt contenuto nel pacchetto
	 * @return il numero di versione del programma
	 *******************************************************************************************/
	private String leggiVersione(){
	    try {
	        InputStream is = FinestraPrincipale.class.getResourceAsStream("versione.txt");
	        InputStreamReader isr = new InputStreamReader(is);
	        int numero;
	        char buffer[] = new char[20];
	        numero = isr.read(buffer);
	        isr.close();
	        is.close();
	        return new String(buffer,0,numero);
	    }catch(Exception ex) {
	        return "V?";
	    }
	}

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
		x.setTitle("Sostituzioni "+leggiVersione());
		x.show();
	}

	@FXML
	/********************************************************************************************
	 * In questo metodo si impostano i valori di alcuni oggetti presenti nella finestra
	 *******************************************************************************************/	
	void initialize() {
		// scorrere le classi e li inserisce alla combobox
		String[] classi = Ambiente.getNomiClassi();
		for (int j = 0; j < classi.length; j++) {
			cmbClasse.getItems().add(classi[j]);
		}
		// inserisce i nomi delle ore nel combo box 
	    for (int i = 1; i <= 8; i++) {
	        String n = "" + i;
	        cmbOra.getItems().add(n);
	    }
		// scorrere l'elenco dei professori e li inserisce alla combobox
		for (int j = 0; j < Ambiente.docenti.size(); j++) {
			nomeProf.getItems().add(Ambiente.docenti.get(j).nome);
		}
		
		Tooltip tt = new Tooltip();
		tt.setAnchorX(0);tt.setAnchorY(0);
		nomeProf.setTooltip( tt );
		new ComboBoxAutoComplete<String>(nomeProf);
		
		if(Ambiente.getProblemi().length()==0) {
		    ivAttenzione.setVisible(false);
		}
		
        listaSostituzioniPossibili.setCellFactory(new FabbricaDiCaselle());
        
        // XXX: è possibile che questa cosa si possa fare dal file FXML 
        // ma siccome non trovo come quindi la metto qui
        listaSostituzioniPossibili.getSelectionModel().selectedItemProperty().addListener( e -> gestioneSelezioneListaSostituzioni() );
        listaOreLezione.getSelectionModel().selectedItemProperty().addListener( e -> gestioneListaOreLezione() );
        
        // se cambi ora o classe pulisco, attenzione che sovrascrivo altri eventuali listener
        cmbOra.setOnAction( e-> puliziaRisultati());
        cmbClasse.setOnAction( e-> puliziaRisultati());
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
            scena = new Scene(  fxmlLoader.load(getClass().getResource("InformazioniDocente.fxml").openStream()) );
            FinestraInformazioniDocente controller = (FinestraInformazioniDocente) fxmlLoader.getController();
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
     * apre la finestra con le informazioni del docente selezionato
     *******************************************************************************************/
    private void gestioneStatistiche(ActionEvent e) {
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(getClass().getResource("FinestraStatistiche.fxml").openStream()) );
            s.setScene(scena);
            s.setTitle("statistiche docenti");
            s.show();
        } catch (IOException x) {
            x.printStackTrace();
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
	/********************************************************************************************
	 * apre la finestra che contiene le informazioni del software
	 *******************************************************************************************/
	private void finestraInfoSoftware(ActionEvent e) {
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
	
    @FXML
    /********************************************************************************************
     * apre la finestra delle preferenze
     *******************************************************************************************/    
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
	
    /********************************************************************************************
     * Legge le informazioni dalla data selezionata e dall'ora selezionata
     * @return 
     *******************************************************************************************/
	private OraLezione leggiOraLezione() {
	    LocalDate d = data.getValue();
	    OraLezione oraDaSostituire = new OraLezione();
        oraDaSostituire.giorno = d.getDayOfWeek().getValue();
        oraDaSostituire.orario = Integer.parseInt(cmbOra.getValue());
        oraDaSostituire.classe = cmbClasse.getValue();
        oraDaSostituire.aula = listaOreLezione.getItems().get(listaOreLezione.getSelectionModel().getSelectedIndex()).aula;
        return oraDaSostituire;
	}
	
	@FXML
	/********************************************************************************************
	 * effettua la ricerca dei docenti in base ai dati richiesti
	 *******************************************************************************************/
    private void gestioneCercaDocenteDisponibile(ActionEvent e) {
        String docenteAssente = nomeProf.getValue();
        String gruppoDocenteAssente = Ambiente.cercaDocentePerNome(docenteAssente).gruppo;
        OraLezione oraDaSostituire = leggiOraLezione();
        String nomeDocenteDaSostituire = nomeProf.getItems().get(nomeProf.getSelectionModel().getSelectedIndex());
        
        // rimuovo vecchia ricerca
        puliziaRisultati();
        ArrayList<Docente> tuttiIDocenti = Ambiente.docenti;
        tuttiIDocenti = RimozioneDocente.docentiRimozione(tuttiIDocenti, docenteAssente);

        // --------------- recupero tutti gli eventuali docenti in compresenza ------------------
        ArrayList<Docente> docentiCoPresenza;
        docentiCoPresenza = FiltroCoPresenza.docentiCoPresenza(tuttiIDocenti, oraDaSostituire);
        for (int i = 0; i < docentiCoPresenza.size(); i++) {
            Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                    oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, true,
                    docentiCoPresenza.get(i).nome);
            s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
            s.setMotivazione("copresenza");
            listaSostituzioniPossibili.getItems().add(s);
        }        

        // ------------------------------------ potenziamento -----------------------------------
        { // creo un blocco di visibilità locale in modo da poter fare copia/incolla sotto!
            ArrayList<Docente> docentiPotenziamento = FiltroPotenziamento.docentiPotenziamento(tuttiIDocenti, oraDaSostituire);
            // prima i docenti di potenziamento di materie affini
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiPotenziamento, gruppoDocenteAssente, true)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("potenziamento stesse discipline");
                listaSostituzioniPossibili.getItems().add(s);
            }
            // poi tutti gli altri
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiPotenziamento, gruppoDocenteAssente, false)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("potenziamento altre discipline");
                listaSostituzioniPossibili.getItems().add(s);
            }
        }

        // ------------------------------- recupero ---------------------------------------------
        { // creo un blocco di visibilità locale in modo da poter fare copia/incolla sotto!
            ArrayList<Docente> docentiRecupero;
            docentiRecupero = FiltroRecupero.docentiRecupero(tuttiIDocenti, oraDaSostituire);
            //  e della stessa classe
            for( Docente docente: FiltroClasse.docentiDellaClasse(docentiRecupero, oraDaSostituire.classe,true)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("recupero stessa classe");
                listaSostituzioniPossibili.getItems().add(s);
            }
            // altre classi
            ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiRecupero, oraDaSostituire.classe,false);
            // altre classi ma stesso gruppo di materie
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("recupero altra classe, stesso gruppo");
                listaSostituzioniPossibili.getItems().add(s);
            }
            // altre classi, gruppi dversi 
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("recupero altra classe, altro gruppo");
                listaSostituzioniPossibili.getItems().add(s);
            }
        }
        // ----------------- recupero docenti con l'ora cercata "a pagamento" -------------------
        {
            ArrayList<Docente> docentiAPagamento;
            docentiAPagamento = FiltroAPagamento.docentiAPagamento(tuttiIDocenti, oraDaSostituire);
            //  e della stessa classe
            for( Docente docente: FiltroClasse.docentiDellaClasse(docentiAPagamento, oraDaSostituire.classe,true)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("a pagamento stessa classe");
                listaSostituzioniPossibili.getItems().add(s);
            }
            // non della stessa classe 
            ArrayList<Docente> docentiAltreClassi = FiltroClasse.docentiDellaClasse(docentiAPagamento, oraDaSostituire.classe,false);
            // altre classi ma stesso gruppo di materie
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, true)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("a pagamento altra classe ma stesso gruppo");
                listaSostituzioniPossibili.getItems().add(s);
            }
            // altre classi e altro gruppo di materie
            for( Docente docente: FiltroGruppo.docentiDelGruppo(docentiAltreClassi, gruppoDocenteAssente, false)){
                Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                        oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                        docente.nome);
                s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
                s.setMotivazione("a pagamento altra classe e altro gruppo");
                listaSostituzioniPossibili.getItems().add(s);
            }
        }
        
        
    
        // recupero docenti con l'ora cercata "a disposizione" al Cassata
        for( Docente docente: FiltroADisposizioneCassata.docentiADisposizioneCassata(tuttiIDocenti, oraDaSostituire)){
            Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                    oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                    docente.nome);
            s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
            s.setMotivazione("a disposizione Cassata");
            listaSostituzioniPossibili.getItems().add(s);
        }
        // un elenco di tutti i docenti liberi della classe
        ArrayList<Docente> docentiDellaClasse;
        docentiDellaClasse = FiltroClasse.docentiDellaClasse(tuttiIDocenti, oraDaSostituire.classe, true);
        ArrayList<Docente> docentiLiberiClasse = FiltroLibero.docentiLiberi(docentiDellaClasse, oraDaSostituire);
        for (int i = 0; i < docentiLiberiClasse.size(); i++) {
            Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                    oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                    docentiLiberiClasse.get(i).nome);
            s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
            s.setMotivazione("libero della classe");
            listaSostituzioniPossibili.getItems().add(s);
        }
        // alla fine tutti quelli liberi
        ArrayList<Docente> docentiLiberi = FiltroLibero.docentiLiberi(tuttiIDocenti, oraDaSostituire);
        for (int i = 0; i < docentiLiberi.size(); i++) {
            Sostituzione s = new Sostituzione(oraDaSostituire.giorno, // giorno in cui dovrà essere fatta la sostituione
                    oraDaSostituire.orario, oraDaSostituire.aula, oraDaSostituire.classe, false,
                    docentiLiberi.get(i).nome);
            s.setNomeDocenteDaSostituire(nomeDocenteDaSostituire);
            s.setMotivazione("libero altra classe");
            listaSostituzioniPossibili.getItems().add(s);
        }

    }
	
	@FXML
	private void gestioneSalva(ActionEvent e) throws IOException, URISyntaxException {
        // recupero l'indice dell'elemento selezionato
        int indiceSelezionato = listaSostituzioniPossibili.getSelectionModel().getSelectedIndex();
        // recupero l'oggetto selezionato usando il suo indice
        Sostituzione s = listaSostituzioniPossibili.getItems().get(indiceSelezionato);
        
	    Alert dialogoAllerta = new Alert(AlertType.CONFIRMATION, 
	            s.getDescrizione());
	    
	    Optional<ButtonType> risposta = dialogoAllerta.showAndWait();
	    if(risposta.isPresent() && risposta.get() == ButtonType.OK) {
	        Giornale.scriviRecord(s);
	    }
	}
	
	@FXML
	private void gestioneSelezioneListaSostituzioni() {
	    Sostituzione sostituzioneScelta = listaSostituzioniPossibili.getItems().get(listaSostituzioniPossibili.getSelectionModel().getSelectedIndex());
	    // OraLezione oraDaSostituire = leggiOraLezione();
	    Docente sostituto = Ambiente.cercaDocentePerNome(sostituzioneScelta.getNomeSostituto());
	    String orarioGiornaliero[] = sostituto.descriviGiornata(sostituzioneScelta.giorno);
	    String orarioGiornalieroModificato[] = orarioGiornaliero.clone();
	    LocalDate d = data.getValue();
	    
	    orarioGiornalieroModificato[ sostituzioneScelta.orario-1 ] = "<big>"+sostituzioneScelta.classe+" ["+sostituzioneScelta.aula+"]</big>";
	    	    
	    String descrizione = "<p>vista l'assenza di "+sostituzioneScelta.getNomeDocenteDaSostituire()+"</p>"+
	            "<p> Per il giorno "+d.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy"))+"</p>"+
	            "<p>"+sostituzioneScelta.getNomeSostituto()+" lo sostituirà nella classe "+sostituzioneScelta.classe+" [aula "+sostituzioneScelta.aula+"]</p>"+
	            "<p>motivazione della scelta: "+sostituzioneScelta.getMotivazione()+"</p>"+
	            "<pre>vecchio orario: "+String.join("  ", orarioGiornaliero)+"</pre>"+
	            "<pre style=\"color:red;font-weight:bold\">nuovo orario: "+String.join("  ", orarioGiornalieroModificato)+"</pre>";
	    
	    // costruisco il messaggio della sostituzione
	    ww.getEngine().loadContent(descrizione);
	}
	
	@FXML
	private void gestioneSelezioneDataEDocente() {
	    puliziaRisultati();
	    LocalDate d = data.getValue();
        String nomeDocenteAssente = nomeProf.getValue();
        if(nomeDocenteAssente!=null && d!=null) {
            lDescrizioneOreLezione.setText("ore da sostituire per "+nomeDocenteAssente+"\n"+
                    d.format(DateTimeFormatter.ofPattern("EEE dd MMMM, yyyy")));
            int giornoDellaSettimana = d.getDayOfWeek().getValue();
            Docente docenteAssente = Ambiente.cercaDocentePerNome(nomeDocenteAssente);
            listaOreLezione.getItems().clear();
            for(OraLezione o: docenteAssente.oreLezione) {
                if(o.giorno==giornoDellaSettimana) {
                    listaOreLezione.getItems().add(o);
                }
            }
        }
	}
	
	@FXML
    private void gestioneListaOreLezione() {
	    int indiceSelezionato = listaOreLezione.getSelectionModel().getSelectedIndex();
	    if(indiceSelezionato>-1) {
    	    OraLezione ol =  listaOreLezione.getItems().get(indiceSelezionato);
    	    for(int i=0; i<cmbOra.getItems().size(); i++) {
    	        if(cmbOra.getItems().get(i).equals(""+ol.orario)) {
    	            cmbOra.getSelectionModel().select(i);
    	        }
    	    }
    	    for(int i=0; i<cmbClasse.getItems().size(); i++) {
                if(cmbClasse.getItems().get(i).equals(ol.classe)) {
                    cmbClasse.getSelectionModel().select(i);
                }
            }
	    }
	}
}