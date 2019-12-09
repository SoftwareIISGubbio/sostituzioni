package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.net.URL;

public class NomiFile {
    
    private static final String NOME_FILE_ORARIO = "fileDatiDocenti.xlsx";
    private static final String NOME_FILE_GIORNALE = "giornaleSostituzioni.csv";
    private static final String NOME_FILE_XML = "fet_stampa_teachers.xml";
    
    public static File fileOrario;
    public static File fileGiornale;
    public static File fileOrarioXml;
    
    static {
        // FIXME: su Linux bisogna fare altri test, solitamente la scrivania (almeno con Gnome) si 
        // chiama "Scrivania"
        String nomeUtente = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        String scrivania = nomeUtente+File.separator+"Desktop"+File.separator;
        boolean windows = os.indexOf("win") >= 0;

        if(windows) {
            File fScrivania = new File(scrivania);
            if( !fScrivania.exists() ) {
                scrivania = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\";
            }
        }
        
        fileOrario = trovaFile(scrivania, NOME_FILE_ORARIO);
        fileOrarioXml = trovaFile(scrivania, NOME_FILE_XML);
        fileGiornale = new File(scrivania+NOME_FILE_GIORNALE);
    }
    
    private static File trovaFile(String scrivania, String file) {
        File cercato = new File(scrivania+file);
        if(cercato.exists()) {
            return cercato;
        } else {
            return new File( NomiFile.class.getResource(file).getFile() );
        }
    }

}
