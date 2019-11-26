package it.edu.iisgubbio.sostituzioni;

import java.io.File;
import java.net.URL;

public class NomiFile {
    
    private static final String NOME_FILE_ORARIO = "orario.xlsx";
    private static final String NOME_FILE_GIORNALE = "giornaleSostituzioni.csv";
    
    public static File fileOrario;
    public static File fileGiornale;
    
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
        // cerco se presente il filer da usare  
        URL URLLocale = NomiFile.class.getResource("fileDatiDocenti.xlsx");
        File fileLocale = new File(URLLocale.getFile().substring(1));
        if(fileLocale.exists()) {
        	fileOrario = fileLocale;
        } else {
        	fileOrario = new File(scrivania+NOME_FILE_ORARIO);
        }
        fileGiornale = new File(scrivania+NOME_FILE_GIORNALE);
    }

}
