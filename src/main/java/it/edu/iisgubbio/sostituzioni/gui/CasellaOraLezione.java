package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import javafx.scene.control.ListCell;

public class CasellaOraLezione extends ListCell<OraLezione>{
    
    @Override
    public void updateItem(OraLezione item, boolean empty) {
        super.updateItem(item, empty);
        String descrizione = null;
        
        if (item != null && !empty) {
            if( this.isSelected() ) {
                this.getStyleClass().add("selezionato");
            } else {
                this.getStyleClass().remove("selezionato");
            }
            int ora = item.orario;
            descrizione = "["+ora+"] "+item.toString();
        }
         
        this.setText(descrizione);
    }
}