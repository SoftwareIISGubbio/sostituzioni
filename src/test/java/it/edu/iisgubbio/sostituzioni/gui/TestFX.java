package it.edu.iisgubbio.sostituzioni.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestFX extends Application{

    Button btn = new Button("Saluta!");

    @Override
    public void start(Stage primaryStage){
        btn.setOnAction(e -> esegui());
          
        StackPane root = new StackPane();
        root.getChildren().add(btn);
          
        Scene scene = new Scene(root, 300, 250);
          
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void esegui(){
        btn.setText("Ciao Mondo!");
    }
    
    public static void main(String[] args) {
        launch(args);

    }

}
