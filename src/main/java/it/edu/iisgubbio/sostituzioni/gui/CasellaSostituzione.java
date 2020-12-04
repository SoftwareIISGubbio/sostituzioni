package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.Ambiente;
import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

// https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
public class CasellaSostituzione extends ListCell<Sostituzione>{
    
    @Override
    public void updateItem(Sostituzione item, boolean empty) {
        super.updateItem(item, empty);
 
        int index = this.getIndex();
        String name = null;
 
        if (item == null || empty) {
        
        } else {        	
        	this.setTextFill(Color.rgb(255, 255, 255));
        	
        	switch(item.getMotivazione()) {
        	case copresenza:
        		this.setStyle("-fx-background-color: #CC0000");
        		break;
        	case potenziamento_stesse_discipline:
        		this.setStyle("-fx-background-color: #FF0000");
        		break;
        	case potenziamento_altre_discipline:
        		this.setStyle("-fx-background-color: #FF8000");
        		break;
        	case recupero_stessa_classe:
        		this.setStyle("-fx-background-color: #FFFF00");
        		break;
        	case recupero_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #80FF00");
        		break;
        	case recupero_altra_classe_altro_gruppo:
        		this.setStyle("-fx-background-color: #33FF33");
        		break;
        	case a_pagamento_stessa_classe:
        		this.setStyle("-fx-background-color: #33FF99");
        		break;
        	case a_pagamento_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #33FFFF");
        		break;
        	case a_pagamento_altra_classe_e_altro_gruppo:
        		this.setStyle("-fx-background-color: #66B2FF");
        		break;
        	case a_disposizione_cassata:
        		this.setStyle("-fx-background-color: #6666FF");
        		break;
        	case libero_della_classe:
        		this.setStyle("-fx-background-color: #B266FF");
        		break;
        	case libero_altra_classe:
        		this.setStyle("-fx-background-color: #FF99FF");
        		break;
        	default:
        		this.setStyle("-fx-background-color: #727272");
        	}
        	
            Docente bersaglio = Ambiente.cercaDocentePerNome(item.getNomeSostituto());
            
            // this.setBackground(new Background(new BackgroundFill( Color.RED, null, null)));
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " ["+
            item.getMotivazione()+"]"+
            "  [recuperate "+(bersaglio.oreRecuperate+" su "+bersaglio.oreDaRecuperare)+"]";
        }
         
        this.setText(name);
        setGraphic(null);  // TODO: questo a cosa serve?
    }
}