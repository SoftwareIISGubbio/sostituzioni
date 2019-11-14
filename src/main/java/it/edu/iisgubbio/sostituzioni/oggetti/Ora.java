package it.edu.iisgubbio.sostituzioni.oggetti;

/****************************************************************************
 * Rappresenta un'ora di orario
 * @author 4i
 ***************************************************************************/
public class Ora {
	private static String nomiGiorni[] = 
		{null,"lunedì","martedì","mercoledì","giovedì","venerdì"};
	private static String nomiOre[] = {
		null, "8:00-8:55", "8:55-9:50", 
		"10:00-10:55", "10:55-11:45",
		"11:55-12:45", "12:45-13:35",
		"14:25-15:20", "15:20-16:15"
	};
	int giorno;
	int orario;
	
	/**
	 * 
	 * @param giorno il giorno della settimana, 1=lunedì
	 * @param orario l'ora del giorno, 1=prima ora
	 * @throws IllegalArgumentException quando giorno fuori da [1-5] o ora 
	 * 	fuori da [1-8]
	 */
	public Ora(int giorno, int orario) throws IllegalArgumentException {
		if(giorno>5 || giorno<1) {
			throw new IllegalArgumentException("numero del giorno errato");
		}
		this.giorno = giorno;
		this.orario = orario;
	}
	
	@Override
	public String toString() {
		
		String s = nomiGiorni[giorno];
		String o = nomiOre[orario];
		
		return s+" "+o;
	}
}
