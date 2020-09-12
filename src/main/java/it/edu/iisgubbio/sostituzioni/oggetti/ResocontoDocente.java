package it.edu.iisgubbio.sostituzioni.oggetti;

public class ResocontoDocente implements Comparable<ResocontoDocente>{
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getOreFatte() {
        return oreFatte;
    }
    
    public void incrementaOreFatte() {
        oreFatte++;
    }

    public void setOreFatte(int oreFatte) {
        this.oreFatte = oreFatte;
    }

    public int getOreDaFare() {
        return oreDaFare;
    }

    public void setOreDaFare(int oreDaFare) {
        this.oreDaFare = oreDaFare;
    }
    
    public int getOreResidue() {
        return oreDaFare-oreFatte;
    }

    private String nome;
    private int oreFatte;
    private int oreDaFare;
    
    public ResocontoDocente(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(ResocontoDocente o) {
        int mioPunteggio = oreDaFare-oreFatte;
        int altroPunteggio = o.oreDaFare-o.oreFatte;
        return altroPunteggio-mioPunteggio;
    }
}
