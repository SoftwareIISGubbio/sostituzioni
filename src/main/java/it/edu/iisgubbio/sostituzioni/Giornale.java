package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class Giornale {

	public static void scriviRecord(Sostituzione x) {
		BufferedWriter out = null;
		try
		{
			out = new BufferedWriter(new FileWriter(Ambiente.getFileGiornale(), true));
			out.write(x+"\n");
			out.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public static ArrayList<Sostituzione> leggiGiornale(File fileGiornale) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileGiornale));
		ArrayList<Sostituzione> risposta=new ArrayList<>();
		String line;
		while((line=reader.readLine())!=null) {
		     Sostituzione x = new Sostituzione(line);
		     risposta.add(x);
		}
		
		reader.close();
		return risposta;
	}
}

