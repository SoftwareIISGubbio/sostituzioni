package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class Elenchi {
    public static ArrayList<Docente> docenti = new ArrayList<>();
    
    // FIXME: questa parte è presente al solo scopo di test iniziale
    // una volta completata l'applicazione va rimossa
    static {
        Docente giammarioli;
        giammarioli = new Docente("Giammarioli Claudio");
        giammarioli.oreLezione.add( new OraLezione(2, 3, "154", "4i", true) );
        giammarioli.oreLezione.add( new OraLezione(4, 3, "154", "4i", true) );
        giammarioli.oreLezione.add( new OraLezione(4, 4, "154", "4i", true) );
        giammarioli.oreLezione.add( new OraLezione(1, 7, "154", "5i", true) );
        giammarioli.oreLezione.add( new OraLezione(1, 8, "154", "5i", true) );
        docenti.add(giammarioli);
        
        Docente panfili;
        panfili = new Docente("Panfili Edoardo");
        panfili.oreLezione.add( new OraLezione(1, 1, "154", "4i", false) );
        panfili.oreLezione.add( new OraLezione(1, 2, "154", "4i", false) );
        panfili.oreLezione.add( new OraLezione(1, 3, "184", "5i", false) );
        panfili.oreLezione.add( new OraLezione(1, 6, "184", "3i1", true) );
        panfili.oreLezione.add( new OraLezione(2, 1, "184", "3i1", true) );
        panfili.oreLezione.add( new OraLezione(2, 2, "184", "3i1", true) );
        panfili.oreLezione.add( new OraLezione(2, 3, "154", "4i", true) );
        docenti.add(panfili);
        
        Docente maranghi;
        maranghi = new Docente("Maranghi Samuele");
        maranghi.oreLezione.add( new OraLezione(1, 4, "154", "3i2", false) );
        maranghi.oreLezione.add( new OraLezione(1, 5, "154", "3i1", true) );
        maranghi.oreLezione.add( new OraLezione(1, 7, "154", "5i", true) );
        maranghi.oreLezione.add( new OraLezione(1, 8, "154", "5i", true) );
        docenti.add(maranghi);
        
        Docente stocchi;
        stocchi = new Docente("Stocchi David");
        stocchi.oreLezione.add( new OraLezione(1, 1, "184", "5i", true) );
        stocchi.oreLezione.add( new OraLezione(1, 2, "184", "5i", true) );
        stocchi.oreLezione.add( new OraLezione(1, 4, "253", "3i1", false) );
        stocchi.oreLezione.add( new OraLezione(1, 7, "253", "4i", false) );
        stocchi.oreLezione.add( new OraLezione(1, 8, "124", "1i", false) );
        docenti.add(stocchi);
    }
}
