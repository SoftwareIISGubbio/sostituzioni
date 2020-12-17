package it.edu.iisgubbio.sostituzioni.oggetti;

public class Versione implements Comparable<Versione>{
    
    private int major;
    private int minor;
    private int patch;
    
    public Versione(int major, int minor, int patch) {
        super();
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /********************************************************************************************
     * @param text un testo nel formnato "major.minor.patch"
     *******************************************************************************************/
    public Versione(String text) {
        // TODO
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    @Override
    /********************************************************************************************
     * @return un numero negativo qualsiasi se questo oggetto è minore di altro
     *         zero se questo oggetto ha lo stesso valore di altro
     *         un numero positivo qualsiasi se questo oggetto è maggiore di altro
     *******************************************************************************************/
    public int compareTo(Versione altro) {
        // TODO Auto-generated method stub
        return 0;
    }

}
