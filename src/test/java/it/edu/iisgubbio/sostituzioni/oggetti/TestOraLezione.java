package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestOraLezione {
	public static void main(String[] args) {
		OraLezione oLezione1;
		oLezione1 = new OraLezione(2,2);
		oLezione1.setAula("154");
		oLezione1.setClasse("4i");
		oLezione1.setCompresenza(true);
		System.out.println(oLezione1.toString());
		OraLezione oLezione2;
		oLezione2 = new OraLezione(2,2);
		oLezione2.setAula("154");
		oLezione2.setClasse("5i");
		oLezione2.setCompresenza(false);
		System.out.println(oLezione2.toString());
	}
}
