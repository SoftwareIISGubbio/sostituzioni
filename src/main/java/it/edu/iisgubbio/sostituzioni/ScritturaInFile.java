package it.edu.iisgubbio.sostituzioni;

import java.io.BufferedWriter;
import java.io.FileWriter;

import it.edu.iisgubbio.sostituzioni.oggetti.Sostituzione;

public class ScritturaInFile extends NomiFile {

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
	}

