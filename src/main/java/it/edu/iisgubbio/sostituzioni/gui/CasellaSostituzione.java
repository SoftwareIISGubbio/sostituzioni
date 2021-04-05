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
            
            this.getStyleClass().clear();
            this.getStyleClass().add("cell");
            this.getStyleClass().add("indexed-cell");
            this.getStyleClass().add("list-cell");
            this.getStyleClass().add(item.getMotivazione().toString().toLowerCase());

            Docente bersaglio = Ambiente.cercaDocentePerNome(item.getNomeSostituto());
            name = (index + 1) + ". " +
            item.getNomeSostituto() + " ["+
            item.getMotivazione()+"]"+
            "  [recuperate "+(bersaglio.oreRecuperate+" su "+bersaglio.oreDaRecuperare)+"]";
        }
         
        this.setText(name);
    }
}