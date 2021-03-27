package it.edu.iisgubbio.sostituzioni;

import java.io.FileWriter;
import java.io.IOException;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class ExportCSV {

    public static void main(String[] args) throws IOException {
        try (FileWriter uscita = new FileWriter("/volumes/ramdisk/dati.csv")){
            for(Docente d: Ambiente.getDocenti()) {
                for( OraLezione ora: d.oreLezione) {
                    String dati[] = {
                            d.nome,
                            ""+ora.giorno,
                            ""+ora.orario,
                            ora.classe,
                            ora.materia,
                            ora.aula
                    };
                    uscita.write( CSV.toCSV(dati) + "\n" );
                }
            }
        }
    }

}
