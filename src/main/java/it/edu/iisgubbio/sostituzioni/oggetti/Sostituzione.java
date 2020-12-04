package it.edu.iisgubbio.sostituzioni.oggetti;

public class Sostituzione extends OraLezione {

	protected String nomeSostituto;
	protected Motivo motivazione;
	protected String nomeDocenteDaSostituire;

	public enum Motivo {
		copresenza, potenziamento_stesse_discipline, potenziamento_altre_discipline, recupero_stessa_classe,
		recupero_altra_classe_Stesso_gruppo, recupero_altra_classe_Altro_gruppo, a_pagamento_Stessa_classe,
		a_pagamento_altra_classe_Stesso_gruppo, a_pagamento_altra_classe_e_altro_gruppo, a_disposizione_Cassata,
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
		case ("potenziamento stesse discipline"):
			yield Motivo.potenziamento_stesse_discipline;
		case ("potenziamento altre discipline"):
			yield Motivo.potenziamento_altre_discipline;
		case ("recupero stessa classe"):
			yield Motivo.recupero_stessa_classe;
		case ("recupero altra classe, stesso gruppo"):
			yield Motivo.recupero_altra_classe_Stesso_gruppo;
		case ("recupero altra classe, altro gruppo"):
			yield Motivo.recupero_altra_classe_Altro_gruppo;
		case ("a pagamento stessa classe"):
			yield Motivo.a_pagamento_Stessa_classe;
		case ("a pagamento altra classe ma stesso gruppo"):
			yield Motivo.a_pagamento_altra_classe_Stesso_gruppo;
		case ("a pagamento altra classe e altro gruppo"):
			yield Motivo.a_pagamento_altra_classe_e_altro_gruppo;
		case ("a disposizione Cassata"):
			yield Motivo.a_disposizione_Cassata;
		case ("libero della classe"):
			yield Motivo.libero_della_classe;
		case ("libero altra classe"):
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
