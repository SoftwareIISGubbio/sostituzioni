package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestSostituzioni {
	public static void main(String[] args) {
		Sostituzione giammarioli;
		giammarioli = new Sostituzione(2, 3, "154", "4I", true, "Giammarioli");
		String testo = giammarioli.toString();
		Sostituzione nuova = new Sostituzione(testo);
		System.out.println(giammarioli);
		System.out.println(nuova);
	}
}
