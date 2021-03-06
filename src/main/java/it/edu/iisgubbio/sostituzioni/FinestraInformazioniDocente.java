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
	TextField cOreDaRecuperare;
	@FXML
	TextField cOraRecupero;
	@FXML
	ListView<String> lOreDisposizioneCassata;
	@FXML
	ListView<String> lOreDisposizioneGattapone;
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
		cOreDaRecuperare.setText(""+d.oreDaRecuperare);
		if (d.oraARecupero != null) {
		cOraRecupero.setText(d.oraARecupero.toString());
		}
		for (int i = 0; i < d.oreLezione.size(); i++) {
			lOreLezione.getItems().add(d.oreLezione.get(i).toString() + "\n");
		}
		if (d.oreADisposizioneCassata != null) {
            for (int i = 0; i < d.oreADisposizioneCassata.size(); i++) {
                lOreDisposizioneCassata.getItems().add(d.oreADisposizioneCassata.get(i).toString() + "\n");
            }
        }
		if (d.oreADisposizioneGattapone != null) {
            for (int i = 0; i < d.oreADisposizioneGattapone.size(); i++) {
                lOreDisposizioneGattapone.getItems().add(d.oreADisposizioneGattapone.get(i).toString() + "\n");
            }
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
