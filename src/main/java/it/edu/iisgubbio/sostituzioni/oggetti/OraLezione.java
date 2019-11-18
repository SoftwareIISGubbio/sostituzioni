package it.edu.iisgubbio.sostituzioni.oggetti;

/**
 * Rappresenta un'ora di lezione di una materia in una classe
 * @author 4i
 */
public class OraLezione extends Ora{
	private String aula;
	private String classe;
	private boolean compresenza;

	public OraLezione(int giorno, int orario) {
		super(giorno,orario);
	}
	
	public void setAula(String aula) {
		this.aula = aula;
	}
	
	public void setClasse(String c) {
		classe = c;
	}
	
	public void setCompresenza(boolean compresenza) {
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
