package it.edu.iisgubbio.sostituzioni;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

// https://code.makery.ch/blog/javafx-dialogs-official/
public class FinestraEccezione extends Alert{
    public FinestraEccezione(Throwable ex) {
        super(AlertType.ERROR);
        this.setTitle("Qualcosa è andato male");
        this.setHeaderText( ex.getClass().getCanonicalName()+": "+ex.getLocalizedMessage() );

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(false);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        
        BorderPane expContent = new BorderPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.setTop( new Label("StackTrace:") );
        expContent.setCenter(textArea);

        // Set expandable Exception into the dialog pane.
        this.getDialogPane().setExpandableContent(expContent);
        this.getDialogPane().setExpanded(true);
    }
}
