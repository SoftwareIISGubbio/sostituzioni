package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestInformazioniDocente extends Application{
    
    public void start(Stage x) {
        Stage s = new Stage();
        Scene scena;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            scena = new Scene(  fxmlLoader.load(getClass().getResource("InformazioniDocente.fxml").openStream()) );
            InformazioniDocente controller = (InformazioniDocente) fxmlLoader.getController();
            s.setScene(scena);
            s.setTitle("info");
            s.show();
            Docente d = Elenchi.cercaDocentePerNome("giammarioli");
            controller.setDocente(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
