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

public class Gionale extends NomiFile {

	public static void scriviRecord(Sostituzione x) {
		BufferedWriter out = null;
		try
		{
			out = new BufferedWriter(new FileWriter(NomiFile.fileGiornale, true));
			out.write(x+"\n");
			out.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public static ArrayList<Sostituzione> leggiGiornale(File fileGiornale) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader("giornaleSostituzioni.csv"));
		ArrayList<Sostituzione> risposta=new ArrayList<>();
		String line = reader.readLine();
		while(line!=null) {
		     System.out.println(line);
		     line = reader.readLine();
		     
		     Sostituzione x = new Sostituzione(line);
		     
		     risposta.add(x);
		}
		
		reader.close();
		return risposta;
	}
}

