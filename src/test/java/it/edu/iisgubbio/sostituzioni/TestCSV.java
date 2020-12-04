package it.edu.iisgubbio.sostituzioni;

import java.util.Arrays;

public class TestCSV {
    public static void main(String[] args) {
        String v[] = {"uno","due"};
        String t;
        
        t = CSV.toCSV(v);
        System.out.println("\n"+Arrays.toString(v) );
        System.out.println( t );
        if("uno,due".equals(t) || "\"uno\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
        
        v[0] = "u,no";
        t = CSV.toCSV(v);
        System.out.println("\n"+Arrays.toString(v) );
        System.out.println( t );
        if("\"u,no\",due".equals(t) || "\"u,no\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
        
        v[0] = "u\"no";
        t = CSV.toCSV(v);
        System.out.println("\n"+Arrays.toString(v) );
        System.out.println( t );
        if("\"u\"\"no\",due".equals(t) || "\"u\"\"no\",\"due\"".equals(t)) {
            System.out.println("ok");
        }else {
            System.out.println("errore");
        }
    }
}
