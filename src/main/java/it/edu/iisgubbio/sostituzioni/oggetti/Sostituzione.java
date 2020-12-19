package it.edu.iisgubbio.sostituzioni.oggetti;

public class Sostituzione extends OraLezione {

	protected String nomeSostituto;
	protected Motivo motivazione;
	protected String nomeDocenteDaSostituire;
	public enum Motivo {
		copresenza, potenziamento_stesse_discipline, potenziamento_altre_discipline, 
		recupero_stessa_classe,recupero_altra_classe_stesso_gruppo, recupero_altra_classe_altro_gruppo, 
		a_pagamento_stessa_classe,a_pagamento_altra_classe_stesso_gruppo, a_pagamento_altra_classe_e_altro_gruppo, 
		a_disposizione_stessa_classe,a_disposizione_altra_classe_stesso_gruppo, a_disposizione_altra_classe_altro_gruppo,
		libero_della_classe, libero_altra_classe, indefinito
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
	public Sostituzione(int giorno, int orario, String aula, String classe, boolean compresenza, String nomeSostituto) {
		super(giorno, orario, aula, classe, compresenza);
		this.nomeSostituto = nomeSostituto;
	}

	public Sostituzione(String x) {
		String v[] = x.split(",");
		this.nomeSostituto = v[0];
		this.motivazione = switch (v[1]) {
		case ("copresenza"):
			yield Motivo.copresenza;
		case ("potenziamento_stesse_discipline"):
			yield Motivo.potenziamento_stesse_discipline;
		case ("potenziamento_altre_discipline"):
			yield Motivo.potenziamento_altre_discipline;
		case ("recupero_stessa_classe"):
			yield Motivo.recupero_stessa_classe;
		case ("recupero_altra_classe_stesso_gruppo"):
			yield Motivo.recupero_altra_classe_stesso_gruppo;
		case ("recupero_altra_classe_altro_gruppo"):
			yield Motivo.recupero_altra_classe_altro_gruppo;
		case ("a_pagamento_stessa_classe"):
			yield Motivo.a_pagamento_stessa_classe;
		case ("a_pagamento_altra_classe_stesso_gruppo"):
		    yield Motivo.a_pagamento_altra_classe_stesso_gruppo;
		case ("a_pagamento_altra_classe_e_altro_gruppo"):
			yield Motivo.a_pagamento_altra_classe_e_altro_gruppo;
		case ("a_disposizione_stessa_classe"):
			yield Motivo.a_disposizione_stessa_classe;
	    case ("a_disposizione_altra_classe_stesso_gruppo"):
	        yield Motivo.a_disposizione_altra_classe_stesso_gruppo;
	    case ("a_disposizione_altra_classe_altro_gruppo"):
	        yield Motivo.a_disposizione_altra_classe_altro_gruppo;
		case ("libero_della_classe"):
			yield Motivo.libero_della_classe;
		case ("libero_altra_classe"):
			yield Motivo.libero_altra_classe;
		default:
			yield Motivo.indefinito;
		};

		this.classe = v[2];
		this.giorno = Integer.parseInt(v[3]);
		this.orario = Integer.parseInt(v[4]);
		this.aula = v[5];
		this.compresenza = Boolean.parseBoolean(v[6]);
	}

	public String toString() {
		if (compresenza) {
			return nomeSostituto + "," + motivazione + "," + classe + "," + giorno + "," + orario + "," + aula + ","
					+ compresenza + "," + "sostituzione";
		} else {
			return nomeSostituto + "," + motivazione + "," + classe + "," + giorno + "," + orario + "," + aula
					+ ",sostituzione";
		}
	}

	public String getDescrizione() {
		String nomeGiorno = Ora.nomiGiorni[giorno];
		return nomeSostituto + " sostituisce " + nomeDocenteDaSostituire + " nella classe " + classe + " il giorno "
				+ nomeGiorno + " alle ore " + orario + " nell'aula " + aula;
	}

}
