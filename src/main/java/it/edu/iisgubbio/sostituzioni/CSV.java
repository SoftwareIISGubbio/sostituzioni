package it.edu.iisgubbio.sostituzioni;

import java.util.Arrays;

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
				csvLine += ",\"\"";
			else
				csvLine += String.format(",\"%s\"", vector[i].replaceAll("\"", "\"\""));
		}

		return csvLine;
	}

	/**
	 * Trasforma una stringa in formato CSV in un vettore di stringhe
	 * 
	 * @param csv: String in formato CSV
	 * @return vResult: String[] formato da tutte le 'sezioni' del CSV
	 */
	public static String[] fromCSV(String csvLine) {
		char[] vCharCsv = csvLine.toCharArray();
		boolean aperte = false, chiuse = false;

		String[] vResult = new String[csvLine.split(",").length];

		// Controlla se le virgolette sono aperte o chiuse, e in base a quello suddivide
		// la stringa.
		for (int i = 0, virgoletteDiFila = 0, index = -1; i < vCharCsv.length; i++) {
			if (vCharCsv[i] == '"') {
				if (!aperte) {
					aperte = true;
					chiuse = false;
					index++;
					continue;
				}
				virgoletteDiFila++;
				if (i + 1 < vCharCsv.length && vCharCsv[i + 1] != '"') {
					if (virgoletteDiFila % 2.00 != 0) {
						if (i + 1 < vCharCsv.length && vCharCsv[i + 1] == ',' || i + 1 == vCharCsv.length) {
							chiuse = true;
							aperte = false;
						}
					}
				} else {
					if (i + 1 < vCharCsv.length) {
						i++;
					}
				}
			} else {
				virgoletteDiFila = 0;
			}
			if (!chiuse) {
				if (vResult[index] == null) {
					if (String.valueOf(vCharCsv[i]) == "")
						vResult[index] = null;
					else
						vResult[index] = String.valueOf(vCharCsv[i]);
				} else {
					if (String.valueOf(vCharCsv[i]) == "")
						vResult[index] += null;
					else
						vResult[index] += String.valueOf(vCharCsv[i]);
				}
			}
		}
		vResult = restringiVettore(vResult);
		int index = vResult.length - 1;
		vResult[index] = vResult[index].substring(0, vResult[index].length() - 1);

		return vResult;
	}

	/**
	 * Rimuove da un vettore gli spazi riempiti con null
	 * 
	 * @param vector: String[]
	 * @return newVector: String[]
	 */
	private static String[] restringiVettore(String[] vector) {
		int dimension = 0;
		for (int i = 0; i < vector.length; i++) {
			if (vector[dimension] == null) {
				break;
			}
			dimension++;
		}

		String[] newVector = new String[dimension];
		for (int i = 0; i < dimension; i++) {
			newVector[i] = vector[i];
		}

		return newVector;
	}
}
