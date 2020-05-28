package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.scene.control.ListCell;

// https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
public class CasellaSostituzione extends ListCell<Sostituzione>{
    
    @Override
    public void updateItem(Sostituzione item, boolean empty) {
        super.updateItem(item, empty);
 
        int index = this.getIndex();
        String name = null;
 
        if (item == null || empty) {
        
        } else {
            String icona;
            switch( item.getMotivazione() ) {
            case "copresenza":
                icona = "🤝";
                break;
            // FIXME mancano gli altri
            default:
                icona = "??";
            }
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " " + icona + " "+
            item.getMotivazione();
        }
         
        this.setText(name);
        setGraphic(null);
    }
}