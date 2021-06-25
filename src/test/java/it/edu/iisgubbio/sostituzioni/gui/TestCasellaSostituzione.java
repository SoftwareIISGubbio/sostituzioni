package it.edu.iisgubbio.sostituzioni.gui;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione.Motivo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestCasellaSostituzione extends Application{

    ListView<Sostituzione> listaSostituzioniPossibili = new ListView<Sostituzione>();

    @Override
    public void start(Stage primaryStage){
          
        StackPane root = new StackPane();
        root.getChildren().add(listaSostituzioniPossibili);
        listaSostituzioniPossibili.setCellFactory(new FabbricaDiCaselleSostituzione());
        
        Scene scene = new Scene(root, 550, 450);
        scene.getStylesheets().add("it/edu/iisgubbio/sostituzioni/stile.css");
        
        for(Motivo motivo: Motivo.values()) {
            Sostituzione s = new Sostituzione(1, 1, "aula", "5i", false, "PANFILI edoardo","2020-12-22");
            s.compresenza = motivo==Motivo.copresenza;
            s.setMotivazione(motivo);
            listaSostituzioniPossibili.getItems().add(s);
        }
        
        primaryStage.setTitle("Legenda colori sostituzioni");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
