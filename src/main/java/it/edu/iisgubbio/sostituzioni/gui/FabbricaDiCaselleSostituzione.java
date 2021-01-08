package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

//https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
public class FabbricaDiCaselleSostituzione implements Callback<ListView<Sostituzione>, ListCell<Sostituzione>>{
    @Override
    public ListCell<Sostituzione> call(ListView<Sostituzione> studentListView) {
        return new CasellaSostituzione();
    }
}
