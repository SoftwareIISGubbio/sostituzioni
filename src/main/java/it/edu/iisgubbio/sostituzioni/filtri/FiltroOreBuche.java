package it.edu.iisgubbio.sostituzioni.filtri;

import java.util.ArrayList;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;
import it.edu.iisgubbio.sostituzioni.oggetti.Ora;

public class FiltroOreBuche {

	public static ArrayList<Docente> docentiOreBuche(ArrayList<Docente> tutti, Ora oreBuche) {
		ArrayList<Docente> risposta = new ArrayList<>();
		Ora ora = new Ora(oreBuche.giorno, oreBuche.orario);

		for (Docente d : tutti) {
			/*for (int m = 1; m <= 2; m++) {
				for (int p = 1; p <= 2; p++) {
					if (m == 2 && p == 2) {
					} else {
						if (!d.lavoraNellOra(ora)) {
							Ora oraPrima = new Ora(ora.giorno, ora.orario - m);
							Ora oraDopo = new Ora(ora.giorno, ora.orario - p);
							if (d.lavoraNellOra(oraPrima) && d.lavoraNellOra(oraDopo)) {
								risposta.add(d);
							}

						}
					}
				}

			}*/

			Ora OraMenoUno = new Ora(ora.giorno, ora.orario-1);
			Ora OraMenoDue = new Ora(ora.giorno, ora.orario-2);
			Ora OraPiuUno = new Ora(ora.giorno, ora.orario+1);
			Ora OraPiuDue = new Ora(ora.giorno, ora.orario+2);
			if (!d.lavoraNellOra(ora)) { 
				if(d.lavoraNellOra(OraMenoUno) && d.lavoraNellOra(OraPiuUno) ) {
					risposta.add(d);
				}
				if(d.lavoraNellOra(OraMenoDue) && d.lavoraNellOra(OraPiuUno)) {
					risposta.add(d);
				}
				if(d.lavoraNellOra(OraPiuDue) && d.lavoraNellOra(OraMenoUno)) {
					risposta.add(d);
				}
			}

		}

		return risposta;

	}

}
