package it.edu.iisgubbio.sostituzioni.oggetti;

import it.edu.iisgubbio.sostituzioni.CSV;

public class Sostituzione extends OraLezione {
    
    private static final int INDICE_NOME_SOSTITUO = 0;
    private static final int INDICE_MOTIVAZIONE = 1;
    private static final int INDICE_CLASSE = 2;
    private static final int INDICE_GIORNO = 3;
    private static final int INDICE_ORARIO = 4;
    private static final int INDICE_AULA = 5;
    private static final int INDICE_COPRESENZA = 6;
    private static final int INDICE_DATA = 7;
    private static final int NUMERO_CAMPI = 8;

	protected String nomeSostituto;
	protected Motivo motivazione;
	protected String nomeDocenteDaSostituire;
	protected String data;
	public enum Motivo {
		copresenza, potenziamento_stesse_discipline, potenziamento_altre_discipline, 
		recupero_stessa_classe,recupero_altra_classe_stesso_gruppo, recupero_altra_classe_altro_gruppo, 
		a_pagamento_stessa_classe,a_pagamento_altra_classe_stesso_gruppo, a_pagamento_altra_classe_e_altro_gruppo, 
		a_disposizione_stessa_classe,a_disposizione_altra_classe_stesso_gruppo, a_disposizione_altra_classe_altro_gruppo,
		libero_della_classe, libero_altra_classe_stesso_gruppo, libero_altra_classe_altro_gruppo, indefinito
	}

	public String getNomeDocenteDaSostituire() {
		return nomeDocenteDaSostituire;
	}

	public void setNomeDocenteDaSostituire(String nomeDocenteDaSostituire) {
		this.nomeDocenteDaSostituire = nomeDocenteDaSostituire;
	}

	public String getNomeSostituto() {
		return nomeSostituto;
	}

	public void setNomeSostituto(String nomeSostituto) {
		this.nomeSostituto = nomeSostituto;
	}

	public Motivo getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(Motivo motivazione) {
		this.motivazione = motivazione;
	}

	/**
	 * @param giorno      giorno della settimana, lunedì=1
	 * @param orario      ora del giorno, prima ora = 1
	 * @param aula        nome dell'aula
	 * @param classe      classe in orario
	 * @param compresenza true se presente codocente
	 * @param nome        del docente
	 */
	public Sostituzione(int giorno, int orario, String aula, String classe, boolean compresenza, String nomeSostituto, String data) {
		super(giorno, orario, aula, classe, compresenza);
		this.nomeSostituto = nomeSostituto;
		this.data = data;
	}

	public Sostituzione(String lineaCSV) {
	    System.out.println(lineaCSV);
	    String v[] = CSV.fromCSV( lineaCSV );
	    System.out.println(String.join("##", v));
	    this.giorno = Integer.parseInt( v[INDICE_GIORNO] ); 
	    this.orario = Integer.parseInt( v[INDICE_ORARIO]); 
	    this.aula = v[INDICE_AULA];
	    this.classe = v[INDICE_CLASSE];
	    this.compresenza = v[INDICE_COPRESENZA].equals("true");
	    this.nomeSostituto = v[INDICE_NOME_SOSTITUO];
        this.motivazione = Motivo.valueOf(v[INDICE_MOTIVAZIONE]);
        this.data = v[INDICE_DATA];
	}

	public String toString() {
        String v[] = new String[NUMERO_CAMPI];
        v[INDICE_NOME_SOSTITUO] = nomeSostituto;
        v[INDICE_MOTIVAZIONE] = motivazione!=null ? motivazione.toString() : Motivo.indefinito.toString();
        v[INDICE_CLASSE] = classe==null ? "?" : classe;
        v[INDICE_GIORNO] = ""+giorno;
        v[INDICE_ORARIO] = ""+orario;
        v[INDICE_AULA] = aula;
        v[INDICE_COPRESENZA] = ""+compresenza;
        v[INDICE_DATA] = data;
        return CSV.toCSV(v);
	}

	public String getDescrizione() {
		String nomeGiorno = Ora.nomiGiorni[giorno];
		return nomeSostituto + " sostituisce " + nomeDocenteDaSostituire + " nella classe " + classe + " il giorno "
				+ nomeGiorno + " alle ore " + orario + " nell'aula " + aula;
	}

}
