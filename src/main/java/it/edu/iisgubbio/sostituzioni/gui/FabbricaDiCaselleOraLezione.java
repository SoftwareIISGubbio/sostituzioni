package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

//https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
public class FabbricaDiCaselleOraLezione implements Callback<ListView<OraLezione>, ListCell<OraLezione>>{
    @Override
    public ListCell<OraLezione> call(ListView<OraLezione> oraListView) {
        return new CasellaOraLezione();
    }
}
