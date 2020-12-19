package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.scene.control.ListCell;

// https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
public class CasellaSostituzione extends ListCell<Sostituzione>{
    
    @Override
    public void updateItem(Sostituzione item, boolean empty) {
        super.updateItem(item, empty);
        int index = this.getIndex();
        String name = null;
        
        if (item != null && !empty) {
            if( this.isSelected() ) {
                this.getStyleClass().add("selezionato");
            } else {
                this.getStyleClass().remove("selezionato");
            }

        	switch(item.getMotivazione()) {
        	case copresenza:
        		this.setStyle("-fx-background-color: #f7f7f7");
        		break;
        	case potenziamento_stesse_discipline:
        		this.setStyle("-fx-background-color: #ff7f57");
        		break;
        	case potenziamento_altre_discipline:
        		this.setStyle("-fx-background-color: #eeb597");
        		break;
        	case recupero_stessa_classe:
        		this.setStyle("-fx-background-color: #6a87d8");
        		break;
        	case recupero_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #5390fe");
        		break;
        	case recupero_altra_classe_altro_gruppo:
        		this.setStyle("-fx-background-color: #adc8ff");
        		break;
        	case a_pagamento_stessa_classe:
        		this.setStyle("-fx-background-color: #3cb371");
        		break;
        	case a_pagamento_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #48e44f");
        		break;
        	case a_pagamento_altra_classe_e_altro_gruppo:
        		this.setStyle("-fx-background-color: #89ee8e");
        		break;
        	case a_disposizione_stessa_classe:
                this.setStyle("-fx-background-color: #ff8c00");
                break;
        	case a_disposizione_altra_classe_stesso_gruppo:
                this.setStyle("-fx-background-color: #ff9900");
                break;
        	case a_disposizione_altra_classe_altro_gruppo:
        	    this.setStyle("-fx-background-color: #ffb340");
                break;
        	case libero_della_classe:
        		this.setStyle("-fx-background-color: #9778ba");
        		break;
        	case libero_altra_classe:
        		this.setStyle("-fx-background-color: #beabd4");
        		break;
        	default:
        		this.setStyle("-fx-background-color: white");
        	}
        	
            Docente bersaglio = Ambiente.cercaDocentePerNome(item.getNomeSostituto());
            
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " ["+
            item.getMotivazione()+"]"+
            "  [recuperate "+(bersaglio.oreRecuperate+" su "+bersaglio.oreDaRecuperare)+"]";
        }
         
        this.setText(name);
    }
}