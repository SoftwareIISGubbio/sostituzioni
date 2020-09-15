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
            if(item.getMotivazione().equals("a disposizione Cassata") || 
                    item.getMotivazione().equals("libero della classe") ||
                    item.getMotivazione().equals("libero altra classe") ) {
                this.setTextFill(Color.GRAY);
            }else {
                this.setTextFill(Color.BLACK);
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