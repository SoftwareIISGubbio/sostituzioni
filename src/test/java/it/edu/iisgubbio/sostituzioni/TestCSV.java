package it.edu.iisgubbio.sostituzioni;

import java.util.Arrays;

public class TestCSV {
    public static void main(String[] args) {
        String v[] = {"uno","due"};
        String t;
        
        t = CSV.toCSV(v);
        System.out.print(Arrays.toString(v) + " → " + t +" : ");
        if("uno,due".equals(t) || "\"uno\",\"due\"".equals(t)) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }
        
        v[0] = "u,no";
        t = CSV.toCSV(v);
        System.out.print(Arrays.toString(v) + " → " + t +" : ");
        if("\"u,no\",due".equals(t) || "\"u,no\",\"due\"".equals(t)) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }
        
        v[0] = "u\"no";
        t = CSV.toCSV(v);
        System.out.print(Arrays.toString(v) + " → " + t +" : ");
        if("\"u\"\"no\",due".equals(t) || "\"u\"\"no\",\"due\"".equals(t)) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }
        
        
        
        t = "\"uno\",\"due\",\"tre\"";
        v = CSV.fromCSV(t);
        System.out.print(t + " → " + Arrays.toString(v) +" : ");
        if( "uno-due-tre".equals( String.join("-", v) ) ) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }        
        
        t = "\"uno\",\"du\"\"e\",\"tre\"";
        v = CSV.fromCSV(t);
        System.out.print(t + " → " + Arrays.toString(v) +" : ");
        if( "uno-du\"e-tre".equals( String.join("-", v) ) ) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }   
        
        
        t = "uno,due,tre";
        v = CSV.fromCSV(t);
        System.out.print(t + " → " + Arrays.toString(v) +" : ");
        if( "uno-due-tre".equals( String.join("-", v) ) ) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }   
        
        t = "\"uno\",due,\"tr,e\"";
        v = CSV.fromCSV(t);
        System.out.print(t + " → " + Arrays.toString(v) +" : ");
        if( "uno-due-tr,e".equals( String.join("-", v) ) ) {
            System.out.println("OK");
        }else {
            System.out.println("ERRORE");
        }   

    }
}
