package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestDocente {
	public static void main(String[] args) {
		Docente giammarioli;
		giammarioli = new Docente("Giammarioli Claudio");
		
		OraLezione i4Martedi3 = new OraLezione(2, 3);
		i4Martedi3.setClasse("4i");
		i4Martedi3.setAula("154");
		i4Martedi3.setCompresenza(true);
		giammarioli.addOraLezione( i4Martedi3 );
		
		OraLezione i4Giovedi3 = new OraLezione(4, 3);
		i4Giovedi3.setClasse("4i");
		i4Giovedi3.setAula("154");
		i4Giovedi3.setCompresenza(true);
		giammarioli.addOraLezione( i4Giovedi3 );
		
		OraLezione i4Giovedi4 = new OraLezione(4, 4);
		i4Giovedi4.setClasse("4i");
		i4Giovedi4.setAula("154");
		i4Giovedi4.setCompresenza(true);
		giammarioli.addOraLezione( i4Giovedi4 );
		
		System.out.println(giammarioli);
	}
}
