package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestDocente {
	public static void main(String[] args) {
		Docente giammarioli;
		giammarioli = new Docente("Giammarioli Claudio");
		
		Docente panfili;
		panfili = new Docente ("Panfili Edoardo");
		
		OraLezione i4Martedi3 = new OraLezione(2, 3);
		i4Martedi3.classe = "4i";
		i4Martedi3.aula = "154";
		i4Martedi3.compresenza = true;
		giammarioli.oreLezione.add( i4Martedi3 );
		
		OraLezione i4Giovedi3 = new OraLezione(4, 3);
		i4Giovedi3.classe = "4i";
		i4Giovedi3.aula = "154";
		i4Giovedi3.compresenza = true;
		giammarioli.oreLezione.add( i4Giovedi3 );
		
		OraLezione i4Giovedi4 = new OraLezione(4, 4);
		i4Giovedi4.classe = "4i";
		i4Giovedi4.aula = "154";
		i4Giovedi4.compresenza = true;
		giammarioli.oreLezione.add( i4Giovedi4 );
		
		OraLezione i5Lunedi = new OraLezione(4,1);
		i5Lunedi.classe = "5i";
		i5Lunedi.aula = "184";
		i5Lunedi.compresenza = false;
		panfili.oreLezione.add(i5Lunedi);
		
		giammarioli.oraARecupero = new Ora(1, 1);
		giammarioli.oraADisposizioneCassata = new Ora(2, 1);
		giammarioli.oraADisposizioneGattapone = new Ora(2, 2);
		
		giammarioli.oreAPagamento.add(new Ora(5,1));
		giammarioli.oreAPagamento.add(new Ora(5,2));
		
		giammarioli.orePotenziamento.add(new Ora(4,5));
        giammarioli.orePotenziamento.add(new Ora(4,6));
		
		System.out.println(giammarioli);
		
		System.out.println("lavora in 4i: "+giammarioli.lavoraNellaClasse("4i"));
		System.out.println("lavora in 4b: "+giammarioli.lavoraNellaClasse("4b"));
		System.out.println("lavora nell'ora '"+i4Giovedi4+"': "+giammarioli.lavoraNellOra(i4Giovedi4));
		System.out.println("lavora nell'ora '"+i5Lunedi+"': "+giammarioli.lavoraNellOra(i5Lunedi));
	}
}
