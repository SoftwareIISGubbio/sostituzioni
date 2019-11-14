package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestOra {

	public static void main(String[] args) {
		Ora x = null;
		Ora y = null;
		
		try {
			y = new Ora(12,3);
			System.out.println(y);
		}catch(Exception ecc) {
			System.out.println("sbaglio!");
		}
		// qui
		try {
			x = new Ora(2,3);
			System.out.println(x);
		}catch(Exception ecc) {
			System.out.println("sbaglio!");
		}
		
		System.out.println("--------");
		System.out.println(y);
		System.out.println(x);

	}

}
