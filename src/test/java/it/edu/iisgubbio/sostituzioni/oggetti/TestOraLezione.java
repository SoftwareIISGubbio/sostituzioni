package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestOraLezione {
	public static void main(String[] args) {
		OraLezione oLezione1;
		oLezione1 = new OraLezione(2,2);
		oLezione1.aula = "154";
		oLezione1.classe = "4i";
		oLezione1.compresenza = true;
		System.out.println(oLezione1.toString());
		OraLezione oLezione2;
		oLezione2 = new OraLezione(2,2);
		oLezione2.aula = "154";
		oLezione2.classe = "5i";
		oLezione2.compresenza = false;
		System.out.println(oLezione2.toString());
	}
}
