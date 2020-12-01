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
            /*if(item.getMotivazione().equals("a disposizione Cassata") || 
                    item.getMotivazione().equals("libero della classe") ||
                    item.getMotivazione().equals("libero altra classe") ) {
                this.setTextFill(Color.GRAY);
            }else {
                this.setTextFill(Color.BLACK);
            }*/
        	
        	this.setTextFill(Color.rgb(255, 255, 255));
        	
        	switch(item.getMotivazione()) {
        	case("copresenza"):
        		this.setStyle("-fx-background-color: #CC0000");
        		break;
        	case("potenziamento stesse discipline"):
        		this.setStyle("-fx-background-color: #FF0000");
        		break;
        	case("potenziamento altre discipline"):
        		this.setStyle("-fx-background-color: #FF8000");
        		break;
        	case("recupero stessa classe"):
        		this.setStyle("-fx-background-color: #FFFF00");
        		break;
        	case("recupero altra classe, stesso gruppo"):
        		this.setStyle("-fx-background-color: #80FF00");
        		break;
        	case("recupero altra classe, altro gruppo"):
        		this.setStyle("-fx-background-color: #33FF33");
        		break;
        	case("a pagamento stessa classe"):
        		this.setStyle("-fx-background-color: #33FF99");
        		break;
        	case("a pagamento altra classe ma stesso gruppo"):
        		this.setStyle("-fx-background-color: #33FFFF");
        		break;
        	case("a pagamento altra classe e altro gruppo"):
        		this.setStyle("-fx-background-color: #66B2FF");
        		break;
        	case("a disposizione Cassata"):
        		this.setStyle("-fx-background-color: #6666FF");
        		break;
        	case("libero della classe"):
        		this.setStyle("-fx-background-color: #B266FF");
        		break;
        	case("libero altra classe"):
        		this.setStyle("-fx-background-color: #FF99FF");
        		break;
        	}
        	
            Docente bersaglio = Ambiente.cercaDocentePerNome(item.getNomeSostituto());
            
            // this.setBackground(new Background(new BackgroundFill( Color.RED, null, null)));
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " ["+
            item.getMotivazione()+"]"+
            "  [recuperate "+(bersaglio.oreRecuperate+" su "+bersaglio.oreDaRecuperare)+"]";
        }
         
        this.setText(name);
        setGraphic(null);
    }
}