package it.edu.iisgubbio.sostituzioni;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/** è soltanto un demo di quel che si può fare  */
public class FinestraSql {

    @FXML
    Label lInfo;
    
    @FXML
    TextArea taQuery;
    
    @FXML
    TextArea taOutput;
    
    Connection connessione; // FIXME da chiudere quando chiude la finestra
    
    @FXML
    void initialize() {
        long start = System.currentTimeMillis();
        try {
            connessione = DriverManager.getConnection("jdbc:h2:mem:");
            try( Statement stmt = connessione.createStatement() ) {             
                 String sql = """
                         CREATE TABLE sostituzioni(
                           nome_sostituto varchar(100),
                           motivazione varchar(100),
                           classe varchar(20),
                           giorno integer,
                           orario integer,
                           aula varchar(20),
                           copresenza boolean,
                           data date,
                           recupero boolean,
                           da_sostituire varchar(100)
                         )"""; 
                stmt.executeUpdate(sql);
                sql = "INSERT INTO sostituzioni(nome_sostituto,motivazione,classe,giorno,orario,aula,copresenza,data,recupero,da_sostituire) "+
                "VALUES('%s','%s','%s',%d,%d,'%s',%s,'%s',%s,'%s')";
                 
                for(Sostituzione s: Giornale.leggiGiornale()) {
                    String q = sql.formatted(s.getNomeSostituto(),
                            s.getMotivazione(),
                            s.classe,
                            s.giorno,
                            s.orario,
                            s.aula,
                            s.compresenza,
                            s.getData(),
                            s.isRecupero(),
                            s.getNomeDocenteDaSostituire());
                    stmt.executeUpdate(q);
                }
                
                // FIXME: troppo semplificato
                sql = """
                        CREATE TABLE docenti(
                          nome varchar(100),
                          gruppo varchar(100)
                        )"""; 
               stmt.executeUpdate(sql);
               sql = "INSERT INTO docenti(nome,gruppo) "+
                       "VALUES('%s','%s')";
               for(Docente d: Ambiente.getDocenti()) {
                   String q = sql.formatted(d.nome, d.gruppo);
                   stmt.executeUpdate(q);
               }
             } catch (SQLException e) {
                e.printStackTrace();
             } catch (IOException e) {
                e.printStackTrace();
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        lInfo.setText("TABELLE sostituzioni, docenti caricate in %d millisecondi".formatted(end-start));
    }
    
    @FXML
    private void eseguiQuery(ActionEvent e) {
        String query = taQuery.getText();
        try (Statement stmt = connessione.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metaData= rs.getMetaData();
            int numeroColonne = metaData.getColumnCount(); 
            StringBuilder risposta = new StringBuilder();
            risposta.append(metaData.getColumnName(1));
            for(int i=2; i<=numeroColonne;i++) {
                risposta.append(',');
                risposta.append(metaData.getColumnName(i));
            }
            risposta.append('\n');

            while (rs.next()) {
                risposta.append(rs.getString(1));
                for(int i=2; i<=numeroColonne;i++) {
                    risposta.append(',');
                    risposta.append(rs.getString(i));
                }
                risposta.append('\n');
            }
            taOutput.setText( risposta.toString() );
        } catch (SQLException ecc) {
            ecc.printStackTrace();
        }
    }
}
