package it.edu.iisgubbio.sostituzioni.oggetti;

public class Sostituzione extends OraLezione{
	protected String nome;
	public Sostituzione(int giorno, int orario, String aula, String classe, boolean compresenza, String nome) {
		super(giorno, orario, aula, classe, compresenza);
		this.nome=nome;
	}
	/**
	 * @param nome del docente 
	 * @param giorno giorno della settimana, lunedì=1
	 * @param orario ora del giorno, prima ora = 1
	 * @param aula nome dell'aula
	 * @param classe classe in orario
	 * @param compresenza true se presente codocente
	 */
	public String toString() {
		
		if( compresenza ) {
			return nome +","+classe+ ","+ giorno+ ","+ orario +","+aula+","+compresenza+","+"sostituzione";
		}else {
			return nome +","+classe+ ","+ giorno+ ","+ orario +","+aula+",sostituzione";
		}		
	}
	
	public Sostituzione(String x) {
		String v[]=x.split(",");
		this.nome = v[0];
		this.classe=v[1];
		this.giorno=Integer.parseInt(v[2]);
		this.orario=Integer.parseInt(v[3]);
		this.aula=v[4];
		this.compresenza=Boolean.parseBoolean(v[5]);
		
	}

}
