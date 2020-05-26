package it.edu.iisgubbio.sostituzioni.oggetti;

public class Sostituzione extends OraLezione{

	protected String nomeSostituto;
	protected String motivazione; 
	
    public String getNomeSostituto() {
        return nomeSostituto;
    }

    public void setNomeSostituto(String nomeSostituto) {
        this.nomeSostituto = nomeSostituto;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    /** 
     * @param giorno giorno della settimana, lunedì=1
     * @param orario ora del giorno, prima ora = 1
     * @param aula nome dell'aula
     * @param classe classe in orario
     * @param compresenza true se presente codocente
     * @param nome del docente
     */
	public Sostituzione(int giorno, int orario, String aula, String classe, 
	        boolean compresenza, String nomeSostituto) {
		super(giorno, orario, aula, classe, compresenza);
		this.nomeSostituto=nomeSostituto;
	}
	
    public Sostituzione(String x) {
        String v[]=x.split(",");
        this.nomeSostituto = v[0];
        this.motivazione = v[1];
        this.classe=v[2];
        this.giorno=Integer.parseInt(v[3]);
        this.orario=Integer.parseInt(v[4]);
        this.aula=v[5];
        this.compresenza=Boolean.parseBoolean(v[6]);
    }

	public String toString() {
		if( compresenza ) {
			return nomeSostituto +","+motivazione+","+classe+ ","+ giorno+ ","+ orario +","+aula+","+compresenza+","+"sostituzione";
		}else {
		    return nomeSostituto +","+motivazione+","+classe+ ","+ giorno+ ","+ orario +","+aula+",sostituzione";
		}		
	}
	
	public String getDescrizione() {
        String nomeGiorno = Ora.nomiGiorni[giorno];
        return nomeSostituto +" sostituisce ? nella classe "+classe+ " il giorno "+ nomeGiorno+ 
                " alle ore "+ orario +" nell'aula "+aula;
	}

}
