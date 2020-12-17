package it.edu.iisgubbio.sostituzioni.oggetti;

public class TestVersione {

    public static void main(String[] args) {
        Versione v1 = new Versione(1,0,0);
        Versione v2 = new Versione(2,0,0);
        int confronto;
        
        confronto = v1.compareTo(v2);
        System.out.print(v1+ " "+v2+" -> "+confronto+"  ");
        if(confronto<0) {
            System.out.println("OK");
        } else {
            System.out.println("errore");
        }
        
        confronto = v2.compareTo(v1);
        System.out.print(v2+ " "+v1+" -> "+confronto+"  ");
        if(confronto<0) {
            System.out.println("OK");
        } else {
            System.out.println("errore");
        }
        
        confronto = v2.compareTo(v2);
        System.out.print(v2+ " "+v2+" -> "+confronto+"  ");
        if(confronto==0) {
            System.out.println("OK");
        } else {
            System.out.println("errore");
        }
    }

}
