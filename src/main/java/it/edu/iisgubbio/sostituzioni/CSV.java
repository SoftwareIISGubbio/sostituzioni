package it.edu.iisgubbio.sostituzioni;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {

	/**
	 * Trasforma un vettore di stringhe in una stringa da usare come CSV.
	 * 
	 * @param vector: String[] da trasformare.
	 * @return csv: String in formato CSV
	 */
	public static String toCSV(String[] vector) {
		// Mette le virgolette all'inizio e alla fine di ogni stringa,
		// e raddoppia quelle che stanno all'interno.
		String csvLine = "\"" + vector[0].replaceAll("\"", "\"\"") + "\"";
		for (int i = 1; i < vector.length; i++) {
			if (vector[i] == null)
				csvLine += ",null";
			else
				csvLine += String.format(",\"%s\"", vector[i].replaceAll("\"", "\"\""));
		}

		return csvLine;
	}
	
	/**
	 * Trasforma una stringa CSV in 1 vettore.
	 * 
	 * @author Nathan Spears - https://stackoverflow.com/questions/1441556/parsing-csv-input-with-a-regex-in-java
	 * @author java2s - http://www.java2s.com/Code/Java/Development-Class/SimpledemoofCSVmatchingusingRegularExpressions.htm
	 * 
	 * @param csv: String in formato CSV
	 * @return vCSV: String[]
	 */
	public static String[] fromCSV(String csvLine) {
		List<String> list = new ArrayList<>();
		Pattern csvPattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?=,|$)");
		Matcher matcher = csvPattern.matcher(csvLine);
		
		while (matcher.find()) {
			String match = matcher.group();
			if (match == null)
				break;
			if (match.endsWith(",")) {
				match = match.substring(0, match.length() - 1).replace("\\''", "\"");
			}
			if (match.startsWith("\"")) {
				match = match.substring(1, match.length() - 1).replace("\\''", "\"");
			}
			if (match.length() == 0 || match.equals("null"))
				match = null;
			list.add(match);
		}
		
		String[] vCSV = new String[list.size()];
		for (int i = 0; i < vCSV.length; i++) {
			vCSV[i] = list.get(i);
		}
		
		return vCSV;
	}
}
