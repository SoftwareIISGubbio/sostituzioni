package it.edu.iisgubbio.sostituzioni;



import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class InfoProgramma{
	private String[] sviluppatori = {"Edoardo Panfili","Claudio Giammarioli","Angeloni Pietro","Battistoni Giulia","Bejdo Loris","Biagiotti Luca",
			"Casagrande Nicola","Cenni Gabriele","Costea Bianca Ioana","Ermirandi Jennifer","Kakcuku Sokol","Mancini Giada",
			"Morelli Cristian","Natalini Sofia","Pierotti Giorgio","Rinaldoni Matteo","Vantaggi Leonardo","Vantaggi Michele",
			"Luzi Davide","Andrei Riginel Ungureanu","Flavio Merli","Omar Mohamed Nasr","Diego Mariucci"};
	@FXML
	Label lController;
	@FXML
	Label lVersione;
	@FXML
	Label lAggiornamento;
	@FXML
	ListView<String> lListaSviluppatori;
	@FXML
    void initialize() {
		String javaVersion=System.getProperty("java.version");
    	lController.setText(javaVersion);
    	lVersione.setText(Ambiente.VERSIONE);
    	lAggiornamento.setText(Ambiente.DATA_COMPILAZIONE);
    	
    	Random rand = new Random();
		
		for (int i = 0; i < sviluppatori.length; i++) {
			int randomIndexToSwap = rand.nextInt(sviluppatori.length);
			String temp = sviluppatori[randomIndexToSwap];
			sviluppatori[randomIndexToSwap] = sviluppatori[i];
			sviluppatori[i] = temp;
		}
		
		for (int i = 0; i < sviluppatori.length; i++) {
			String stringa = sviluppatori[i];
			lListaSviluppatori.getItems().add(stringa);
		}
    }
}
