package it.edu.iisgubbio.sostituzioni.oggetti;

/**
 * Rappresenta un'ora di lezione di una materia in una classe
 * @author 4i
 */
public class OraLezione extends Ora{
	public String aula;
	public String classe;
	public boolean compresenza;

	public OraLezione(int giorno, int orario) {
		super(giorno,orario);
	}
	
	public OraLezione(){
		
	}
	
	/**
	 * @param giorno giorno della settimana, lunedì=1
	 * @param orario ora del giorno, prima ora = 1
	 * @param aula nome dell'aula
	 * @param classe classe in orario
	 * @param compresenza true se presente codocente
	 */
	public OraLezione(int giorno, int orario, String aula, String classe, boolean compresenza) {
        super(giorno,orario);
        this.aula = aula;
        this.classe = classe;
        this.compresenza = compresenza;
    }
	
	@Override
	public String toString() {
		String toStringOra = super.toString();
		if( compresenza ) {
			return toStringOra+" "+classe+" "+aula+" compresenza";
		}else {
			return toStringOra+" "+classe+" "+aula;
		}
	}
}
