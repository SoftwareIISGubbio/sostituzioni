package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.OraLezione;

public class DebugDocenti {

	public static void main(String[] args) {
		ArrayList<Docente> lista = Ambiente.docenti;
		
		//ottiene un docente a caso
		Docente d = lista.get(118);//(int) (Math.random()*lista.size()));
		System.out.println(d);
		System.out.println("Disposizione cassata: "+d.oraADisposizioneCassata);
		System.out.println("Disposizione gattapone: "+d.oraADisposizioneGattapone);
		System.out.println("Recupero: "+d.oraARecupero);
		for(int i = 0; i<d.orePotenziamento.size(); i++) {
			System.out.println("potenziamento: "+d.orePotenziamento.get(i));
		}
		for(int i = 0; i<d.oreAPagamento.size(); i++) {
			System.out.println("Pagamento: "+d.oreAPagamento.get(i));
		}
	}
}
