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
            /*
            String icona="";
            l'idea originale era di mettere emoji ma JavaFX ha problemi con il rendering delle emoji
            switch( item.getMotivazione() ) {
            case "copresenza":
                icona = "[copresenza]";
                break;
            case "recupero":
                icona = "[recupero]";
                break;
            case "della classe":
                icona = "[della classe]";
                break;
            case "libero":
                icona = "[libero]";
                break;
            case "a disposizione":
                icona = "[a disposizione]";
                break;
            default:
                icona = "[??]";
            }*/
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " ["+
            item.getMotivazione()+"]";
        }
         
        this.setText(name);
        setGraphic(null);
    }
}