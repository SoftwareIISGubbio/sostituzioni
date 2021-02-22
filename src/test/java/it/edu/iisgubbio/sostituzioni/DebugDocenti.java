package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class DebugDocenti {

	public static void main(String[] args) {
		ArrayList<Docente> lista = Ambiente.getDocenti();
		
		//ottiene un docente a caso
		Docente d = lista.get(118);//(int) (Math.random()*lista.size()));
		System.out.println(d);
		System.out.println("Recupero: "+d.oraARecupero);
		for(int i = 0; i<d.oreADisposizioneCassata.size(); i++) {
		    System.out.println("disposizione Cassata: "+d.oreADisposizioneCassata.get(i));
	    }
		for(int i = 0; i<d.oreADisposizioneGattapone.size(); i++) {
            System.out.println("disposizione Gattapone: "+d.oreADisposizioneGattapone.get(i));
        }
		for(int i = 0; i<d.orePotenziamento.size(); i++) {
			System.out.println("potenziamento: "+d.orePotenziamento.get(i));
		}
		for(int i = 0; i<d.oreAPagamento.size(); i++) {
			System.out.println("Pagamento: "+d.oreAPagamento.get(i));
		}
	}
}
