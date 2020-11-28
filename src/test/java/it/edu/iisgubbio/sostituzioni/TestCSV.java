package it.edu.iisgubbio.sostituzioni;

public class TestCSV {
    public static void main(String[] args) {
        String v[] = {"uno","due"};
        
        String t = CSV.toCSV(v);
        if("uno,due".equals(t) || "\"uno\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
        
        v[0] = "u,no";
        if("\"u,no\",due".equals(t) || "\"u,no\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
        
        v[0] = "u\"no";
        if("\"u\"\"no\",due".equals(t) || "\"u\"\"no\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
    }
}
