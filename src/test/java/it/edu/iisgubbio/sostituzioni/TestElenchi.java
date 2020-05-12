package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class TestElenchi {

    public static void main(String[] args) {
        long inizio = System.currentTimeMillis();
        ArrayList<Docente> elencoDocenti = Ambiente.docenti;
        long fine = System.currentTimeMillis();
        System.out.println("tempo impiegato: "+(fine-inizio)+"msec");
        
         for(int i=0; i<elencoDocenti.size(); i++) {
            System.out.println(elencoDocenti.get(i).nome);
        }

        String nomiClassi[] = Ambiente.getNomiClassi();
        for(int i=0; i<nomiClassi.length; i++) {
            System.out.print(nomiClassi[i]+", ");
            if(i>0 && i%10==0) {
                System.out.println("-");
            }
        }
        
        System.out.println(Ambiente.getProblemi());
    }

}
