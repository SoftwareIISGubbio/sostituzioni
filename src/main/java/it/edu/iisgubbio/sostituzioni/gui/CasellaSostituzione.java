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
        		this.setStyle("-fx-background-color: #ff1a1a");
        		break;
        	case potenziamento_altre_discipline:
        		this.setStyle("-fx-background-color: #ff6666");
        		break;
        		
        	case recupero_stessa_classe:
        	    this.setStyle("-fx-background-color: #4670e8");
        		break;
        	case recupero_altra_classe_stesso_gruppo:
        	    this.setStyle("-fx-background-color: #6688e8");
        		break;
        	case recupero_altra_classe_altro_gruppo:
        	    this.setStyle("-fx-background-color: #95aced");
        		break;
        		
        	case a_disposizione_stessa_classe:
                this.setStyle("-fx-background-color: #d67702");
                break;
            case a_disposizione_altra_classe_stesso_gruppo:
                this.setStyle("-fx-background-color: #ff9900");
                break;
            case a_disposizione_altra_classe_altro_gruppo:
                this.setStyle("-fx-background-color: #ffb340");
                break;
        		
        	case a_pagamento_stessa_classe:
        	    this.setStyle("-fx-background-color: #22943e");
        		break;
        	case a_pagamento_altra_classe_stesso_gruppo:
        	    this.setStyle("-fx-background-color: #45ad5f");
        		break;
        	case a_pagamento_altra_classe_e_altro_gruppo:
        	    this.setStyle("-fx-background-color: #74c288");
        		break;
        	
        	case ora_buca_della_classe:
        		this.setStyle("-fx-background-color: #FAE400");
        		break;
        	case ora_buca_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #FFF865");
        		break;
        	case ora_buca_altra_classe_altro_gruppo:
        		this.setStyle("-fx-background-color: #FFF9AA");
        		break;
        		
        	case lavora_ora_adiacente_della_classe:
        	    this.setStyle("-fx-background-color: #02cfcf");
        		break;
        	case lavora_ora_adiacente_altra_classe_stesso_gruppo:
        	    this.setStyle("-fx-background-color: #4cd9d9");
        		break;
        	case lavora_ora_adiacente_altra_classe_altro_gruppo:
        	    this.setStyle("-fx-background-color: #84e8e8");
        		break;
        		
        	case libero_della_classe:
        		this.setStyle("-fx-background-color: #9778ba");
        		break;
        	case libero_altra_classe_stesso_gruppo:
        		this.setStyle("-fx-background-color: #beabd4");
        		break;
        	case libero_altra_classe_altro_gruppo:
        		this.setStyle("-fx-background-color: #dcd0e8");
        		break;
        	default:
        		this.setStyle("-fx-background-color: red; -fx-text-fill: yellow");
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