package it.edu.iisgubbio.sostituzioni;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FinestraInformazioniDocente {

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
	@FXML
    TextField cGruppo;

	Docente docente;

	@FXML
	public void initialize() {

	}

	public void setDocente(Docente d) {
		eNomeDocente.setText(d.nome);
		cGruppo.setText(d.gruppo);
		if (d.oraARecupero != null) {
		cOraRecupero.setText(d.oraARecupero.toString());
		}
		for (int i = 0; i < d.oreLezione.size(); i++) {
			lOreLezione.getItems().add(d.oreLezione.get(i).toString() + "\n");
		} 
		if (d.oraADisposizioneCassata != null) {
			cOraDisposizioneCassata.setText(d.oraADisposizioneCassata.toString());
		}
		if (d.oraADisposizioneGattapone != null) {
			cOraDisposizioneGattapone.setText(d.oraADisposizioneGattapone.toString());
		}
		
		if (d.oreAPagamento != null) {
			for (int i = 0; i < d.oreAPagamento.size(); i++) {
				lOrePagamento.getItems().add(d.oreAPagamento.get(i).toString() + "\n");
			
			}
		}
		if (d.orePotenziamento != null) {
			for (int i = 0; i < d.orePotenziamento.size(); i++) {
				lOrePotenziamento.getItems().add(d.orePotenziamento.get(i).toString() + "\n");
			}
		}
	}
}
